package org.example.batch.job.writer.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "restaurant_standard_data")
public class RestaurantData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "service_name", nullable = false, length = 50)
  private String serviceName;

  @Column(name = "service_id", nullable = false, length = 20)
  private String serviceId;

  @Column(name = "municipality_code", nullable = false, length = 10)
  private String municipalityCode;

  @Column(name = "management_no", nullable = false, length = 30)
  private String managementNo;

  @Column(name = "permit_date", nullable = false)
  private LocalDate permitDate;

  @Column(name = "permit_cancel_date")
  private LocalDate permitCancelDate;

  @Column(name = "business_status_code", length = 5)
  private String businessStatusCode;

  @Column(name = "business_status", length = 20)
  private String businessStatus;

  @Column(name = "detailed_status_code", length = 5)
  private String detailedStatusCode;

  @Column(name = "detailed_status", length = 20)
  private String detailedStatus;

  @Column(name = "closure_date")
  private LocalDate closureDate;

  @Column(name = "suspension_start")
  private LocalDate suspensionStart;

  @Column(name = "suspension_end")
  private LocalDate suspensionEnd;

  @Column(name = "reopening_date")
  private LocalDate reopeningDate;

  @Column(name = "location_phone", length = 20)
  private String locationPhone;

  @Column(name = "location_area", precision = 10, scale = 2)
  private BigDecimal locationArea;

  @Column(name = "location_zipcode", length = 10)
  private String locationZipcode;

  @Column(name = "location_address", columnDefinition = "TEXT")
  private String locationAddress;

  @Column(name = "road_address", columnDefinition = "TEXT")
  private String roadAddress;

  @Column(name = "road_zipcode", length = 10)
  private String roadZipcode;

  @Column(name = "business_name", length = 100)
  private String businessName;

  @Column(name = "last_update")
  private LocalDateTime lastUpdate;

  @Column(name = "data_update_flag", length = 1)
  private String dataUpdateFlag;

  @Column(name = "data_update_date")
  private LocalDate dataUpdateDate;

  @Column(name = "business_type", length = 50)
  private String businessType;

  @Column(name = "coord_x", precision = 15, scale = 6)
  private BigDecimal coordX;

  @Column(name = "coord_y", precision = 15, scale = 6)
  private BigDecimal coordY;

  @Column(name = "sanitation_type", length = 50)
  private String sanitationType;

  @Column(name = "male_employees")
  private Integer maleEmployees;

  @Column(name = "female_employees")
  private Integer femaleEmployees;

  @Column(name = "surrounding_type", length = 50)
  private String surroundingType;

  @Column(name = "grade", length = 20)
  private String grade;

  @Column(name = "water_supply_type", length = 20)
  private String waterSupplyType;

  @Column(name = "total_employees")
  private Integer totalEmployees;

  @Column(name = "hq_employees")
  private Integer hqEmployees;

  @Column(name = "factory_office_staff")
  private Integer factoryOfficeStaff;

  @Column(name = "factory_sales_staff")
  private Integer factorySalesStaff;

  @Column(name = "factory_production_staff")
  private Integer factoryProductionStaff;

  @Column(name = "building_ownership", length = 50)
  private String buildingOwnership;

  @Column(name = "security_deposit", precision = 15, scale = 2)
  private BigDecimal securityDeposit;

  @Column(name = "monthly_rent", precision = 15, scale = 2)
  private BigDecimal monthlyRent;

  @Column(name = "multi_use")
  private Boolean multiUse;

  @Column(name = "facility_size", precision = 10, scale = 2)
  private BigDecimal facilitySize;

  @Column(name = "traditional_code", length = 50)
  private String traditionalCode;

  @Column(name = "main_food", length = 100)
  private String mainFood;

  @Column(name = "website", length = 255)
  private String website;

}
