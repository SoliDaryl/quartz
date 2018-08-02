package pers.lqh.quartz;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;
import org.quartz.*;

import java.time.LocalDateTime;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SleepCase implements Job {
    public static int count;
    private boolean stop = false;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("----------------------------------");
        init();
        start1();


        destroy1();

        System.out.println("----------------------------------");
    }

    private void init() {
        System.out.printf("===============调用初始化方法:%s=================\r\n", LocalDateTime.now());
    }

    private synchronized void start1() {
        System.out.printf("==============开始执行业务方法%s============\r\n", LocalDateTime.now());
        PythonInterpreter pi = new PythonInterpreter();
        String s = System.getProperty("user.dir") + "/pytest1.py";

        pi.execfile(s);
        PyFunction pf = pi.get("hello",PyFunction.class);
        PyObject pyObject = pf.__call__();
        System.out.println(pyObject);
        System.out.printf("==============%s%s============\r\n", pyObject,LocalDateTime.now());

        count++;
        System.out.printf("==============业务方法执行结束：%s，次数为：%s============\r\n", LocalDateTime.now(),count);

    }

    private void destroy1() {
        count = 0;
        System.out.printf("===============销毁:%s============\r\n", LocalDateTime.now());
    }

}
