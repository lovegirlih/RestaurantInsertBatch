# RestaurantDataInsertBatch
대용량 Data를 DB에(무결성 보장) 입력하는 어플리케이션을 구현합니다.

## 목차
1. [요구사항](#요구사항)
2. [실행 전 데이터베이스 DDL](#데이터 베이스 DDL)
3. [설치 방법](#설치-방법)

## 요구사항
* 배치어플리케이션으로 CSV 파일을 읽어 데이터베이스(MySQL)에 CSV의 데이터를 열을 구분하여 저장합니다.
* 배치 작업의 진행상황과 발생한 오류를 추적할 수 있도록 로깅합니다.
* 배치의 기능을 검증하기 위해 단위테스트를 작성합니다.

## 데이터 베이스 DDL
로컬환경에 mysql을 설치 하신 후 root 권한으로 실행 부탁드립니다.
혹시 mysql이 설치되어있지 않다면 OS에 따르게 적절하게 설치 부탁드립니다.
---
1. 스키마 추가
```sql
CREATE DATABASE my_database;
```
---
2. 테이블 추가
```sql
CREATE TABLE restaurant_standard_data (
id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 고유 ID, 자동증가
service_name VARCHAR(50) NOT NULL, -- 서비스 명칭 (NULL 불가)
service_id VARCHAR(20) NOT NULL, -- 서비스 ID (NULL 불가)
municipality_code VARCHAR(10) NOT NULL, -- 자치단체 코드 (NULL 불가)
management_no VARCHAR(30) NOT NULL, -- 관리 번호 (NULL 불가)
permit_date DATE NOT NULL, -- 인허가 일자 (NULL 불가)
permit_cancel_date DATE NULL, -- 인허가 취소 일자
business_status_code VARCHAR(5) NULL, -- 영업 상태 구분 코드
business_status VARCHAR(20) NULL, -- 영업 상태명
detailed_status_code VARCHAR(5) NULL, -- 상세 영업 상태 코드
detailed_status VARCHAR(20) NULL, -- 상세 영업 상태명
closure_date DATE NULL, -- 폐업 일자
suspension_start DATE NULL, -- 휴업 시작 일자
suspension_end DATE NULL, -- 휴업 종료 일자
reopening_date DATE NULL, -- 재개업 일자
location_phone VARCHAR(20) NULL, -- 소재지 전화
location_area DECIMAL(10, 2) NULL, -- 소재지 면적
location_zipcode VARCHAR(10) NULL, -- 소재지 우편번호
location_address TEXT NULL, -- 소재지 전체 주소
road_address TEXT NULL, -- 도로명 전체 주소
road_zipcode VARCHAR(10) NULL, -- 도로명 우편번호
business_name VARCHAR(100) NULL, -- 사업장명
last_update TIMESTAMP NULL, -- 최종 수정 시점
data_update_flag CHAR(1) NULL, -- 데이터 갱신 구분
data_update_date DATE NULL, -- 데이터 갱신 일자
business_type VARCHAR(50) NULL, -- 업태 구분명
coord_x DECIMAL(15, 6) NULL, -- 좌표 정보 (x)
coord_y DECIMAL(15, 6) NULL, -- 좌표 정보 (y)
sanitation_type VARCHAR(50) NULL, -- 위생 업태명
male_employees INT NULL, -- 남성 종사자 수
female_employees INT NULL, -- 여성 종사자 수
surrounding_type VARCHAR(50) NULL, -- 영업장 주변 구분명
grade VARCHAR(20) NULL, -- 등급 구분명
water_supply_type VARCHAR(20) NULL, -- 급수 시설 구분명
total_employees INT NULL, -- 총 직원 수
hq_employees INT NULL, -- 본사 직원 수
factory_office_staff INT NULL, -- 공장 사무직 직원 수
factory_sales_staff INT NULL, -- 공장 판매직 직원 수
factory_production_staff INT NULL, -- 공장 생산직 직원 수
building_ownership VARCHAR(50) NULL, -- 건물 소유 구분명
security_deposit DECIMAL(15, 2) NULL, -- 보증액
monthly_rent DECIMAL(15, 2) NULL, -- 월세액
multi_use BOOLEAN NULL, -- 다중 이용 업소 여부
facility_size DECIMAL(10, 2) NULL, -- 시설 총 규모
traditional_code VARCHAR(50) NULL, -- 전통 업소 지정 번호
main_food VARCHAR(100) NULL, -- 전통 업소 주된 음식
website VARCHAR(255) NULL -- 홈페이지 주소
);
```

## 설치 및 실행 가이드
1. git clone을 합니다. (public으로 설정했습니다.)
   (https://github.com/lovegirlih/RestaurantInsertBatch.git)
2. resurces/application.yaml에 file:excel-path:에 내 로컬 환경에 존재하는 excel파일의 경로를 설정합니다.
3. RestaurantInsertBatch파일의 main을 실행합니다.
4. 문의사항은 010-8783-0421로 부탁드립니다!