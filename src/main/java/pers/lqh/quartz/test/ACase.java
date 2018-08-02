package pers.lqh.quartz.test;

public class ACase extends BaseJob implements Invoke {
    @Override
    public void invoke() {
        System.out.println("=========调用了”A“实例的方法==========");
    }
}
