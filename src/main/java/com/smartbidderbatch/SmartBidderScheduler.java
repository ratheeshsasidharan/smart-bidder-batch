package com.smartbidderbatch;

import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@AllArgsConstructor
public class SmartBidderScheduler {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Scheduled(cron="*/10 * * * * *")
    public void myScheduler(){
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            System.out.println("Job's Status:::"+jobExecution.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
