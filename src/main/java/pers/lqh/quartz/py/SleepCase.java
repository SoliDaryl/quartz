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
    static final String[] shareObj = {"true"};
    private int count;
    private boolean stop = false;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException
    {
        System.out.println("----------------------------------");
        init();
        start1();
        destroy1();
        System.out.println("----------------------------------");
    }

    private void init()
    {
        System.out.printf("%s:===============调用初始化方法===============\r\n", LocalDateTime.now());
    }

    private void start1()
    {
        System.out.printf("%s:++++++++++++++++开始执行业务方法++++++++++++++++\r\n", LocalDateTime.now());

        test("pytest1.py", "test1");

/*        try
        {
            System.out.println("##################在3s后执行下一个脚本##################");
//            Thread.sleep(3000);
            this.wait();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }*/

        test("pytest2.py", "test2");

        count++;
        System.out.printf("%s:++++++++++++++++业务方法执行结束：%s，次数为++++++++++++++++\r\n", LocalDateTime.now(), count);


    }

    private void destroy1()
    {
        count = 0;
        System.out.printf("%s:===============销毁============\r\n", LocalDateTime.now());
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
//        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        synchronized (shareObj)
        {
            while("true".equals(shareObj[0])){
                try
                {
                    System.out.println("开始等待!");
                    shareObj.wait();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sleep()
    {

    }

}
