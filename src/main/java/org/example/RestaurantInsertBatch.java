package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@Slf4j
@SpringBootApplication
@EnableBatchProcessing
public class RestaurantInsertBatch implements CommandLineRunner {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job importJob;  // 설정된 Job을 가져옴

  @Autowired
  private JobExplorer jobExplorer;

  public static void main(String[] args) {
    SpringApplication.run(RestaurantInsertBatch.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    // 실행중인 job 정리
    stopRunningJobs("importJob");

    // 새로운 parameter로 Job 실행
    JobParameters jobParameters = new JobParametersBuilder()
      .addLong("time", System.currentTimeMillis()).toJobParameters();

    JobExecution jobExecution = jobLauncher.run(importJob, jobParameters);
    log.info("Job Status: {}", jobExecution.getStatus());

    jobExecution.getStepExecutions().forEach(stepExecution -> {
      log.info("Step Status: {}", stepExecution.getStatus());
      log.info("Step Name: {}", stepExecution.getStepName());

      if (stepExecution.getStatus() == BatchStatus.COMPLETED) {
        log.info("모든 배치 작업이 완료되었습니다.");
      } else if (stepExecution.getStatus() == BatchStatus.FAILED) {
        log.warn("배치 작업이 실패했습니다.");
      }

    });
  }

  // 실행 중인 Job을 중지시키는 메서드
  private void stopRunningJobs(String jobName) {
    Set<JobExecution> runningJobs = jobExplorer.findRunningJobExecutions(jobName);
    for (JobExecution jobExecution : runningJobs) {
      System.out.println("Stopping Job Execution: " + jobExecution.getId());
      jobExecution.setStatus(BatchStatus.STOPPED);
      jobExecution.setExitStatus(ExitStatus.FAILED);
    }
  }
}