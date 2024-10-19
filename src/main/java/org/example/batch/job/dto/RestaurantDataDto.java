package org.example.batch.job.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDataDto {

  private String serviceName;
  private String serviceId;
  private String municipalityCode;
  private String managementNo;
  private LocalDate permitDate;
  private LocalDate permitCancelDate;
  private String businessStatusCode;
  private String businessStatus;
  private String detailedStatusCode;
  private String detailedStatus;
  private LocalDate closureDate;
  private LocalDate suspensionStart;
  private LocalDate suspensionEnd;
  private LocalDate reopeningDate;
  private String locationPhone;
  private BigDecimal locationArea;
  private String locationZipcode;
  private String locationAddress;
  private String roadAddress;
  private String roadZipcode;
  private String businessName;
  private LocalDateTime lastUpdate;
  private String dataUpdateFlag;
  private LocalDate dataUpdateDate;
  private String businessType;
  private BigDecimal coordX;
  private BigDecimal coordY;
  private String sanitationType;
  private Integer maleEmployees;
  private Integer femaleEmployees;
  private String surroundingType;
  private String grade;
  private String waterSupplyType;
  private Integer totalEmployees;
  private Integer hqEmployees;
  private Integer factoryOfficeStaff;
  private Integer factorySalesStaff;
  private Integer factoryProductionStaff;
  private String buildingOwnership;
  private BigDecimal securityDeposit;
  private BigDecimal monthlyRent;
  private Boolean multiUse;
  private BigDecimal facilitySize;
  private String traditionalCode;
  private String mainFood;
  private String website;

}