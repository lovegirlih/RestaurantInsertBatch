import org.example.batch.job.config.BatchConfig;
import org.example.batch.job.dto.RestaurantDataDto;
import org.example.batch.job.processor.RestaurantDataProcessor;
import org.example.batch.job.reader.ExcelReader;
import org.example.batch.job.writer.DatabaseWriter;
import org.example.batch.job.writer.entity.RestaurantData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Sql(scripts = "/org/springframework/batch/core/schema-h2.sql")
@ExtendWith(SpringExtension.class)
@SpringBatchTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = {BatchTestConfig.class, BatchConfig.class})  // BatchConfig를 포함시켜 Job을 주입
@ActiveProfiles("test")
public class BatchConfigTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @MockBean
  private ExcelReader excelReader;

  @MockBean
  private RestaurantDataProcessor restaurantDataProcessor;

  @MockBean
  private DatabaseWriter databaseWriter;

  @BeforeEach
  void setup() {
    // 테스트 데이터 설정
    RestaurantDataDto dto = new RestaurantDataDto();
    dto.setServiceName("Test Service");

    when(excelReader.read()).thenReturn(dto, (RestaurantDataDto) null);

    RestaurantData restaurantData = RestaurantData.builder().serviceName("Test Service").build();
    when(restaurantDataProcessor.process(dto)).thenReturn(restaurantData);
  }

  @Test
  void testJobExecution() throws Exception {
    JobExecution jobExecution = jobLauncherTestUtils.launchJob();
    assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
  }

  @Test
  void testStepExecution() throws Exception {
    JobExecution stepExecution = jobLauncherTestUtils.launchStep("importStep");
    assertThat(stepExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
  }

  @Test
  void testProcessor() throws Exception {
    RestaurantDataDto dto = excelReader.read();
    assertThat(dto).isNotNull();
    assertThat(dto.getServiceName()).isEqualTo("Test Service");

    RestaurantData restaurantData = restaurantDataProcessor.process(dto);
    assertThat(restaurantData).isNotNull();
    assertThat(restaurantData.getServiceName()).isEqualTo("Test Service");
  }

  @Test
  void testWriter() throws Exception {
    RestaurantData restaurantData = RestaurantData.builder().serviceName("Test Service").build();
    databaseWriter.write(new org.springframework.batch.item.Chunk<>(List.of(restaurantData)));

    Mockito.verify(databaseWriter, Mockito.times(1)).write(Mockito.any());
  }
}