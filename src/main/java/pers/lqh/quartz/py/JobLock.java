package pers.lqh.quartz.py;

import java.util.HashMap;
import java.util.Map;

public class JobLock
{
    static Map<String,Boolean[]> lockMap = new HashMap<>();

    static void pause(String lockName)
    {
        if(lockMap.get(lockName) != null){
            lockMap.get(lockName)[0] = true;
        }
//        SleepCase.shareObj[0] = true;
    }

    static void noti(String lockName)
    {
        if(lockMap.get(lockName) != null){
            synchronized (lockMap.get(lockName))
            {
                if (lockMap.get(lockName)[1])
                {
                    lockMap.get(lockName)[0] = false;
                    lockMap.get(lockName).notify();
                    lockMap.get(lockName)[1] = false;
                }
            }
        }
    }
}
