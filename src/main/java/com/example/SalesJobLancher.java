package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SalesJobLancher {

	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job salesJob;
	
	private JobParameters jobParameters;
	
	@Scheduled(cron = "0 0 17 * * *", zone = "Asia/Tokyo")
	public void batch() throws Exception {
		jobParameters = salesJob.getJobParametersIncrementer().getNext(jobParameters);
		jobLauncher.run(salesJob, jobParameters);
	}
	
}
