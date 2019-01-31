package com.cn.jm.web.plugin.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class TestJobListener implements JobListener  {

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext arg0) {
		System.out.println("MyJobListener.jobToBeExecuted()");
		
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext arg0) {
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext arg0,JobExecutionException arg1) {
		
	}

}
