package com.example.project.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project.dto.CourseDTO;
import com.example.project.entity.Course;

import java.util.List;
import java.util.Map;

/**
 * 课程服务接口
 */
public interface CourseService extends IService<Course> {

    /**
     * 分页查询课程
     * 
     * @param page         分页对象
     * @param queryWrapper 查询条件
     * @return 分页结果
     */
    IPage<Course> pageCourses(Page<Course> page, LambdaQueryWrapper<Course> queryWrapper);

    /**
     * 根据条件查询课程列表
     * 
     * @param courseId    课程ID
     * @param courseName  课程名称
     * @param teacherId   教师ID
     * @param teacherName 教师名称
     * @param courseTime  课程时间
     * @return 课程列表
     */
    List<CourseDTO> queryCourses(Integer courseId, String courseName, Integer teacherId, String teacherName,
            String courseTime);

    /**
     * 获取学生已选课程
     * 
     * @param studentId 学生ID
     * @return 已选课程列表
     */
    List<CourseDTO> getStudentSelectedCourses(String studentId);

    /**
     * 获取教师开设的课程
     * 
     * @param teacherId 教师ID
     * @return 教师课程列表
     */
    List<CourseDTO> getTeacherCourses(Integer teacherId);

    /**
     * 学生选课
     * 
     * @param studentId 学生ID
     * @param courses   课程列表
     * @return 是否成功
     */
    boolean selectCourses(String studentId, List<CourseDTO> courses);

    /**
     * 学生退课
     * 
     * @param studentId 学生ID
     * @param courses   课程列表
     * @return 是否成功
     */
    boolean dropCourses(String studentId, List<CourseDTO> courses);

    /**
     * 获取学生成绩
     * 
     * @param studentId 学生ID
     * @return 课程成绩列表
     */
    List<Map<String, Object>> getStudentScores(String studentId);

    /**
     * 获取课程学生名单
     * 
     * @param courseId  课程ID
     * @param teacherId 教师ID
     * @return 学生名单
     */
    List<Map<String, Object>> getCourseStudents(Integer courseId, Integer teacherId);

    /**
     * 更新学生课程成绩
     * 
     * @param courseId         课程ID
     * @param studentId        学生ID
     * @param dailyScore       平时成绩
     * @param examinationScore 考试成绩
     * @return 是否成功
     */
    boolean updateStudentScore(Integer courseId, String studentId, Float dailyScore, Float examinationScore);

    /**
     * 更新学生课程成绩（带教师ID）
     * 
     * @param courseId         课程ID
     * @param studentId        学生ID
     * @param dailyScore       平时成绩
     * @param examinationScore 考试成绩
     * @param teacherId        教师ID
     * @return 是否成功
     */
    boolean updateStudentScore(Integer courseId, String studentId, Float dailyScore, Float examinationScore,
            Integer teacherId);
}
