drop table if exists book;
drop table if exists student_teacher_binding;
drop table if exists student;
drop table if exists teacher;

create table student (
id serial primary key,
first_name text,
last_name text
);

create table teacher (
id serial primary key,
first_name text,
last_name text
);

create table book (
id serial primary key,
title text,
author text,
student_id int references student(id)
);

create table student_teacher_binding (
student_id int references student(id),
teacher_id int references teacher(id)
);