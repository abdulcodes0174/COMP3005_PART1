/*
Abdul Malik
101 269 165
COMP3005 - Assignment 3 - Part 1
*/

create table students (
    student_id         SERIAL,
    first_name         TEXT NOT NULL,
    last_name          TEXT NOT NULL,
    email              TEXT NOT NULL UNIQUE,
    enrollment_date    DATE,
    PRIMARY KEY (student_id)
);