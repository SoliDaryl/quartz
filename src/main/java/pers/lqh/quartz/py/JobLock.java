package pers.lqh.quartz.py;

import java.util.HashMap;
import java.util.Map;

public class JobLock
{
    static Map<String,Boolean[]> lockMap = new HashMap<>();
}
