package com.example;

import java.io.InputStream;
import java.util.Properties;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("quartz.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find quartz.properties");
                System.exit(1);
            }
            properties.load(input);
        }
        // Create SchedulerFactory with properties
        SchedulerFactory schedulerFactory = new StdSchedulerFactory(properties);
        Scheduler scheduler = schedulerFactory.getScheduler();

        // JobDetail job = JobBuilder.newJob(MyJob.class)
        //         .withIdentity("myJob", "group1")
        //         .build();

        // Trigger trigger = TriggerBuilder.newTrigger()
        //         .withIdentity("myTrigger", "group1")
        //         .startNow()
        //         .withSchedule(SimpleScheduleBuilder.simpleSchedule()
        //                 .withIntervalInSeconds(10)
        //                 .repeatForever())
        //         .build();

        // scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }
}