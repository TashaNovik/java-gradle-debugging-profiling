# Java Gradle Debugging & Profiling

Домашнее задание по теме "Инструменты разработчика" для дисциплины "Язык Java"

## Описание проекта

Spring Boot приложение для демонстрации работы с:
- Gradle сборкой
- IntelliJ IDEA Debugger
- Профилировщиком (Java Flight Recorder / IntelliJ IDEA Profiler)

## Требования

- Java 17+
- Gradle 8.5+ или IntelliJ IDEA

## Первоначальная настройка

### Вариант 1: Использование IntelliJ IDEA (Рекомендуется)

1. Откройте проект в IntelliJ IDEA
2. IDEA автоматически распознает проект как Gradle и предложит импортировать его
3. Дождитесь завершения индексации и загрузки зависимостей
4. После этого все ошибки компиляции должны исчезнуть

### Вариант 2: Установка Gradle вручную

Если Gradle не установлен, скачайте его с [официального сайта](https://gradle.org/releases/) и установите.

После установки выполните:
```bash
gradle wrapper
```

Это создаст полноценный Gradle Wrapper для проекта.

## Запуск приложения

### Через IntelliJ IDEA:
- Откройте `MessagingApplication.java`
- Нажмите на зеленую стрелку запуска

### Через Gradle (если установлен):
```bash
./gradlew bootRun
```

## Доступные эндпоинты

После запуска приложение доступно на `http://localhost:8080`:

- `GET /api/messages` - получить все сообщения
- `GET /api/messages/nplus1` - демонстрация проблемы N+1
- `GET /api/messages/optimized` - оптимизированный запрос
- `GET /api/messages/{id}` - получить сообщение по ID
- `GET /api/messages/{id}/fail` - демонстрация LazyInitializationException
- `POST /api/messages` - создать новое сообщение

### Консоль H2:
`http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (пусто)

## Задачи

### Задание 1: Gradle
- ✅ Файл build.gradle настроен
- ✅ Кастомная задача `runTestDataInitializer` создана
- ✅ Зависимости Hibernate вынесены в переменные

### Задание 2: Debugging
- Установить breakpoint в `MessageService.getMessagesOptimized()`
- Использовать Step Into/Step Over для отслеживания выполнения
- Изучить проблему N+1 в `getMessagesNPlus1()`
- Проанализировать LazyInitializationException в эндпоинте `/api/messages/{id}/fail`

### Задание 3: Profiling
- Запустить приложение с профилировщиком
- Сгенерировать нагрузку на `/api/messages/nplus1`
- Сравнить с `/api/messages/optimized`
- Проанализировать Flame Graph

## Запуск тестов

```bash
./gradlew test
```

Или через IntelliJ IDEA: правый клик на `MessagingApplicationTests.java` → Run Tests

**Результаты тестирования:**
- HTML отчет: `build/reports/tests/test/index.html`
- Сохраненные результаты профилирования: `src/main/resources/Test results - Test Summary_files`

## Структура проекта

```
src/
├── main/
│   ├── java/com/example/messaging/
│   │   ├── MessagingApplication.java
│   │   ├── controller/MessageController.java
│   │   ├── service/MessageService.java
│   │   ├── repository/
│   │   │   ├── MessageRepository.java
│   │   │   └── AuthorRepository.java
│   │   └── model/
│   │       ├── Message.java
│   │       └── Author.java
│   └── resources/application.properties
└── test/
    └── java/com/example/messaging/
        └── MessagingApplicationTests.java
```
