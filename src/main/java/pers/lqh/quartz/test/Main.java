package pers.lqh.quartz.test;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) {
//        try {
//            cronTrigger();
//        } catch (SchedulerException | ParseException e) {
//            e.printStackTrace();
//        }
        testMyJob();
    }

    private static void cronTrigger() throws SchedulerException, ParseException {

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        Scheduler scheduler2 = schedulerFactory.getScheduler();

        System.out.println(scheduler + ":" + scheduler2);


        JobDetail jobDetail1 = JobBuilder.
                newJob(MyJob.class).
                withIdentity("cronTriggerDetail1", "cronTriggerDetailGrounp").
                build();

        JobDetail jobDetail2 = JobBuilder.
                newJob(ACase.class).
                withIdentity("cronTriggerDetail2", "cronTriggerDetailGrounp").
                build();

        String cronExpression = "30/5 * * * * ?"; // 每分钟的30s起，每5s触发任务
/*        CronTrigger cronTrigger = new CronTrigger("cronTrigger",
                Scheduler.DEFAULT_GROUP, cronExpression);*/

        CronTrigger cronTrigger = TriggerBuilder.//和之前的 SimpleTrigger 类似，现在的 CronTrigger 也是一个接口，通过 Tribuilder 的 build()方法来实例化
                newTrigger().
                withIdentity("cronTrigger", "cronTrigger").
                withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)). //在任务调度器中，使用任务调度器的 CronScheduleBuilder 来生成一个具体的 CronTrigger 对象
                build();

        scheduler.scheduleJob(jobDetail1, cronTrigger);
//        scheduler.scheduleJob(jobDetail2, cronTrigger);

        scheduler.start();

    }

    private static void testMyJob() {
        Scheduler s = null;
        try {
            s = StdSchedulerFactory.getDefaultScheduler();
//            s.start();
        } catch (SchedulerException e) {
            s = null;
            e.printStackTrace();
        }
        if (s != null) {

            JobDetail jd2 = JobBuilder.newJob(BCase.class)
                    .withIdentity("job1", "group1")
                    .build();

            Trigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
//                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)
                            .repeatForever())
                    .build();

            JobDetail jd = JobBuilder.newJob(ACase.class)
                    .withIdentity("job2", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger2", "group1")
//                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(1)
                            .repeatForever())
                    .build();

            try {
                s.scheduleJob(jd, trigger);
                s.scheduleJob(jd2, trigger2);
                s.start();

//                long i = 0;
//                while(true){
//                    if(i%1000000 == 0){
//                        System.out.print(i%73);
//                        if(i%73 == 72){
//                            System.out.println();
//                        }
//                    }
//                    i++;
//                }

            } catch (SchedulerException e) {
                try {
                    s.shutdown();
                } catch (SchedulerException e1) {
                    e1.printStackTrace();
                }
                System.out.println("=========任务执行错误===========");
                e.printStackTrace();
            }/*finally{
                try {
                    s.shutdown();
                } catch (SchedulerException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }
}
