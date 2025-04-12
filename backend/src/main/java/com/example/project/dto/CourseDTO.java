package com.example.project.dto;

import lombok.Data;

/**
 * 课程数据传输对象
 */
@Data
public class CourseDTO {
    private Integer courseId;
    private String courseName;
    private Integer teacherId;
    private String teacherName;
    private Integer capacity;
    private Integer selectedNumber;
    private String time;
} 