package com.example.project.entity;

        import lombok.Data;

@Data
public class score {
    private Integer course_id;
    private Integer teacher_id;
    private Integer student_id;
    private String student_name;
    private double  daily_score;
    private double examination_score;
}