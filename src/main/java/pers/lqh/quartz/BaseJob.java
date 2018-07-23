package pers.lqh.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseJob implements Job,Invoke {

//    private Class<T> clazz;


    BaseJob(){
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        this.invoke();
    }

    @Override
    public void invoke(){
        System.out.println("======调用了基类的方法========");
    }
}
