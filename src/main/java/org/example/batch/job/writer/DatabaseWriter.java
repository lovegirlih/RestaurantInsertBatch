package org.example.batch.job.writer;

import lombok.extern.slf4j.Slf4j;
import org.example.batch.job.writer.entity.RestaurantData;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseWriter implements ItemWriter<RestaurantData> {

  private final RestaurantDataRepository repository;

  public DatabaseWriter(RestaurantDataRepository repository) {
    this.repository = repository;
  }

  @Override
  public void write(Chunk<? extends RestaurantData> chunk) throws Exception {
    log.info("RestaurantData를 MySQL에 저장을 시작합니다. 저장할 데이터 수: {}", chunk.size());
    repository.saveAll(chunk.getItems());
    log.info("RestaurantData를 MySQL에 저장을 완료했습니다.");
  }

}