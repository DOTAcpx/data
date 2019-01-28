package com.cn.jm.web.core;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.cn.jm.core.tool.JMToolString;
import com.jfinal.core.JFinal;

/**
 * 项目入口
 * 
 * @author 李劲
 *
 *         2017年9月8日 上午2:28:20
 */
public class JM {

	public static final String EVENT_STARTED = "xp:started";
	private static Map<String, String> argMap;
	private String projectName = "";//项目名称
	
	private static JM jm = new JM();

	public static JM me() {
		return jm;
	}

	/**
	 * main 入口方法
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		run(args);
	}

	public static void run(String[] args) {
		parseArgs(args);
		jm.start();
	}

	/**
	 * 解析启动参数
	 *
	 * @param args
	 */
	private static void parseArgs(String[] args) {
		if (args == null || args.length == 0) {
			return;
		}

		for (String arg : args) {
			int indexOf = arg.indexOf("=");
			if (arg.startsWith("--") && indexOf > 0) {
				String key = arg.substring(2, indexOf);
				String value = arg.substring(indexOf + 1);
				setBootArg(key, value);
			}
		}
	}

	public static void setBootArg(String key, Object value) {
		if (argMap == null) {
			argMap = new HashMap<>();
		}
		argMap.put(key, value.toString());
	}

	/**
	 * 获取启动参数
	 *
	 * @param key
	 * @return
	 */
	public static String getBootArg(String key) {
		if (argMap == null)
			return null;
		return argMap.get(key);
	}

	/**
	 * 开始启动
	 */
	public void start() {
		printServerPath();

		tryToHoldApplication();
		
		if(JMToolString.isEmpty(getProjectName()) || getProjectName().equals("xiaopao")){
			System.err.println("项目名称不能为空");
            return;
		}
		
	}

	private void tryToHoldApplication() {
		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void printServerPath() {
		System.out.println("server classPath    : " + getRootClassPath());
	}

	private static String getRootClassPath() {
		String path = null;
		try {
			path = JM.class.getClassLoader().getResource("").toURI().getPath();
			return new File(path).getAbsolutePath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 是否是开发模式
	 *
	 * @return
	 */
	public boolean isDevMode() {
		return JFinal.me().getConstants().getDevMode();
	}
	
	
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}
	
	public String getProjectName(){
		return this.projectName;
	}

}
