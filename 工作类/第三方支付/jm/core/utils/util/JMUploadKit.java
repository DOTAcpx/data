package com.cn.jm.core.utils.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;

public class JMUploadKit {

	private static Logger log = Logger.getLogger(JMUploadKit.class);

	/**
	 * 上传图片允许的最大尺寸，目前只允许 200KB
	 */
	public static final int imageMaxSize = 200 * 1024 * 1024;
	/**
	 * 上传视频允许的最大尺寸，目前只允许 10M
	 */
	public static final int videoMaxSize = 200 * 1024 * 1024;

	/**
	 * 上传图片临时目录，相对于 baseUploadPath 的路径，是否以 "/" 并无影响
	 */
	public static final String uploadTempPath = "/temp/";
	public static final String uploadAvatarPath = "/avatar/";
	public static final String uploadPath = "/upload/";
	public static final String uploadImgPath = "/image/";

	public static final String uploadImg = "image/";

	public static final String uploadAvatar = "avatar/";

	public static final String uploadVideo = "video/";

	/**
	 * 相对于 webRootPath 之后的目录，与"/upload" 是与 baseUploadPath 重合的部分
	 */
	public static final String basePath = PathKit.getWebRootPath() + "/upload/";

	/**
	 * 每个子目录允许存 5000 个文件
	 */
	public static final int FILES_PER_SUB_DIR = 5000;

	public enum TypeEnum {
		image, video, file;
	}

	/**
	 * 上传头像
	 * 
	 * @param controller
	 * @param accountId
	 * @return
	 */
//	public static File uploadAvatar(Controller controller, String parameterName) {
//		File file = new File(basePath + uploadAvatarPath);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		File to = new File(file.toString() + "/avatar_" + System.currentTimeMillis() + ".jpg");
//		UploadFile upload = controller.getFile(parameterName, JMUploadKit.uploadTempPath, imageMaxSize);
//		if (upload != null) {
//			try {
//				Thumbnails.of(upload.getFile().getAbsolutePath()).scale(1f).outputQuality(0.2f).outputFormat("jpg")
//						.toFile(to);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return null;
//			}
//			upload.getFile().delete();
//		} else
//			return null;
//		return to;
//	}
//
//	public static String uploadAvatarImg(Controller controller, String parameterName) {
//		File file = new File(basePath + uploadAvatarPath);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		File to = new File(file.toString() + "/avatar_" + System.currentTimeMillis() + ".jpg");
//
//		UploadFile upload = controller.getFile(parameterName, JMUploadKit.uploadTempPath, imageMaxSize);
//
//		if (upload != null && upload.getFile() != null) {
//			try {
//				Thumbnails.of(upload.getFile().getAbsolutePath()).scale(1f).outputQuality(0.2f).outputFormat("jpg")
//						.toFile(to);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return null;
//			}
//			upload.getFile().delete();
//		} else
//			return null;
//
//		return uploadPath + uploadVideo + to.getName();
//	}
//
//	public static String uploadImg(Controller controller, String parameterName) {
//		File file = new File(basePath + uploadImgPath);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		File to = new File(file.toString() + "/image_" + System.currentTimeMillis() + ".jpg");
//		UploadFile upload = controller.getFile(parameterName, JMUploadKit.uploadTempPath, imageMaxSize);
//		if (upload != null && upload.getFile() != null) {
//			try {
//				Thumbnails.of(upload.getFile().getAbsolutePath()).scale(1f).outputQuality(0.2f).outputFormat("jpg")
//						.toFile(to);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return null;
//			}
//			upload.getFile().delete();
//		} else
//			return null;
//
//		return uploadPath + uploadImgPath + to.getName();
//	}
//
//	/**
//	 * 上传图片
//	 * 
//	 * @param controller
//	 * @param parameterName
//	 * @return
//	 */
//	public static File uploadImage(Controller controller, String parameterName) {
//		File file = new File(basePath + uploadImgPath);
//		if (!file.exists()) {
//			file.mkdirs();
//		}
//		File to = new File(file.toString() + "/image_" + System.currentTimeMillis() + ".jpg");
//		UploadFile upload = controller.getFile(parameterName, JMUploadKit.uploadTempPath, imageMaxSize);
//		if (upload != null) {
//			try {
//				Thumbnails.of(upload.getFile().getAbsolutePath()).scale(1f).outputQuality(0.2f).outputFormat("jpg")
//						.toFile(to);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return null;
//			}
//			upload.getFile().delete();
//		} else
//			return null;
//		return to;
//	}
//
	/**
	 * 
	 * @Description: 上传视频
	 * @param controller
	 * @param parameterName
	 * @return
	 */
	public static String uploadVideo(UploadFile f,String path){
	    if(f == null){
	      return null;
	    }
	        String webRoot = PathKit.getWebRootPath();
	        String saveDir = JFinal.me().getConstants().getBaseUploadPath();
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	        StringBuilder newFileName = new StringBuilder()
	            .append("/")
	            .append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
	        if(StrKit.notBlank(saveDir)){
	          newFileName.append("/")
	          .append(path);
	        }
	        Random r = new Random();
	    int x=r.nextInt(999999-100000+1)+100000;
	        newFileName.append("/")
	        .append(dateFormat.format(new Date()))
	            .append("/");
	        StringBuilder fileName = new StringBuilder().append(System.currentTimeMillis()+"_"+x)
	                .append(getFileExt(f.getFileName()));
	        newFileName.append(fileName);
	        File dest = new File(webRoot + newFileName.toString());
	        if (!dest.getParentFile().exists()) {
	          dest.getParentFile().mkdirs();
	        }
	        File source = f.getFile();
	        source.renameTo(dest);
	        
	        // Endpoint以杭州为例，其它Region请按实际情况填写。
	        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
	        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
	        String accessKeyId = "LTAIZEVgm0oUI5tF";
	        String accessKeySecret = "l2Ia9aPnljt9brrK9y43eH3hjq9YVP";
	        // 创建OSSClient实例。
	        OSSClient ossClient = null ;
	        try {
	          ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
	           // System.out.println(source.getPath());
	            ossClient.putObject("worldmodel",newFileName.toString().substring(1,newFileName.toString().length()),dest);
	            return newFileName.toString();
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null ;
	    }finally {
	      // 关闭OSSClient。
	      if(ossClient != null)
	        ossClient.shutdown();
//	      f.getFile().delete();
//	            source.delete();
//	            dest.delete();
	    }
	}

