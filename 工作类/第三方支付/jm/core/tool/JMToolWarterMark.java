package com.cn.jm.core.tool;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class JMToolWarterMark {

	/*
	 * public static File uploadImage(Controller controller, String param){
	 * UploadFile file = controller.getFile(param); File changeFile = null; String
	 * rootPath = PathKit.getWebRootPath() + "/upload/"; if(file != null){
	 * if(!file.getContentType().contains("image") ){ file.getFile().delete(); }
	 * changeFile = new File(rootPath + ToolDateTime.format(new Date(),
	 * "yyyyMMddHHmmss")+"_"+ToolUtils.getUuidByJdk(true)+".jpg"); File source =
	 * file.getFile(); source.renameTo(changeFile);
	 * pressImage(rootPath+"watermark.png", changeFile, 20, 20); } return
	 * changeFile; }
	 * 
	 * public final static void pressImage(String pressImg, File targetFile, int x,
	 * int y) { try { Image src = ImageIO.read(targetFile); int wideth =
	 * src.getWidth(null); int height = src.getHeight(null); BufferedImage image =
	 * new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB); Graphics g =
	 * image.createGraphics(); g.drawImage(src, 0, 0, wideth, height, null);
	 * 
	 * // 水印文件 File _filebiao = new File(pressImg); Image src_biao =
	 * ImageIO.read(_filebiao); int wideth_biao = src_biao.getWidth(null); int
	 * height_biao = src_biao.getHeight(null); g.drawImage(src_biao, wideth -
	 * wideth_biao - x, y, wideth_biao, height_biao, null); g.dispose();
	 * FileOutputStream out = new FileOutputStream(targetFile); JPEGImageEncoder
	 * encoder = JPEGCodec.createJPEGEncoder(out); encoder.encode(image);
	 * out.close(); } catch (Exception e) { e.printStackTrace(); } }
	 */

	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
	}

	public static String fileUpload(Controller controller, String param, String path) {
		UploadFile f = controller.getFile(param);
		if (f == null) {
			return null;
		}
		String webRoot = PathKit.getWebRootPath();
		String saveDir = JFinal.me().getConstants().getBaseUploadPath();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		StringBuilder newFileName = new StringBuilder().append("/")
				.append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
		if (StrKit.notBlank(saveDir)) {
			newFileName.append("/").append(path);
		}
		Random r = new Random();
		int x = r.nextInt(999999 - 100000 + 1) + 100000;
		newFileName.append("/").append(dateFormat.format(new Date())).append("/")
				.append(System.currentTimeMillis() + "_" + x).append(getFileExt(f.getFileName()));
		File dest = new File(webRoot + newFileName.toString());
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		File source = f.getFile();
		source.renameTo(dest);
		pressImage(webRoot + "/upload/watermark.png", dest, 20, 20, 600);
		return newFileName.toString();
	}

	public synchronized final static void pressImage(String pressImg, File targetFile, int x, int y, int w) {
		String fileSuffix = targetFile.getName().substring(targetFile.getName().lastIndexOf(".") + 1);
		try {
			BufferedImage image = ImageIO.read(targetFile);
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage newImg = null;
			if (width != w) {
				int differWidth = width - w;
				double d = Double.valueOf(differWidth) / Double.valueOf(width);
				int h = (int) (height - height * d);

				switch (image.getType()) {
				case 13:
					break;
				default:
					newImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
					break;
				}
				Graphics2D g = newImg.createGraphics();
				if (fileSuffix.equals("png") || fileSuffix.equals("PNG")) {
					// 解决png透明底变黑色
					newImg = g.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
				} else {
					g.drawImage(image, 0, 0, width, height, null);
				}
				g.dispose();

				g = newImg.createGraphics();
				g.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
				g.dispose();

				// 水印文件
				File _filebiao = new File(pressImg);
				Image src_biao = ImageIO.read(_filebiao);
				int wideth_biao = src_biao.getWidth(null);
				int height_biao = src_biao.getHeight(null);
				g = newImg.createGraphics();
				g.drawImage(src_biao, w - wideth_biao - x, y, wideth_biao, height_biao, null);
				g.dispose();

			} else {
				newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImg.createGraphics();
				if (fileSuffix.equals("png") || fileSuffix.equals("PNG")) {
					// 解决png透明底变黑色
					newImg = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
					g.dispose();
					g = newImg.createGraphics();
					g.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
				} else {
					g.drawImage(image, 0, 0, width, height, null);
				}
				g.dispose();

				File _filebiao = new File(pressImg);
				Image src_biao = ImageIO.read(_filebiao);
				int wideth_biao = src_biao.getWidth(null);
				int height_biao = src_biao.getHeight(null);
				g = newImg.createGraphics();
				g.drawImage(src_biao, width - wideth_biao - x, y, wideth_biao, height_biao, null);
				g.dispose();
			}
			ImageIO.write(newImg, fileSuffix, targetFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String fileUpload2(Controller controller, String param, String path) {
		UploadFile f = controller.getFile(param);
		if (f == null) {
			return null;
		}
		String webRoot = PathKit.getWebRootPath();
		String saveDir = JFinal.me().getConstants().getBaseUploadPath();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		StringBuilder newFileName = new StringBuilder().append("/")
				.append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
		if (StrKit.notBlank(saveDir)) {
			newFileName.append("/").append(path);
		}
		Random r = new Random();
		int x = r.nextInt(999999 - 100000 + 1) + 100000;
		newFileName.append("/").append(dateFormat.format(new Date())).append("/")
				.append(System.currentTimeMillis() + "_" + x).append(getFileExt(f.getFileName()));
		File dest = new File(webRoot + newFileName.toString());
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		File source = f.getFile();
		source.renameTo(dest);
		pressImage2(webRoot + "/upload/watermark.png", dest, 20, 20, 600);
		return newFileName.toString();
	}

	public final static void pressImage2(String pressImg, File targetFile, int x, int y, int w) {
		try {
			BufferedImage image = ImageIO.read(targetFile);
			int width = image.getWidth();
			int height = image.getHeight();
			BufferedImage newImg = null;
			if (width != w) {
				int differWidth = width - w;
				double d = Double.valueOf(differWidth) / Double.valueOf(width);
				int h = (int) (height - height * d);

				switch (image.getType()) {
				case 13:
					break;
				default:
					newImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
					break;
				}
				Graphics2D g = newImg.createGraphics();
				g.drawImage(image, 0, 0, width, height, null);
				g.dispose();

				Graphics g2 = newImg.getGraphics();
				g2.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);

				FileOutputStream out = new FileOutputStream(targetFile);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(newImg);
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成路径
	 */
	public static String getPath(String path) {
		String webRoot = PathKit.getWebRootPath();
		String saveDir = JFinal.me().getConstants().getBaseUploadPath();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		StringBuilder newFileName = new StringBuilder().append("/")
				.append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
		if (StrKit.notBlank(saveDir)) {
			newFileName.append("/").append(path);
		}
		newFileName.append("/").append(dateFormat.format(new Date()));
		Random r = new Random();
		int x = r.nextInt(999999 - 100000 + 1) + 100000;
		newFileName.append("/").append(System.currentTimeMillis() + "_" + x);
		File dest = new File(webRoot + newFileName.toString());
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		return newFileName.toString();
	}

	public static String getThumbnail(String name, String img) {
		return getPath(name) + getFileExt(img);
	}

	public static void deleteFile(String... values) {
		String filePath = PathKit.getWebRootPath();
		for (String value : values) {
			if (value != null) {
				File file = new File(filePath + value);
				if (file.exists() && file.isFile()) {
					file.delete();
				}
			}
		}
	}

}
