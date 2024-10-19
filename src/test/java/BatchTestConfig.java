import org.h2.tools.Server;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class BatchTestConfig {

  @Bean
  public JobLauncherTestUtils jobLauncherTestUtils(JobRepository jobRepository, Job job) {
    JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
    jobLauncherTestUtils.setJobRepository(jobRepository);
    jobLauncherTestUtils.setJob(job);  // Job을 명시적으로 설정
    return jobLauncherTestUtils;
  }

  @Bean
  public PlatformTransactionManager transactionManager(DataSource dataSource) {
    return new DataSourceTransactionManager(dataSource);
  }

  @Bean
  public JobCompletionListener jobCompletionListener() {
    return new JobCompletionListener();  // JobCompletionListener 빈 추가
  }

  // H2 Database 서버 인스턴스 생성
  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server h2Server() throws SQLException {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
  }

}