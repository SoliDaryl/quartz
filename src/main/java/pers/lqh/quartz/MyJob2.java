package pers.lqh.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class MyJob2 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.printf("\r\n++++++++++++myJob2,当前时间为:%s+++++++++++++\r\n",ldt);
    }
}
