package pers.lqh.quartz.py;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Scanner;

public class SleepTest
{
    public static void main(String[] args)
    {
        try
        {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail sleepJob = JobBuilder.newJob(SleepCase.class)
                    .withIdentity("sleepThread", "sleepGroup")
                    .build();

            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("sleepTrigger", "sleepGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(250)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(sleepJob, trigger);
            scheduler.start();

//            scheduler.pauseAll();
//            Thread.sleep(3000);

            TriggerKey key = new TriggerKey("sleepTrigger", "sleepGroup");

//            scheduler.pauseTrigger(key);
//            System.out.println(key);

            new Thread(() ->
            {
                while (true)
                {
                    Scanner s = new Scanner(System.in);
                    System.out.print("请输入0或1:");
                    String rs = s.next();
                    if ("0".equals(rs))
                    {
                        try
                        {
                            System.out.println("执行暂停!");
                            scheduler.pauseTrigger(key);
                            SleepTest.pause();
                        }
                        catch (SchedulerException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else if ("1".equals(rs))
                    {
                        try
                        {
                            System.out.println("继续任务!");
                            SleepTest.noti();
                            scheduler.resumeTrigger(key);
                            System.out.println("等待结束!");
                        }
                        catch (SchedulerException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void pause()
    {
        SleepCase.shareObj[0] = true;
    }

    static void noti()
    {
        synchronized (SleepCase.shareObj)
        {
            SleepCase.shareObj[0] = false;
            //            scheduler.resumeAll();

            SleepCase.shareObj.notifyAll();
        }
    }
}
