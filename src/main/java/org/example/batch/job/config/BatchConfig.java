package org.example.batch.job.config;

import org.example.batch.job.dto.RestaurantDataDto;
import org.example.batch.job.processor.RestaurantDataProcessor;
import org.example.batch.job.reader.ExcelReader;
import org.example.batch.job.writer.DatabaseWriter;
import org.example.batch.job.writer.entity.RestaurantData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  private final ExcelReader excelReader;
  private final DatabaseWriter databaseWriter;
  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;
  private final RestaurantDataProcessor restaurantDataProcessor;

  public BatchConfig(ExcelReader excelReader, DatabaseWriter databaseWriter,
                     JobRepository jobRepository, PlatformTransactionManager transactionManager,
                     RestaurantDataProcessor restaurantDataProcessor
  ) {
    this.excelReader = excelReader;
    this.databaseWriter = databaseWriter;
    this.jobRepository = jobRepository;
    this.transactionManager = transactionManager;
    this.restaurantDataProcessor = restaurantDataProcessor;
  }

  @Bean
  public Job importJob() {
    return new JobBuilder("importJob", jobRepository)
      .start(importStep())
      .build();
  }

  @Bean
  public Step importStep() {
    return new StepBuilder("importStep", jobRepository)
      .<RestaurantDataDto, RestaurantData>chunk(1000, transactionManager)
      .reader(excelReader)                        // Reader는 RestaurantDataDto를 읽음
      .processor(restaurantDataProcessor)         // Processor가 RestaurantDataDto를 RestaurantData로 변환
      .writer(databaseWriter)                     // Writer는 변환된 RestaurantData를 처리
      .build();
  }

}