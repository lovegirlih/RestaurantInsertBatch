package org.example.batch.job.reader;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.example.batch.job.dto.RestaurantDataDto;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Component
public class ExcelReader implements ItemReader<RestaurantDataDto> {

  private Iterator<Row> rowIterator = null;
  private final Map<String, Integer> headerIndexMap = new HashMap<>();

  // 엑셀 헤더와 DTO 필드 이름 간의 매핑 설정 (엑셀 헤더로 한글을 셋팅해야함)
  private final Map<String, String> customHeaderMapping = Map.ofEntries(
    Map.entry("개방서비스명", "serviceName"),
    Map.entry("개방서비스아이디", "serviceId"),
    Map.entry("개방자치단체코드", "municipalityCode"),
    Map.entry("관리번호", "managementNo"),
    Map.entry("인허가일자", "permitDate"),
    Map.entry("인허가취소일자", "permitCancelDate"),
    Map.entry("영업상태구분코드", "businessStatusCode"),
    Map.entry("영업상태명", "businessStatus"),
    Map.entry("상세영업상태코드", "detailedStatusCode"),
    Map.entry("상세영업상태명", "detailedStatus"),
    Map.entry("폐업일자", "closureDate"),
    Map.entry("휴업시작일자", "suspensionStart"),
    Map.entry("휴업종료일자", "suspensionEnd"),
    Map.entry("재개업일자", "reopeningDate"),
    Map.entry("소재지전화", "locationPhone"),
    Map.entry("소재지면적", "locationArea"),
    Map.entry("소재지우편번호", "locationZipcode"),
    Map.entry("소재지전체주소", "locationAddress"),
    Map.entry("도로명전체주소", "roadAddress"),
    Map.entry("도로명우편번호", "roadZipcode"),
    Map.entry("사업장명", "businessName"),
    Map.entry("최종수정시점", "lastUpdate"),
    Map.entry("데이터갱신구분", "dataUpdateFlag"),
    Map.entry("데이터갱신일자", "dataUpdateDate"),
    Map.entry("업태구분명", "businessType"),
    Map.entry("좌표정보(x)", "coordX"),
    Map.entry("좌표정보(y)", "coordY"),
    Map.entry("위생업태명", "sanitationType"),
    Map.entry("남성종사자수", "maleEmployees"),
    Map.entry("여성종사자수", "femaleEmployees"),
    Map.entry("영업장주변구분명", "surroundingType"),
    Map.entry("등급구분명", "grade"),
    Map.entry("급수시설구분", "waterSupplyType"),
    Map.entry("총직원수", "totalEmployees"),
    Map.entry("본사직원수", "hqEmployees"),
    Map.entry("공장사무직직원수", "factoryOfficeStaff"),
    Map.entry("공장판매직직원수", "factorySalesStaff"),
    Map.entry("공장생산직직원수", "factoryProductionStaff"),
    Map.entry("건물소유구분명", "buildingOwnership"),
    Map.entry("보증액", "securityDeposit"),
    Map.entry("월세액", "monthlyRent"),
    Map.entry("다중이용업소여부", "multiUse"),
    Map.entry("시설총규모", "facilitySize"),
    Map.entry("전통업소지정번호", "traditionalCode"),
    Map.entry("전통업소주된음식", "mainFood"),
    Map.entry("홈페이지", "website")
  );

  // 파일을 처음 읽을 때만 초기화
  private void initializeFile() throws Exception {
    if (rowIterator == null) {
      log.info("ExcelReader 파일 초기화");
      Workbook workbook = WorkbookFactory.create(new FileInputStream("/Users/ibang/Desktop/test.xlsx"));
      Sheet sheet = workbook.getSheetAt(0);
      this.rowIterator = sheet.iterator();
      initializeHeaderIndexMap(this.rowIterator.next());  // 헤더 정보 매핑 초기화
    }
  }

  @Override
  public RestaurantDataDto read() {
    log.info("Excel 파일 읽기를 시작합니다.");
    try {
      initializeFile();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (rowIterator.hasNext()) {
      Row row = rowIterator.next();
      return mapRowToDto(row);
    }
    log.info("Excel 파일 읽기를 완료했습니다.");
    return null;
  }

  // 엑셀 헤더와 DTO 필드 매핑 초기화
  private void initializeHeaderIndexMap(Row headerRow) {
    for (Cell cell : headerRow) {
      String excelHeader = cell.getStringCellValue().trim();
      if (customHeaderMapping.containsKey(excelHeader)) {
        // 엑셀 헤더에 해당하는 필드 이름을 매핑
        headerIndexMap.put(customHeaderMapping.get(excelHeader), cell.getColumnIndex());
      }
    }
  }

  // Row 데이터를 DTO로 매핑
  private RestaurantDataDto mapRowToDto(Row row) {
    RestaurantDataDto dto = new RestaurantDataDto();
    for (Field field : RestaurantDataDto.class.getDeclaredFields()) {
      String fieldName = field.getName();
      if (headerIndexMap.containsKey(fieldName)) {
        int cellIndex = headerIndexMap.get(fieldName);
        Cell cell = row.getCell(cellIndex);
        field.setAccessible(true);
        try {
          Object cellValue = getCellValue(cell, field.getType());
          field.set(dto, cellValue);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
    return dto;
  }

  // 셀 값을 필드 타입에 맞게 변환
  private Object getCellValue(Cell cell, Class<?> fieldType) {
    if (cell == null || cell.getCellType() == CellType.BLANK) return null;

    switch (cell.getCellType()) {
      case STRING:
        if (fieldType == String.class) {
          return cell.getStringCellValue();
        }
        break;
      case NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {  // 셀이 날짜 형식인지 확인
          if (fieldType == LocalDate.class) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
          } else if (fieldType == LocalDateTime.class) {
            return cell.getLocalDateTimeCellValue();
          }
        } else {
          if (fieldType == Long.class) {
            return (long) cell.getNumericCellValue();
          } else if (fieldType == Integer.class) {
            return (int) cell.getNumericCellValue();
          } else if (fieldType == BigDecimal.class) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
          } else if (fieldType == String.class) {  // 숫자를 문자열로 변환
            return String.valueOf(cell.getNumericCellValue());
          }
        }
        break;
      case BOOLEAN:
        if (fieldType == Boolean.class) {
          return cell.getBooleanCellValue();
        } else if (fieldType == String.class) {  // Boolean을 String으로 변환
          return String.valueOf(cell.getBooleanCellValue());
        }
        break;
      case FORMULA:
        return cell.getCellFormula();  // 셀에 수식이 있는 경우 처리
      default:
        throw new IllegalArgumentException("Unsupported cell type: " + cell.getCellType());
    }

    return null;  // 적합한 타입이 아니면 null 반환
  }

}