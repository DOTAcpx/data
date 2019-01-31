package com.cn.jm.core.tool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;

public class ToolUpload {
	
	public static String getFileExt(String fileName) {
	    return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
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
            .append("/")
            .append(System.currentTimeMillis()+"_"+x)
            .append(getFileExt(f.getFileName()));
        File dest = new File(webRoot + newFileName.toString());
        if (!dest.getParentFile().exists()) {
        	dest.getParentFile().mkdirs();
        }
        File source = f.getFile();
        source.renameTo(dest);
        return newFileName.toString();
	}
	
	/**
	 * 生成路径
	 */
	public static String getPath(String path){
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
        newFileName.append("/").append(dateFormat.format(new Date()));
        Random r = new Random();
		int x=r.nextInt(999999-100000+1)+100000;
        newFileName.append("/").append(System.currentTimeMillis()+"_"+x);
        File dest = new File(webRoot + newFileName.toString());
        if (!dest.getParentFile().exists()) {
        	dest.getParentFile().mkdirs();
        }
        return newFileName.toString();
	}
	
	/**
	 * 生成路径
	 */
	public static String getTimePath(String path){
        String saveDir = JFinal.me().getConstants().getBaseUploadPath();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        StringBuilder newFileName = new StringBuilder()
            .append("/")
            .append(StrKit.isBlank(saveDir) ? "upload" : saveDir);
        if(StrKit.notBlank(saveDir)){
        	newFileName.append("/")
        	.append(path);
        }
        newFileName.append("/").append(dateFormat.format(new Date()));
        return newFileName.toString();
	}
	
	public static String getThumbnail(String name, String img){
		return getPath(name)+getFileExt(img);
	}
	
	public static void deleteFile(String... values){
		String filePath = PathKit.getWebRootPath();
		for (String value : values) {
			if(value != null){
				File file = new File(filePath+value);
				if (file.exists() && file.isFile()) {
					file.delete();
				}
			}
		}
	}
	
}
