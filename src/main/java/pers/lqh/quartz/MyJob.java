package pers.lqh.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Time;
import java.time.LocalDateTime;

public class MyJob implements org.quartz.Job {
    public void execute(JobExecutionContext jobExecutionContext) {
        LocalDateTime t = LocalDateTime.now();
        System.out.printf("\r\n============Hello World!当前时间是:%s==============\r\n",t);
    }
}
