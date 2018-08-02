package pers.lqh.quartz;

import org.quartz.*;

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

//        while(!stop){
////            if(count==50) stop=true;
////            start1();
//            if(count==50){
//                try {
//                    System.out.println("睡眠");
//                    Thread.sleep(10000);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        try {
//            this.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        while(count<200){
//            start1();
//        }

        while(true){
            start1();
        }
//        destroy1();

//        System.out.println("----------------------------------");
    }

//    SleepCase(){
//        System.out.println("================构造函数================");
//    }

    private void init() {
        System.out.println("===============调用初始化方法=================");
    }

    private synchronized void start1() {
        count++;
        System.out.println("==============执行业务方法============执行次数为:" + count);
    }

    private void destroy1() {
        count = 0;
        System.out.println("===============销毁============");
    }

}
