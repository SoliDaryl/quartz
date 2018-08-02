package pers.lqh.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class SleepTest {
    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail sleepJob = JobBuilder.newJob(SleepCase.class)
                    .withIdentity("sleepThread", "sleepGroup")
                    .build();

            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("sleepTrigger", "sleepGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();
            scheduler.scheduleJob(sleepJob, trigger);
            scheduler.start();


//            scheduler.pauseAll();
//            scheduler.pauseTrigger();
//            Thread.sleep(3000);

            TriggerKey key = new TriggerKey("sleepTrigger", "sleepGroup");

            scheduler.pauseTrigger(key);
            System.out.println(key);

//            Thread.sleep(10000);

            scheduler.resumeAll();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
