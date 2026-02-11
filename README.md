# Infotecs AQA Test

Тестовое задание для стажера-автотестировщика Java

## Профили

Переключение в `application.yml`:

```yml
spring.profiles.active: in-memory  # или postgres
```

- `in-memory` - данные в памяти (без БД)
- `postgres` - H2 (имитация PostgreSQL)

## Эндпоинты

| Метод | URL | Описание |
|-------|-----|----------|
| POST | `/api/process/start` | Запустить процесс |
| GET | `/api/process/result/{id}` | Получить результат процесса по id|
| GET | `/api/process/get/running` | Все запущенные процессы |
| GET | `/api/process/get/all` | Все процессы |
| DELETE | `/api/process/delete/{id}` | Удалить процесс по id|

## Swagger

```
http://localhost:8080/swagger
```

## H2 консоль (для postgres профиля)

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: 
```

## Стек

- Java 21
- Spring Boot 3.5.9
- Spring Data JPA
- H2
- Swagger (springdoc)
- Lombok
