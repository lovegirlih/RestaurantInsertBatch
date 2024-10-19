package org.example.batch.job.processor;

import lombok.extern.slf4j.Slf4j;
import org.example.batch.job.dto.RestaurantDataDto;
import org.example.batch.job.writer.entity.RestaurantData;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RestaurantDataProcessor implements ItemProcessor<RestaurantDataDto, RestaurantData> {

  @Override
  public RestaurantData process(RestaurantDataDto dto) {
    log.info("RestaurantDataDto에서 RestaurantData로 변환을 시작합니다. dto: {}", dto);
    RestaurantData restaurantData = RestaurantData.builder()
      .serviceName(dto.getServiceName())
      .serviceId(dto.getServiceId())
      .municipalityCode(dto.getMunicipalityCode())
      .managementNo(dto.getManagementNo())
      .permitDate(dto.getPermitDate())
      .permitCancelDate(dto.getPermitCancelDate())
      .businessStatusCode(dto.getBusinessStatusCode())
      .businessStatus(dto.getBusinessStatus())
      .detailedStatusCode(dto.getDetailedStatusCode())
      .detailedStatus(dto.getDetailedStatus())
      .closureDate(dto.getClosureDate())
      .suspensionStart(dto.getSuspensionStart())
      .suspensionEnd(dto.getSuspensionEnd())
      .reopeningDate(dto.getReopeningDate())
      .locationPhone(dto.getLocationPhone())
      .locationArea(dto.getLocationArea())
      .locationZipcode(dto.getLocationZipcode())
      .locationAddress(dto.getLocationAddress())
      .roadAddress(dto.getRoadAddress())
      .roadZipcode(dto.getRoadZipcode())
      .businessName(dto.getBusinessName())
      .lastUpdate(dto.getLastUpdate())
      .dataUpdateFlag(dto.getDataUpdateFlag())
      .dataUpdateDate(dto.getDataUpdateDate())
      .businessType(dto.getBusinessType())
      .coordX(dto.getCoordX())
      .coordY(dto.getCoordY())
      .sanitationType(dto.getSanitationType())
      .maleEmployees(dto.getMaleEmployees())
      .femaleEmployees(dto.getFemaleEmployees())
      .surroundingType(dto.getSurroundingType())
      .grade(dto.getGrade())
      .waterSupplyType(dto.getWaterSupplyType())
      .totalEmployees(dto.getTotalEmployees())
      .hqEmployees(dto.getHqEmployees())
      .factoryOfficeStaff(dto.getFactoryOfficeStaff())
      .factorySalesStaff(dto.getFactorySalesStaff())
      .factoryProductionStaff(dto.getFactoryProductionStaff())
      .buildingOwnership(dto.getBuildingOwnership())
      .securityDeposit(dto.getSecurityDeposit())
      .monthlyRent(dto.getMonthlyRent())
      .multiUse(dto.getMultiUse())
      .facilitySize(dto.getFacilitySize())
      .traditionalCode(dto.getTraditionalCode())
      .mainFood(dto.getMainFood())
      .website(dto.getWebsite())
      .build();
    log.info("RestaurantData로 변환을 완료했습니다. restaurantData: {}", restaurantData);
    return restaurantData;
  }

}
