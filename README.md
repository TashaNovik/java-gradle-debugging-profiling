# Java Gradle Debugging & Profiling

Домашнее задание по теме "Инструменты разработчика" для дисциплины "Язык Java"

## Описание проекта

Spring Boot приложение для демонстрации работы с:
- Gradle сборкой
- IntelliJ IDEA Debugger
- Профилировщиком (Java Flight Recorder / IntelliJ IDEA Profiler)

## Структура проекта

```
src/
├── main/
│   ├── java/
│   │   └── com/example/messaging/
│   │       ├── MessagingApplication.java
│   │       ├── controller/
│   │       │   └── MessageController.java
│   │       ├── service/
│   │       │   └── MessageService.java
│   │       ├── repository/
│   │       │   ├── MessageRepository.java
│   │       │   └── AuthorRepository.java
│   │       └── model/
│   │           ├── Message.java
│   │           └── Author.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/example/messaging/
            └── MessagingApplicationTests.java
```

## Требования

- Java 17+
- Gradle 8.5+

## Запуск приложения

```bash
./gradlew bootRun
```

## Задачи

1. ✅ Сборка проекта с Gradle
2. ⏳ Отладка с использованием IntelliJ IDEA Debugger
3. ⏳ Профилирование и анализ Flame Graphs

## TODO

- [ ] Реализовать модели данных
- [ ] Реализовать репозитории
- [ ] Реализовать сервисный слой
- [ ] Реализовать контроллеры
- [ ] Создать кастомную Gradle задачу для заполнения БД
- [ ] Настроить точки останова для отладки
- [ ] Провести профилирование
