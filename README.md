# spring-gift-test

Spring Boot 기반 선물/상품 관리 시스템

## 요구사항

- Java 21
- Docker & Docker Compose

## 실행 방법

### 개발 환경 실행

```bash
# PostgreSQL 시작
docker compose up -d postgres

# 애플리케이션 실행
./gradlew bootRun --args='--spring.profiles.active=dev'
```

### 테스트 실행

```bash
# Cucumber 테스트 실행 (PostgreSQL 자동 시작)
./gradlew cucumberTest

# 전체 테스트 실행 (H2 인메모리 DB 사용)
./gradlew test
```

### DB 수동 관리

```bash
# 테스트용 PostgreSQL 시작
docker compose up -d postgres-test

# 개발용 PostgreSQL 시작
docker compose up -d postgres

# 컨테이너 중지
docker compose down

# 볼륨 포함 완전 삭제
docker compose down -v
```

## 프로파일 설정

| 프로파일 | 데이터베이스 | 포트 | 용도 |
|---------|------------|-----|-----|
| (없음) | H2 in-memory | - | 단위 테스트 |
| dev | PostgreSQL | 5432 | 개발 |
| test | PostgreSQL | 5433 | Cucumber 테스트 |

## 프로젝트 구조

```
src/
├── main/
│   ├── java/gift/
│   │   ├── ui/           # REST 컨트롤러
│   │   ├── application/  # 서비스 & DTO
│   │   ├── model/        # 엔티티 & Repository
│   │   └── infrastructure/
│   └── resources/
│       ├── application.properties
│       └── application-dev.properties
└── test/
    ├── java/gift/cucumber/
    └── resources/
        ├── application.properties
        ├── application-test.properties
        ├── features/     # Cucumber feature 파일
        └── sql/          # 테스트 데이터
```
