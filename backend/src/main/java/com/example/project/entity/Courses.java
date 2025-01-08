package com.example.project.entity;

import lombok.Data;

@Data
public class Courses {
    private Integer course_id;
    private String course_name;
    private Integer teacher_id;
    private String teacher_name;
    private Integer capacity;
    private Integer selected_number;
    private String time;
}

