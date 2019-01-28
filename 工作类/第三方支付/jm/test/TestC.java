package com.cn.jm.test;

import com.cn.jm.web.core.JMConsts;
import com.cn.jm.web.core.router.JMRouterMapping;
import com.jfinal.core.Controller;

@JMRouterMapping(url = TestC.url, viewPath = "")
public class TestC extends Controller{
	
	public static final String url ="/test";
	
	public static final String path = JMConsts.base_view_url+"/temp";
	
	public void index(){
		render(path+"/base/add.jsp");
	}
	
	public void select(){
		render(path+"/select/add.jsp");
	}
	
	public void page(){
		render(path+"/page.jsp");
	}


}
