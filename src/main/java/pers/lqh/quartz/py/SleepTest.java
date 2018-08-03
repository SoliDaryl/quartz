package pers.lqh.quartz.py;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Scanner;
import java.util.UUID;

public class SleepTest
{
    public static void main(String[] args)
    {
        try
        {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            String lockName = String.valueOf(UUID.randomUUID());
            JobDetail sleepJob = JobBuilder.newJob(SleepCase.class)
                    .withIdentity("sleepThread", "sleepGroup")
                    .usingJobData("num", 3)
                    .usingJobData("lockName", lockName)
                    .build();

            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("sleepTrigger", "sleepGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(40)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(sleepJob, trigger);
            scheduler.start();

            TriggerKey key = new TriggerKey("sleepTrigger", "sleepGroup");

            new Thread(() ->
            {
                while (true)
                {
                    Scanner s = new Scanner(System.in);
                    System.out.println("请输入0或1:");
                    String rs = s.next();
                    if ("0".equals(rs))
                    {
                        try
                        {
                            System.out.println("执行暂停!");
                            scheduler.pauseTrigger(key);
                            JobLock.pause(lockName);
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
                            JobLock.noti(lockName);
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

}
