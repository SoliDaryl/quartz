package pers.lqh.quartz.test;

public class BCase extends BaseJob {
    @Override
    public void invoke() {
        System.out.println("==========调用了”B“实例的方法=========");
    }
}
