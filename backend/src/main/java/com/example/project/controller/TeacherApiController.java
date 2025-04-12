package com.example.project.controller;

import com.example.project.common.ApiResponse;
import com.example.project.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 教师API控制器
 * 整合教师相关功能
 */
@RestController
@RequestMapping("/api/teachers")
public class TeacherApiController {

    @Autowired
    private CourseService courseService;

    /**
     * 更新学生课程成绩
     */
    @PutMapping("/{teacherId}/courses/{courseId}/students/{studentId}/score")
    public ApiResponse<?> updateStudentScore(
            @PathVariable Integer teacherId,
            @PathVariable Integer courseId,
            @PathVariable String studentId,
            @RequestParam(required = false) Float dailyScore,
            @RequestParam(required = false) Float examinationScore,
            @RequestBody(required = false) Map<String, Object> requestBody) {

        try {
            System.out.println("接收到成绩更新请求 - 教师ID: " + teacherId + ", 课程ID: " + courseId + ", 学生ID: " + studentId);

            // 记录请求参数
            if (requestBody != null) {
                System.out.println("请求体内容: " + requestBody);
            }

            // 先从请求参数获取成绩，如果为空则尝试从请求体获取
            Float finalDailyScore = dailyScore;
            Float finalExaminationScore = examinationScore;

            // 如果从请求参数中获取不到，尝试从请求体获取
            if ((finalDailyScore == null || finalExaminationScore == null) && requestBody != null) {
                System.out.println("尝试从请求体获取成绩数据");

                if (requestBody.containsKey("dailyScore")) {
                    try {
                        Object value = requestBody.get("dailyScore");
                        System.out.println("请求体中的dailyScore类型: " + (value != null ? value.getClass().getName() : "null")
                                + ", 值: " + value);

                        if (value instanceof Number) {
                            finalDailyScore = ((Number) value).floatValue();
                        } else if (value instanceof String) {
                            finalDailyScore = Float.parseFloat((String) value);
                        }
                    } catch (Exception e) {
                        System.err.println("解析平时成绩出错: " + e.getMessage());
                        // 忽略解析错误
                    }
                }

                if (requestBody.containsKey("examinationScore")) {
                    try {
                        Object value = requestBody.get("examinationScore");
                        System.out.println("请求体中的examinationScore类型: "
                                + (value != null ? value.getClass().getName() : "null") + ", 值: " + value);

                        if (value instanceof Number) {
                            finalExaminationScore = ((Number) value).floatValue();
                        } else if (value instanceof String) {
                            finalExaminationScore = Float.parseFloat((String) value);
                        }
                    } catch (Exception e) {
                        System.err.println("解析考试成绩出错: " + e.getMessage());
                        // 忽略解析错误
                    }
                }
            }

            // 确保成绩参数不为空
            if (finalDailyScore == null || finalExaminationScore == null) {
                String errorMsg = "请提供平时成绩(dailyScore)和考试成绩(examinationScore)";
                System.err.println(errorMsg);
                return ApiResponse.fail(errorMsg);
            }

            System.out.println("即将更新成绩 - 平时成绩: " + finalDailyScore + ", 考试成绩: " + finalExaminationScore);

            // 调用带teacherId参数的新方法
            boolean success = courseService.updateStudentScore(courseId, studentId, finalDailyScore,
                    finalExaminationScore, teacherId);

            System.out.println("成绩更新结果: " + (success ? "成功" : "失败"));

            return success
                    ? ApiResponse.success()
                    : ApiResponse.fail("更新成绩失败");
        } catch (Exception e) {
            System.err.println("更新成绩异常: " + e.getMessage());
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return ApiResponse.fail(e.getMessage());
        }
    }
}