package com.example.project.entity;

import lombok.Data;

@Data
public class CourseScore {
    private Integer course_id;
    private String course_name;
    private String teacher_name;
    private Double score;
}