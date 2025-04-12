package com.example.project.service;

import com.example.project.common.Result;
import com.example.project.entity.Course;
import java.util.List;

/**
 * 学生服务接口
 */
public interface StudentService {
    
    /**
     * 获取学生已选课程
     */
    Result getSelectedCourses(String studentId);
    
    /**
     * 选课
     */
    Result selectCourses(String studentId, List<Course> courses);
    
    /**
     * 退课
     */
    Result deleteCourses(String studentId, List<Course> courses);
    
    /**
     * 获取学生成绩
     */
    Result getScores(String studentId);
    
    /**
     * 获取学生信息
     */
    Result getStudentInfo(String studentId);
}