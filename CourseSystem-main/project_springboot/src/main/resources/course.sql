create table course
(
    course_id   int auto_increment comment '课程编号'
        primary key,
    course_name varchar(50) not null comment '课程名称',
    teacher_id  int         not null comment '教师编号',
    capacity    int         not null comment '课程容量',
    time        varchar(30) not null comment '上课时间'
)
    comment '课程表';

INSERT INTO course (course_id, course_name, teacher_id, capacity, time) VALUES (1, '物理学', 100000, 99, '8:00~9:45');
INSERT INTO course (course_id, course_name, teacher_id, capacity, time) VALUES (2, '化学', 100000, 67, '10:00~12:00');
INSERT INTO course (course_id, course_name, teacher_id, capacity, time) VALUES (3, '英语', 100000, 45, '13:30~15:20');