	/**
	 * 
	 * @Description: 上传图片
	 * @param controller
	 * @param parameterName
	 * @return
	 */
	public static String uploadImage(String path,UploadFile f){
		if(f == null){
		      return null;
	    }
        String webRoot = PathKit.getWebRootPath();
        String saveDir = JFinal.me().getConstants().getBaseUploadPath();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        StringBuilder newFileName = new StringBuilder()
            .append("/")
            .append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
        if(StrKit.notBlank(saveDir)){
          newFileName.append("/")
          .append(path);
        }
        Random r = new Random();
    int x=r.nextInt(999999-100000+1)+100000;
        newFileName.append("/")
        .append(dateFormat.format(new Date()))
            .append("/");
        StringBuilder fileName = new StringBuilder().append(System.currentTimeMillis()+"_"+x)
                .append(getFileExt(f.getFileName()));
        newFileName.append(fileName);
        File dest = new File(webRoot + newFileName.toString());
        if (!dest.getParentFile().exists()) {
          dest.getParentFile().mkdirs();
        }
        File source = f.getFile();
        source.renameTo(dest);
        
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIZEVgm0oUI5tF";
        String accessKeySecret = "l2Ia9aPnljt9brrK9y43eH3hjq9YVP";
        // 创建OSSClient实例。
        OSSClient ossClient = null ;
        try {
          ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
           // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
           // System.out.println(source.getPath());
            ossClient.putObject("worldmodel",newFileName.substring(1,newFileName.length()),dest);
            return newFileName.toString();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }finally {
	      // 关闭OSSClient。
	      if(ossClient != null)
	        ossClient.shutdown();
	      f.getFile().delete();
	            source.delete();
	            dest.delete();
	    }
		return null;
	}
	public static boolean imgLocation(File file) {
		String reg = "(mp4|flv|avi|rm|rmvb|wmv)";
		Pattern p = Pattern.compile(reg);
		return p.matcher(file.getName()).find();
	}

//	/**
//	 * 批量上传多个文件
//	 * 
//	 * @return
//	 */
//	public List<File> uploads(Controller controller) {
//		List<UploadFile> listUploadFiles = controller.getFiles(PathKit.getWebRootPath() + "/upload");
//		List<File> listFiles = new ArrayList<File>();
//		for (UploadFile file : listUploadFiles) {
//			File changeFile = new File(PathKit.getWebRootPath() + "/upload/"
//					+ ToolDateTime.format(new Date(), "yyyyMMddHHmmss") + "_" + ToolUtils.getUuidByJdk(true) + ".jpg");
//			if (file != null) {
//				File source = file.getFile();
//				source.renameTo(changeFile);
//				listFiles.add(source);
//			}
//		}
//		return listFiles;
//	}

