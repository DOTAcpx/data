package com.cn.jm.core.tool.image;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.cn.jm.core.tool.ToolFile;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ToolImage {
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	private final static String[] imgExts = new String[]{"jpg", "jpeg", "png", "bmp"};
	
	public static String getExtName(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index != -1 && (index + 1) < fileName.length()) {
			return fileName.substring(index + 1);
		} else {
			return null;
		}
	}
	
	/**
	 * 通过文件扩展名，判断是否为支持的图像文件，支持则返回 true，否则返回 false
	 * @param fileName
	 * @return
	 */
	public static boolean isImageExtName(String fileName) {
		if (StrKit.isBlank(fileName)) {
			return false;
		}
		fileName = fileName.trim().toLowerCase();
		String ext = getExtName(fileName);
		if (ext != null) {
			for (String s : imgExts) {
				if (s.equals(ext)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 *  通过文件扩展名，判断是否为支持的图像文件，不支持则返回 false，否则返回 true
	 * @param fileName
	 * @return
	 */
	public static final boolean notImageExtName(String fileName) {
		return ! isImageExtName(fileName);
	}
	
	/**
	 * 加载本地图片
	 * @param sourceImageFileName
	 * @return
	 */
	public static BufferedImage loadImageFile(String sourceImageFileName) {
		if (notImageExtName(sourceImageFileName)) {
			throw new IllegalArgumentException("只支持如下几种类型的图像文件：jpg、jpeg、png、bmp");
		}

		File sourceImageFile = new File(sourceImageFileName);
		if (!sourceImageFile.exists() || !sourceImageFile.isFile()) {
			throw new IllegalArgumentException("文件不存在");
		}

		try {
			return ImageIO.read(sourceImageFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 以 maxWidth 为界对图像进行缩放，高保真保存
	 * 1：当图像 width > maxWidth 时，将宽度变为 maxWidth，高度等比例进行缩放，高保真保存
	 * 2:当图像 width <= maxWidth 时，宽高保持不变，只进行高保真保存
	 * @param maxWidth
	 * @param srcFile
	 * @param saveFile
	 */
	public static void zoom(int maxWidth, File srcFile, String saveFile) {
		float quality = 0.8f;

		try {
			BufferedImage srcImage = ImageIO.read(srcFile);
			int srcWidth = srcImage.getWidth();
			int srcHeight = srcImage.getHeight();

			// 当宽度在 maxWidth 范围之内，不改变图像宽高，只进行图像高保真保存
			if (srcWidth <= maxWidth) {
				/**
				 * 如果图像不进行缩放的话， resize 就没有必要了，
				 * 经过测试是否有 resize 这一步，生成的结果图像完全一样，一个字节都不差
				 * 所以删掉 resize，可以提升性能，少一步操作
				 */
				// BufferedImage ret = resize(srcImage, srcWidth, srcHeight);
				// saveWithQuality(ret, quality, saveFile);
				saveWithQuality(srcImage, quality, saveFile);
			}
			// 当宽度超出 maxWidth 范围，将宽度变为 maxWidth，而高度按比例变化
			else {
				float scalingRatio = (float)maxWidth / (float)srcWidth;			// 计算缩放比率
				float maxHeight = ((float)srcHeight * scalingRatio);	// 计算缩放后的高度
				BufferedImage ret = resize(srcImage, maxWidth, (int)maxHeight);
				saveWithQuality(ret, quality, saveFile);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 对图片进行剪裁，只保存选中的区域
	 * @param sourceImageFile 原图
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 */
	public static BufferedImage crop(String sourceImageFile, int left, int top, int width, int height) {
		if (notImageExtName(sourceImageFile)) {
			throw new IllegalArgumentException("只支持如下几种类型的图像文件：jpg、jpeg、png、bmp");
		}

		try {
			BufferedImage bi = ImageIO.read(new File(sourceImageFile));
			width = Math.min(width, bi.getWidth());
			height = Math.min(height, bi.getHeight());
			if(width <= 0) width = bi.getWidth();
			if(height <= 0) height = bi.getHeight();

			left = Math.min(Math.max(0, left), bi.getWidth() - width);
			top = Math.min(Math.max(0, top), bi.getHeight() - height);

			BufferedImage subImage = bi.getSubimage(left, top, width, height);
			return subImage;	// return ImageIO.write(bi, "jpeg", fileDest);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 将处理完的图片保存
	 * @param bi
	 * @param outputImageFile
	 */
	public static void save(BufferedImage bi, String outputImageFile) {
		FileOutputStream newImage = null;
		try {
			// ImageIO.write(bi, "jpg", new File(outputImageFile));
			ImageIO.write(bi, getExtName(outputImageFile), new File(outputImageFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (newImage != null) {
				try {
					newImage.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/**
	 * 高保真缩放
	 * @param bi
	 * @param toWidth
	 * @param toHeight
	 * @return
	 */
	public static BufferedImage resize(BufferedImage bi, int toWidth, int toHeight) {
		Graphics g = null;
		try {
			// 从 BufferedImage 对象中获取一个经过缩放的 image
			Image scaledImage = bi.getScaledInstance(toWidth, toHeight, Image.SCALE_SMOOTH);
			// 创建 BufferedImage 对象，存放缩放过的 image
			BufferedImage ret = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
			g = ret.getGraphics();
			g.drawImage(scaledImage, 0, 0, null);
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (g != null) {
				g.dispose();
			}
		}
	}
	
	/**
	 * jfinal.com 使用参数为宽：200, 高：200, 质量：0.8，
	 * 生成大小为：6.79 KB (6,957 字节)
	 *
	 * 如果使用参数为宽：120, 高：120, 质量：0.8，
	 * 则生成的图片大小为：3.45 KB (3,536 字节)
	 *
	 * 如果使用参数为宽：300, 高：300, 质量：0.5，
	 * 则生成的图片大小为：7.54 KB (7,725 字节)
	 *
	 *
	 * 建议使用 0.8 的 quality 并且稍大点的宽高
	 * 只选用两种质量：0.8 与 0.9，这两个差别不是很大，
	 * 但是如果尺寸大些的话，选用 0.8 比 0.9 要划算，因为占用的空间差不多的时候，尺寸大些的更清晰
	 */
	public static void saveWithQuality(BufferedImage im, float quality, String outputImageFile) {
		FileOutputStream newImage = null;
		try {
			/* 输出到文件流 */
			newImage = new FileOutputStream(outputImageFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newImage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
			/* 压缩质量, 0.75 就算是高质量 */
			jep.setQuality(quality, true);	// jep.setQuality(0.9f, true);
			encoder.encode(im, jep);
			/* 近JPEG编码 */
			// newImage.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (newImage != null) {
				try {newImage.close();} catch (IOException e) {throw new RuntimeException(e);}
			}
		}
	}
	
	
	/**
	 * 将上传的图片移动到指定文件夹
	 * @param uploadFile
	 * @return
	 */
	public static String moveFile(UploadFile uploadFile) {
		if (uploadFile == null)
			return null;

		File file = uploadFile.getFile();
		if (!file.exists()) {
			return null;
		}

		String webRoot = PathKit.getWebRootPath();

		String uuid = UUID.randomUUID().toString().replace("-", "");

		StringBuilder newFileName = new StringBuilder(webRoot).append(File.separator).append("attachment")
				.append(File.separator).append(dateFormat.format(new Date())).append(File.separator).append(uuid)
				.append(ToolFile.getSuffix(file.getName()));

		File newfile = new File(newFileName.toString());

		if (!newfile.getParentFile().exists()) {
			newfile.getParentFile().mkdirs();
		}

		file.renameTo(newfile);

		return ToolFile.removePrefix(newfile.getAbsolutePath(), webRoot);
	}

	
	public static int[] ratio(String src) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new File(src));
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		return new int[] { width, height };
	}

	public static String ratioAsString(String src) throws IOException {
		File file = new File(src);
		if (!file.exists()) {
			return null;
		}
		BufferedImage bufferedImage = ImageIO.read(file);
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		return String.format("%s x %s", width, height);
	}

	public static String scale(String src, int w, int h) throws IOException {
		int inserTo = src.lastIndexOf(".");
		String dest = src.substring(0, inserTo) + String.format("_%sx%s", w, h) + src.substring(inserTo, src.length());
		scale(src, dest, w, h);
		return dest;
	}

	/**
	 * 等比缩放，居中剪切
	 * 
	 * @param src
	 * @param dest
	 * @param w
	 * @param h
	 * @throws IOException
	 */
	public static void scale(String src, String dest, int w, int h) throws IOException {
		String srcSuffix = src.substring(src.lastIndexOf(".") + 1);
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(srcSuffix);
		ImageReader reader = (ImageReader) iterator.next();

		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis);

		BufferedImage srcBuffered = readBuffereImage(reader, w, h);
		BufferedImage targetBuffered = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics graphics = targetBuffered.getGraphics();
		graphics.drawImage(srcBuffered.getScaledInstance(w, h, Image.SCALE_DEFAULT), 0, 0, null); // 绘制缩小后的图

		graphics.dispose();
		srcBuffered.flush();

		ImageIO.write(targetBuffered, srcSuffix, new File(dest));
		targetBuffered.flush();
	}
	
	private static BufferedImage readBuffereImage(ImageReader reader, int w, int h) throws IOException {
		ImageReadParam param = reader.getDefaultReadParam();
		int srcWidth = reader.getWidth(0);
		int srcHeight = reader.getHeight(0);

		Rectangle rect = null;

		if ((float) w / h > (float) srcWidth / srcHeight) {
			h = h * srcWidth / w;
			w = srcWidth;
			rect = new Rectangle(0, (srcHeight - h) / 2, w, h);
		} else {
			w = w * srcHeight / h;
			h = srcHeight;
			rect = new Rectangle((srcWidth - w) / 2, 0, w, h);
		}
		param.setSourceRegion(rect);
		BufferedImage srcBuffered = reader.read(0, param);
		return srcBuffered;
	}
	
	public static void main(String[] args) {
		System.out.println(ToolFile.getSuffix("xxx.jpg"));
	}


}
