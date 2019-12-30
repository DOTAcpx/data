package com.cn.jm.web.plugin.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Collection;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.cn.jm.core.tool.ToolDateTime;

/**
 *
 * @author jin
 *
 */
public class QuartzFactory {

	public static SchedulerFactory sf;

	/**
	 * 定时开始任务
	 * 
	 * @param startTime
	 * @param id
	 * @param name
	 * @param group
	 * @param jobClass
	 */
	public static void startJobOnce(String startTime, int id, String name, String group,
			Class<? extends Job> jobClass) {
		try {
			Scheduler sched = sf.getScheduler();
			// define the job and tie it to our HelloJob class
			JobDetail job = newJob(jobClass).withIdentity("job_" + name + "_" + id, "group_" + group + "_" + id)
					.requestRecovery().build();
			job.getJobDataMap().put(group + "_" + name, id);
			// 定时执行
			SimpleTrigger trigger = (SimpleTrigger) newTrigger()
					.withIdentity("trigger_" + name + "_" + id, "group_" + group + "_" + id)
					.startAt(ToolDateTime.parse(startTime, ToolDateTime.pattern_ymd_hms)).build();

			sched.scheduleJob(job, trigger);
			sched.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 定时开始任务
	 * 
	 * @param cron
	 * @param id
	 * @param name
	 * @param group
	 * @param jobClass
	 */
	public static void startJobCron(String cron, int id, String name, String group, Class<? extends Job> jobClass) {
		try {
			Scheduler sched = sf.getScheduler();
			// define the job and tie it to our HelloJob class
			JobDetail job = newJob(jobClass).withIdentity("job_" + name + "_" + id, "group_" + group + "_" + id)
					.requestRecovery().build();
			job.getJobDataMap().put(group + "_" + name, id);
			// 定时执行
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group)
					.withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
			sched.scheduleJob(job, trigger);
			sched.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 停止任务
	 * 
	 * @param name
	 * @param group
	 * @param id
	 */
	public static void stopJob(String name, String group, int id) {
		try {
			if (sf != null) {
				Scheduler scheduler = sf.getScheduler();
				TriggerKey triggerKey = TriggerKey.triggerKey("trigger_" + name + "_" + id,
						"group_" + group + "_" + id);
				Trigger trigger = scheduler.getTrigger(triggerKey);
				if (trigger != null) {
					scheduler.pauseTrigger(triggerKey);
					scheduler.unscheduleJob(triggerKey);
					scheduler.deleteJob(trigger.getJobKey());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description: 全部关闭
	 */
	public static void stopJob() {
		try {
			if (sf != null) {

				Collection<Scheduler> list = sf.getAllSchedulers();

				for (Scheduler scheduler : list) {
					scheduler.shutdown();
				}
				//
				// Scheduler scheduler = sf.getScheduler();
				// scheduler.shutdown();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
