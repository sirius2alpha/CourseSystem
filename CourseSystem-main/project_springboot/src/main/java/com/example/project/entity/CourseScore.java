package com.example.project.entity;

import lombok.Data;

@Data
public class CourseScore {
    private Integer courseId;
    private String courseName;
    private String teacherName;
    private Double score;
}