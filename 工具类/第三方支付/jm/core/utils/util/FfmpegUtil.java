package com.cn.jm.core.utils.util;

import java.io.File;
import java.util.List;

import com.jfinal.kit.PathKit;

import it.sauronsoftware.jave.Encoder;

	public class FfmpegUtil {

		private static final String FFMPEG_PATH = "/usr/local/ffmpeg/ffmpeg";

		/**
		 * 截图
		 * 
		 * @param videoRealPath
		 *            视频路径
		 * @return
		 */
		public static String printScreen(String videoName) {
			String rootPath = PathKit.getWebRootPath();
			String name = JMUploadKit.uploadPath + JMUploadKit.uploadImg + NoUtils.getUuidByJdk(true) + ".jpg";
			File source = new File(rootPath + videoName);

			File dest = new File(rootPath + name);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			Encoder encoder = new Encoder();
			int width = 350;
			int height = 240;
			try {
				it.sauronsoftware.jave.MultimediaInfo m = encoder.getInfo(source);
				width = m.getVideo().getSize().getWidth();
				height = m.getVideo().getSize().getHeight();
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<String> commend = new java.util.ArrayList<String>();
			// commend.add("D:\\ffmpeg-20180723-0bb5cd8-win64-static\\bin\\ffmpeg.exe");
			commend.add(FFMPEG_PATH);
			commend.add("-ss");// 偏移量
			commend.add("2");// 这个参数是设置截取视频多少秒时的画面
			commend.add("-i");// 输入
			commend.add(rootPath + videoName);
			commend.add("-f");// 格式化，要输出什么格式的截图
			commend.add("mjpeg");

			commend.add("-s");
			commend.add(width + "*" + height);

			commend.add("-vframes");
			commend.add("1");// 截取1帧
			commend.add(rootPath + name);

			try {
				ProcessBuilder builder = new ProcessBuilder();
				builder.directory(new File("/usr/local/ffmpeg"));
				builder.command(commend);
				builder.redirectErrorStream(true);
				builder.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return name;
		}

	}

//	/**
//	 * 截图
//	 * 如果需要在虚拟机上传,需要开通权限,否则无法上传至oss
//	 * @param videoRealPath
//	 *            视频路径
//	 * @return
//	 */
//	public static String printScreen(UploadFile f,String videoName) {
//		String rootPath = PathKit.getWebRootPath();
////			String rootPath = "http://worldmodel.oss-cn-shenzhen.aliyuncs.com";
//			String name = JMUploadKit.uploadPath + JMUploadKit.uploadVideo + NoUtils.getUuidByJdk(true) + ".jpg";
//			File desc = new File(rootPath+videoName);
//			
//			Encoder encoder = new Encoder();
//			int width = 350;
//			int height = 240;
//			try {
//				it.sauronsoftware.jave.MultimediaInfo m = encoder.getInfo(desc);
//				width = m.getVideo().getSize().getWidth();
//				height = m.getVideo().getSize().getHeight();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			List<String> commend = new java.util.ArrayList<String>();
////			 commend.add("D:\\ffmpeg-20180813-551a029-win64-static\\bin\\ffmpeg.exe");
//			commend.add(FFMPEG_PATH);
//			commend.add("-ss");// 偏移量
//			commend.add("1");// 这个参数是设置截取视频多少秒时的画面
//			commend.add("-i");// 输入
//			commend.add(rootPath + videoName);
//			commend.add("-f");// 格式化，要输出什么格式的截图
//			commend.add("mjpeg");
//
//			commend.add("-s");
//			commend.add(width + "*" + height);
//
//			commend.add("-vframes");
//			commend.add("1");// 截取1帧
//			commend.add(rootPath + name);
//
//			try {
//				ProcessBuilder builder = new ProcessBuilder();
//				builder.directory(new File("/usr/local/ffmpeg"));
////				builder.directory(new File("D:\\ffmpeg-20180813-551a029-win64-static\\bin"));
//				builder.command(commend);
//				builder.redirectErrorStream(true);
//				builder.start();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			//上传图片至阿里云OSS
//			JMUploadKit.uploadImage(name,new File(rootPath + name));
//			desc.delete();
//			return name;
//		}
//
//	public static void main(String[] args) {
//		JMUploadKit.uploadImage("123",new File("http://127.0.0.1/worldmodel-web/upload/video/a169a2a3bdcc46e1ab0db118f4412670.jpg"));
//	}
