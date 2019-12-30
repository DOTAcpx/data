package com.cn.jm.web.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cn.jm.web.core.db.JMDbSourceMeta;

/**
 * 项目全局变量
 * @author 李劲
 *
 * 2017年10月1日 下午8:34:50
 */
public class JMConsts {
	
	public static String projectName;
	public static final String VERSION = "0.1.0";//系统版本号
	public static int ThreadPoolCount = 10;//线程池启动线程的总数
	public static ExecutorService threadPool = Executors.newFixedThreadPool(ThreadPoolCount);//线程池
	
	public static String ROOT_PATH = "./";
	public static String PLACEHOLDER = "%s";
	
	public static List<JMDbSourceMeta> dbSourceMetaList = new ArrayList<JMDbSourceMeta>();
	
	public static final int ACTION_DETAIL = 0;//详情页面
	public static final int ACTION_ADD = 1;//添加页面
	public static final int ACTION_EDIT = 2;//编辑页面
	
	public static HashMap<String, Object> temporaryCache = new HashMap<String, Object>();
	
	
	
	public static void setTemporaryCacheAttr(String name, Object value) {
		if (temporaryCache == null) {
			temporaryCache = new HashMap<>();
        }
		temporaryCache.put(name, value);
	}
	
	public static <T> T getTemporaryCacheAttr(String name) {
		return JMConsts.temporaryCache == null ? null : (T) JMConsts.temporaryCache.get(name);
	}
	    
	
	/**
	 * 用户登录验证超时时间
	 */
	public static int token_time = 14*24*60*60*1000;
	
	public static String  base_view_url = "/WEB-INF/view";
	
	public static int pageSize = 20 ;
	
}

