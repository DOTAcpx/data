package com.cn.jm.other.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 每天0点执行任务
 * @author 李劲
 *
 * 2017年11月9日 下午10:11:05
 */
public class EveryZeroJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	}

}