	public static String videoScreenshot(String screenshotPath){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Random r = new Random();
		String webRoot = PathKit.getWebRootPath();
		String saveDir = JFinal.me().getConstants().getBaseUploadPath();
		StringBuilder newFileName = new StringBuilder()
				.append("/")
				.append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
		if(StrKit.notBlank(saveDir)){
			newFileName.append("/")
			.append("videoScreenshot");
		}
		int x=r.nextInt(999999-100000+1)+100000;
		newFileName.append("/")
		.append(dateFormat.format(new Date()))
		.append("/");

		String nowfileName =dateFormat.format(new Date())+x+".jpg"; 
        String url = "http://worldmodel.oss-cn-shenzhen.aliyuncs.com"+screenshotPath;
        try {
			download(url,nowfileName,"");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        File f = new File("\\"+nowfileName);
        File dest = new File(webRoot + newFileName.toString());
        StringBuilder fileName = new StringBuilder().append(System.currentTimeMillis()+"_"+x)
        		.append(getFileExt(f.getName()));
        newFileName.append(fileName);
        if (!dest.getParentFile().exists()) {
          dest.getParentFile().mkdirs();
        }
        f.renameTo(dest);
        
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIZEVgm0oUI5tF";
        String accessKeySecret = "l2Ia9aPnljt9brrK9y43eH3hjq9YVP";
        // 创建OSSClient实例。
        OSSClient ossClient = null ;
        try {
          ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
           // System.out.println(source.getPath());
            ossClient.putObject("worldmodel",newFileName.toString().substring(1,newFileName.toString().length()),dest);
            return newFileName+"?x-oss-process=image/rotate,90";
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    }finally {
	      // 关闭OSSClient。
	      if(ossClient != null)
	        ossClient.shutdown();
	      f.delete();
	            dest.delete();
	    }
    }
 public static void download(String urlString, String filename,String savePath) throws Exception {  
	 // 构造URL  
	 URL url = new URL(urlString);  
	 // 打开连接  
	 URLConnection con = url.openConnection();  
	 //设置请求超时为5s  
	 con.setConnectTimeout(5*1000);  
	 // 输入流  
	 InputStream is = con.getInputStream();  
	 
	 // 1K的数据缓冲  
	 byte[] bs = new byte[1024];  
	 // 读取到的数据长度  
	 int len;  
	 // 输出的文件流  
	 File sf=new File(savePath);  
	 if(!sf.exists()){  
       sf.mkdirs();  
	 }  
	 OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);  
	 // 开始读取  
	 while ((len = is.read(bs)) != -1) {  
		 os.write(bs, 0, len);  
	 }  
    // 完毕，关闭所有链接  
    os.close();  
    is.close();  
} 

	
	public static String fileUpload(Controller controller, String param, String path){
	    UploadFile f = controller.getFile(param);
	    if(f == null){
	      return null;
	    }
        String webRoot = PathKit.getWebRootPath();
        String saveDir = JFinal.me().getConstants().getBaseUploadPath();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        StringBuilder newFileName = new StringBuilder()
            .append("/")
            .append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
        if(StrKit.notBlank(saveDir)){
          newFileName.append("/")
          .append(path);
        }
        Random r = new Random();
	    int x=r.nextInt(999999-100000+1)+100000;
	        newFileName.append("/")
	        .append(dateFormat.format(new Date()))
	            .append("/");
        StringBuilder fileName = new StringBuilder().append(System.currentTimeMillis()+"_"+x)
                .append(getFileExt(f.getFileName()));
        newFileName.append(fileName);
        File dest = new File(webRoot + newFileName.toString());
        if (!dest.getParentFile().exists()) {
          dest.getParentFile().mkdirs();
        }
        File source = f.getFile();
        source.renameTo(dest);
        
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIZEVgm0oUI5tF";
        String accessKeySecret = "l2Ia9aPnljt9brrK9y43eH3hjq9YVP";
        // 创建OSSClient实例。
        OSSClient ossClient = null ;
        try {
        	ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
            // System.out.println(source.getPath());
            ossClient.putObject("worldmodel",newFileName.toString().substring(1,newFileName.toString().length()),dest);
            return newFileName.toString();
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null ;
	    }finally {
	      // 关闭OSSClient。
	      if(ossClient != null)
	        ossClient.shutdown();
	      	f.getFile().delete();
            source.delete();
            dest.delete();
	    }
	        
	  }
	public static String imageUpload(File f,String name, String path){
        String webRoot = PathKit.getWebRootPath();
        File dest = new File(webRoot + name);
        if (!dest.getParentFile().exists()) {
          dest.getParentFile().mkdirs();
        }
        f.renameTo(dest);
        
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIZEVgm0oUI5tF";
        String accessKeySecret = "l2Ia9aPnljt9brrK9y43eH3hjq9YVP";
        // 创建OSSClient实例。
        OSSClient ossClient = null ;
        try {
          ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
           // System.out.println(source.getPath());
            ossClient.putObject("worldmodel",name.substring(1,name.toString().length()),dest);
            return name;
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null ;
	    }finally {
	      // 关闭OSSClient。
	      if(ossClient != null)
	        ossClient.shutdown();
	      f.delete();
	            dest.delete();
	    }
	        
	  }
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
	}

	public static List<String> fileUploads(Controller controller, String path) {
	    List<UploadFile> files = controller.getFiles();
	    if(files == null){
	      return null;
	    }
        String webRoot = PathKit.getWebRootPath();
        String saveDir = JFinal.me().getConstants().getBaseUploadPath();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        StringBuilder newFileName = new StringBuilder()
            .append("/")
            .append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
        if(StrKit.notBlank(saveDir)){
          newFileName.append("/")
          .append(path);
        }
        Random r = new Random();
        List<String> fileNameList = new ArrayList<>();
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-shenzhen.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAIZEVgm0oUI5tF";
        String accessKeySecret = "l2Ia9aPnljt9brrK9y43eH3hjq9YVP";
        // 创建OSSClient实例。
        OSSClient ossClient = null ;
        try {
        	ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        	for (UploadFile f : files) {
			
			    int x=r.nextInt(999999-100000+1)+100000;
		        newFileName.append("/")
		        .append(dateFormat.format(new Date()))
		            .append("/");
		        StringBuilder fileName = new StringBuilder().append(System.currentTimeMillis()+"_"+x)
		                .append(getFileExt(f.getFileName()));
		        newFileName.append(fileName);
		        File dest = new File(webRoot + newFileName.toString());
		        if (!dest.getParentFile().exists()) {
		          dest.getParentFile().mkdirs();
		        }
		        File source = f.getFile();
		        source.renameTo(dest);
		        
	            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
	            // System.out.println(source.getPath());
	            ossClient.putObject("worldmodel",newFileName.toString().substring(1,newFileName.toString().length()),dest);
	            fileNameList.add(newFileName.toString());
	            f.getFile().delete();
	            source.delete();
	            dest.delete();
        	}
        	return fileNameList;
        } catch (Exception e) {
        	e.printStackTrace();
        	return null ;
        }finally {
        	// 关闭OSSClient。
        	if(ossClient != null)
        		ossClient.shutdown();
        }
	}

	public static String getFileContent(File f) {
		//将文件的内容读取出来
		StringBuilder result = new StringBuilder();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
			//构造一个BufferedReader类来读取文件
	        String s = null;
	        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
	            result.append(System.lineSeparator()+s);
	        }
        	br.close();   
		} catch (Exception e) {
			e.printStackTrace();
		}
        //将文件删除
        f.delete();
        return result.toString(); 
	}
}
