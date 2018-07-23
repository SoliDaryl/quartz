package pers.lqh.quartz;

import org.quartz.Job;

public interface Invoke extends Job {
    void invoke();
}
