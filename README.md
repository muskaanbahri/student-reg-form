![image](https://github.com/user-attachments/assets/b2e7c7a2-fe50-44a1-b8c0-8844e3e84069)
# Student Registration System (Java + MySQL)

A simple desktop application built using Java Swing and JDBC to manage student data.

## Features
- Register new students
- View all registered students

## Tech Stack
- Java (Swing for UI)
- MySQL (Database)
- JDBC (Database Connectivity)

## How to Run
1. Clone the repo
2. Import as a Java project in your IDE
3. Set up MySQL and update `DBConnection.java` with your credentials
4. Compile and run `StudentForm.java`

## Database Schema

```sql
CREATE TABLE students (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50),
  email VARCHAR(50),
  phone VARCHAR(15)
);
