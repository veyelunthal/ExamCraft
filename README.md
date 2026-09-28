# ExamCraft - Randomized Question Paper Generator

Spring Boot + MySQL (XAMPP) + HTML/CSS/JavaScript.

## Features
- Manage units and tagged questions.
- Tag questions by unit, topic and difficulty.
- Generate randomized test papers with easy/medium/hard percentages.
- Prevent duplicate questions in the same paper.
- Auto-grade MCQ and True/False submissions.
- Track question usage frequency.
- REST APIs for CRUD and generation.
- Global validation/error responses.
- Frontend pages are served directly by Spring Boot.

## Run
1. Start MySQL from XAMPP.
2. In phpMyAdmin create a database named `examcraft`.
3. Open this folder in IntelliJ IDEA.
4. Use Java 17+ and Maven.
5. Check `src/main/resources/application.properties` for your MySQL username/password.
6. Run `ExamCraftApplication`.
7. Open `http://localhost:8080/`.

Hibernate uses `ddl-auto=update`, so the tables are created automatically.
