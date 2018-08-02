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
                            .withIntervalInSeconds(40)
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
                    System.out.println("请输入0或1:");
                    String rs = s.next();
                    if ("1".equals(rs))
                    {
                        noti();
                    }
                }
            }).start();

//            Thread.sleep(10000);

//            SleepCase.shareObj[0] = "false";

//            Thread.sleep(10000);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static void stop(){

    }

    static void noti()
    {
        synchronized (SleepCase.shareObj)
        {
            SleepCase.shareObj[0] = "false";
            //            scheduler.resumeAll();

            SleepCase.shareObj.notifyAll();
            System.out.println("等待结束!");

        }
    }
}
