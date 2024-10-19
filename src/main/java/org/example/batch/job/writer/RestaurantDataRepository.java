package org.example.batch.job.writer;

import org.example.batch.job.writer.entity.RestaurantData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDataRepository extends JpaRepository<RestaurantData, Long> {

}