package com.cn.jm.web.plugin.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job{

    public TestJob(){
    }

    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("开始时间="+new Date());
        try {
            //JobDataMap dataMap= arg0.getJobDetail().getJobDataMap();
            System.out.println("哇哈哈");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结束时间="+new Date());
    }

}
