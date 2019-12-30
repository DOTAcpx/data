package com.cn.jm.web.plugin.quartz;

public class Test {
	
	public static void main(String[] args) {
		String startTime = "2017-07-12 22:42:00";
		QuartzFactory.startJobOnce(startTime, 5, "test", "testgroup", TestJob.class);
		
		
		
//		String startTime = "2016-12-26 0:";
//		for (int i = 0; i < 1000; i++) {
//			int j = i%60;
//			int k = i/60;
//			startTime = startTime+(26+k)+":"+j;
//			System.err.println(startTime);
//			QuartzFactory.startJobOnce(startTime, 5, "test"+i, "testgroup", TestJob.class);
//		}
		
	}

}
