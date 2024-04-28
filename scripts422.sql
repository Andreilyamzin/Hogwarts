alter table student
    add constraint age_check CHECK (age >= 16);

insert into student(age, name, faculty_id)
values (16, 'укук', null);

insert into student(age, name, faculty_id)
values (null, 'wfwf', null);

alter table student
    add constraint name_unique unique (name);

alter table student
    alter column name set not null;

alter table faculty
    add constraint faculty_name_color_uniq unique (name, color);

alter table student
    alter column age set default 20;

insert into faculty(color, name)
values ('white', 'jdjdjs');

update student
set faculty_id = 1;
select s.name, s.age, f.name
from student s
         join public.faculty f on f.id = s.faculty_id;

select *
from student s
         join public.avatar a on s.id = a.student_id
where a.student_id is not null;






CREATE TABLE People (
    people_id   INT PRIMARY KEY,
    name        VARCHAR(444),
    age         INT,
    has_license BOOLEAN
);
CREATE TABLE Car (
    car_id INT PRIMARY KEY,
    name   VARCHAR(444),
    model  VARCHAR(444),
    price  DECIMAL(10, 2)
);

CREATE TABLE Motorists (
    people_id INT,
    car_id    INT,
    PRIMARY KEY (people_id, car_id),
    FOREIGN KEY (car_id) REFERENCES Car (car_id),
    FOREIGN KEY (people_id) REFERENCES People (people_id)

);



