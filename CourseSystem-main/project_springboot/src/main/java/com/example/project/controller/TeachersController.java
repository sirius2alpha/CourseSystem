package com.example.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.project.common.Result;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.CurrentCourses;
import com.example.project.entity.SelectedCourses;
import com.example.project.entity.Teachers;
import com.example.project.service.CoursePlanService;
import com.example.project.service.CurrentCoursesService;
import com.example.project.service.SelectedCoursesService;
import com.example.project.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ge
 * @since 2023-10-23
 */
@RestController
@RequestMapping("/api/teachers")
public class TeachersController {
    @Autowired
    private SelectedCoursesService selectedCoursesService;
    @Autowired
    private CurrentCoursesService currentCoursesService;
    @Autowired
    private CoursePlanService coursePlanService;
    @Autowired
    private TeachersService teachersService;

    /**
     * 课程详情
     */
    @GetMapping("/{userId}/courses/{courseId}")
    public Result courses(@PathVariable("userId") Integer teacherId, @PathVariable("courseId") Integer courseId) {
        LambdaQueryWrapper<CurrentCourses> currentCoursesQueryWrapper = new LambdaQueryWrapper<>();
        currentCoursesQueryWrapper.eq(CurrentCourses::getCourseId, courseId);
        currentCoursesQueryWrapper.eq(CurrentCourses::getTeacherId, teacherId);
        CurrentCourses currentCourses = currentCoursesService.getOne(currentCoursesQueryWrapper);

        LambdaQueryWrapper<Teachers> teachersQueryWrapper = new LambdaQueryWrapper<>();
        teachersQueryWrapper.eq(Teachers::getId, teacherId);
        Teachers teachers = teachersService.getOne(teachersQueryWrapper);
        if (null != teachers) {
            currentCourses.setTeacherName(teachers.getName());
        }

        LambdaQueryWrapper<CoursePlan> coursePlanQueryWrapper = new LambdaQueryWrapper<>();
        coursePlanQueryWrapper.eq(CoursePlan::getCourseId, courseId);
        CoursePlan coursePlan = coursePlanService.getOne(coursePlanQueryWrapper);
        if (null != coursePlan) {
            currentCourses.setCourseName(coursePlan.getCourseName());
        }

        LambdaQueryWrapper<SelectedCourses> selectedCoursesQueryWrapper = new LambdaQueryWrapper<>();
        selectedCoursesQueryWrapper.like(SelectedCourses::getCurrentCourseId, currentCourses.getNo());
        int count = selectedCoursesService.count(selectedCoursesQueryWrapper);
        currentCourses.setSelectedCount(count);

        //课程容量先固定写100
        currentCourses.setCapacity(100);

        return Result.suc(currentCourses);
    }

}
