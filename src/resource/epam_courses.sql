CREATE DATABASE epam_courses;
USE epam_courses;
CREATE TABLE users
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(25) NOT NULL,
  name VARCHAR(25) NOT NULL,
  familyname VARCHAR(25) NOT NULL,
  patronymic VARCHAR(25),
  user_type ENUM("admin", "student", "professor")
);
CREATE TABLE departments
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name VARCHAR(150) NOT NULL
);
CREATE TABLE courses
(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    professor_id INT NOT NULL,
    department_id INT
);
CREATE TABLE courses_students
(
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    mark TINYINT
);

ALTER TABLE courses ADD FOREIGN KEY (department_id) REFERENCES departments (id);
ALTER TABLE courses ADD FOREIGN KEY (professor_id) REFERENCES users (id);
CREATE INDEX department_id_key ON courses (department_id);
CREATE INDEX professor_id_key ON courses (professor_id);
ALTER TABLE courses_students ADD FOREIGN KEY (student_id) REFERENCES users (id);
ALTER TABLE courses_students ADD FOREIGN KEY (course_id) REFERENCES courses (id);
CREATE UNIQUE INDEX uc_studentID_courseID ON courses_students (student_id, course_id);
CREATE INDEX course_id_key ON courses_students (course_id);
CREATE UNIQUE INDEX email ON users (email);

INSERT INTO Users (email, password, name, familyname, patronymic, user_type) VALUES ("admin@courses.com", "0000", "Admin", "Admin", "", "admin");
