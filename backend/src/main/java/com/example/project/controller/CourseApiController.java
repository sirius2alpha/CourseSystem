package com.example.project.controller;

import com.example.project.common.ApiResponse;
import com.example.project.dto.CourseDTO;
import com.example.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 课程API控制器
 * 整合并重构原有的课程相关控制器
 */
@RestController
@RequestMapping("/api")
public class CourseApiController {

    @Autowired
    private CourseService courseService;

    /**
     * 根据条件查询课程
     */
    @GetMapping("/courses")
    public ApiResponse<List<CourseDTO>> queryCourses(
            @RequestParam(required = false) Integer course_id,
            @RequestParam(required = false) String course_name,
            @RequestParam(required = false) Integer teacher_id,
            @RequestParam(required = false) String teacher_name,
            @RequestParam(required = false) String course_time) {

        List<CourseDTO> courses = courseService.queryCourses(
                course_id, course_name, teacher_id, teacher_name, course_time);

        return courses.isEmpty()
                ? ApiResponse.fail("未找到匹配的课程")
                : ApiResponse.success(courses, courses.size());
    }

    /**
     * 获取学生已选课程
     */
    @GetMapping("/students/{userId}/courses")
    public ApiResponse<List<CourseDTO>> getStudentCourses(@PathVariable String userId) {
        List<CourseDTO> courses = courseService.getStudentSelectedCourses(userId);

        return courses.isEmpty()
                ? ApiResponse.fail("该学生未选课程")
                : ApiResponse.success(courses, courses.size());
    }

    /**
     * 学生选课
     */
    @PostMapping("/students/{userId}/courses")
    public ApiResponse<?> selectCourses(
            @PathVariable String userId,
            @RequestBody List<CourseDTO> courses) {

        try {
            boolean success = courseService.selectCourses(userId, courses);
            return success
                    ? ApiResponse.success()
                    : ApiResponse.fail("选课失败");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    /**
     * 学生退课
     */
    @DeleteMapping("/students/{userId}/courses")
    public ApiResponse<?> dropCourses(
            @PathVariable String userId,
            @RequestBody List<CourseDTO> courses) {

        try {
            boolean success = courseService.dropCourses(userId, courses);
            return success
                    ? ApiResponse.success()
                    : ApiResponse.fail("退课失败");
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }

    /**
     * 获取学生成绩
     */
    @GetMapping("/students/{userId}/courses/score")
    public ApiResponse<List<Map<String, Object>>> getStudentScores(@PathVariable String userId) {
        List<Map<String, Object>> scores = courseService.getStudentScores(userId);

        return scores.isEmpty()
                ? ApiResponse.fail("未找到成绩信息")
                : ApiResponse.success(scores, scores.size());
    }

    /**
     * 获取教师课程
     */
    @GetMapping("/teachers/{userId}/courses")
    public ApiResponse<List<CourseDTO>> getTeacherCourses(@PathVariable Integer userId) {
        List<CourseDTO> courses = courseService.getTeacherCourses(userId);

        return courses.isEmpty()
                ? ApiResponse.fail("该教师未开设课程")
                : ApiResponse.success(courses, courses.size());
    }

    /**
     * 获取教师课程的学生名单
     */
    @GetMapping("/teachers/{teacherId}/courses/{courseId}")
    public ApiResponse<List<Map<String, Object>>> getCourseStudents(
            @PathVariable Integer teacherId,
            @PathVariable Integer courseId) {

        try {
            List<Map<String, Object>> students = courseService.getCourseStudents(courseId, teacherId);
            return students.isEmpty()
                    ? ApiResponse.fail("该课程没有学生选修")
                    : ApiResponse.success(students, students.size());
        } catch (Exception e) {
            return ApiResponse.fail(e.getMessage());
        }
    }
}