package com.cn.jm.core.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.jfinal.kit.PathKit;
import com.jfinal.log.Log;

public class ToolProp {
	private static final Log log = Log.getLog(ToolProp.class);
	
	/**
	 * 
	 * @param properties
	 * @param name:文件名
	 * @return
	 */
	public static boolean createDbProperties(Properties properties,String name) {
		File pFile = new File(PathKit.getRootClassPath(), name+".properties");
		return save(properties, pFile);
	}
	
	private static boolean save(Properties p, File pFile) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(pFile);
			p.store(fos, "Auto create by JPress");
		} catch (Exception e) {
			log.warn("InstallUtils save erro", e);
			return false;
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e) {
				}
		}
		return true;
	}

}
