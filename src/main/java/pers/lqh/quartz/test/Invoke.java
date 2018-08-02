package pers.lqh.quartz.test;

import org.quartz.Job;

public interface Invoke extends Job {
    void invoke();
}
