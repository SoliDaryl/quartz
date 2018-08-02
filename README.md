# quartz

quartz是用于定时调度控制的插件 结合时间表达式使用可以完成复杂的任务调度工作

1.先使用StdSchedulerFactory的方法获得调度程序Scheduler类

2.使用JobBuilder.newJob(MyJob.class)添加自己的需要执行的任务类,(Myjob实现了org.quartz.Job接口,任务代码写在execute()方法中);

3.使用TriggerBuilder.newTrigger获得一个心的TriggerBuilder对象,通过TriggerBuilder对象的方法来设置参数
//其中withSchedule()方法设置的参数为不同的计划生成器,为ScheduleBuilder的子类,其有三个子类
//1)SimpleScheduleBuilder:可以设置间隔时分秒执行,同时设置执行次数或重复执行
//2)CalendarIntervalScheduleBuilder:日历间隔生成器,可以设置day, week, month, year属性
