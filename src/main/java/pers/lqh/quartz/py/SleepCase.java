package pers.lqh.quartz.py;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.quartz.*;

import java.time.LocalDateTime;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SleepCase implements Job
{
//    static final boolean[] shareObj = {false};
    private int count;
    private boolean stop = false;
    private String lockName;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException
    {
        System.out.println("----------------------------------");

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        int num = jobDataMap.getIntValue("num");
        lockName = jobDataMap.getString("lockName");
        init();

        start1(num);
        destroy1();
        System.out.println("----------------------------------");
    }

    private void init()
    {
        System.out.println();
        System.out.printf("%s:===============调用初始化方法===============\r\n", LocalDateTime.now());
        JobLock.lockMap.put(lockName, new Boolean[]{false,false});
    }

    private void start1(int num)
    {
        System.out.printf("%s:++++++++++++++++开始执行业务方法++++++++++++++++\r\n", LocalDateTime.now());

        for (int i = 0; i < num; i++)
        {
            System.out.println("当前for循环i为:" + i);

            synchronized (JobLock.lockMap.get(lockName))
            {
                while (JobLock.lockMap.get(lockName)[0])
                {
                    try
                    {
                        System.out.println("开始等待!");
//                    shareObj.wait();
                        JobLock.lockMap.get(lockName)[1] = true;
                        JobLock.lockMap.get(lockName).wait();
                    }
                    catch (InterruptedException e)
                    {
                        JobLock.lockMap.get(lockName)[1] = false;
                        e.printStackTrace();
                    }
                }
            }

            test("pytest2.py", "test2");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }

        test("pytest1.py", "test1");
        count++;
        System.out.printf("%s:++++++++++++++++业务方法执行结束，次数为：%s++++++++++++++++\r\n", LocalDateTime.now(), count);
    }

    private void destroy1()
    {
        System.out.printf("%s:===============销毁============\r\n", LocalDateTime.now());
        System.out.println();
        JobLock.lockMap.remove(lockName);
    }

    private void test(String file, String pyFunction)
    {
        System.out.printf("%s:===============开始执行一个脚本=================\r\n", LocalDateTime.now());
        String s = System.getProperty("user.dir") + "/" + file;
        PythonInterpreter pi = null;
        PyObject pyObject = null;
        try
        {
            pi = new PythonInterpreter();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (pi != null)
        {
            pi.execfile(s);
            PyFunction pf = pi.get(pyFunction, PyFunction.class);
            pyObject = pf.__call__();
            pi.close();
        }
        System.out.printf("%s:==============脚本执行结束：%s============\r\n", LocalDateTime.now(), pyObject);
    }
}
