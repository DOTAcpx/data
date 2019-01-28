package com.cn.jm.web.plugin.quartz;

import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.IPlugin;

public class QuartzPlugin implements IPlugin{

    /**默认配置文件**/
    private String config = "quartz.properties";

    public QuartzPlugin(){

    }

    public QuartzPlugin(String config){
        this.config = config;
    }

    @Override
    public boolean start() {
        try {
            //加载配置文件
            Properties props = PropKit.use(config).getProperties();
            //实例化
            QuartzFactory.sf = new StdSchedulerFactory(props);
            //获取Scheduler
            Scheduler sched = QuartzFactory.sf.getScheduler();
            sched.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean stop() {
        try {
            QuartzFactory.sf.getScheduler().shutdown();
            QuartzFactory.sf = null;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
