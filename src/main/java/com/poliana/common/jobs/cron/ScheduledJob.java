package com.poliana.common.jobs.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledJob {

    public ScheduledJob() {
    }

    @Scheduled(fixedRate=3)
    public void myFirstScheduleMethod() {
        System.out.println("this is on a schedule!");
    }
}
