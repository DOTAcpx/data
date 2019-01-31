package com.cn.jm.other.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 每隔5秒执行的任务
 * @author 李劲
 *
 * 2017年10月29日 上午10:55:17
 */
public class EveryFiveJob implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		//处理广告
//		JMAdsDao.jmd.inspect();
	}

}
