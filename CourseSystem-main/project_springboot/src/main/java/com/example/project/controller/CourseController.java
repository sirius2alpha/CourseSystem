package com.example.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.common.QueryPageParam;
import com.example.project.common.Result;
import com.example.project.entity.Course;
import com.example.project.entity.SelCourses;
import com.example.project.entity.SelectedCourses;
import com.example.project.entity.Teachers;
import com.example.project.service.CourseService;
import com.example.project.service.SelCoursesService;
import com.example.project.service.SelectedCoursesService;
import com.example.project.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 课程控制器
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeachersService teachersService;
    @Autowired
    private SelCoursesService selCoursesService;

    /**
     * 课程列表
     * 用于 老师页面->开课详情
     */
    @PostMapping("/page")
    public Result page(@RequestBody QueryPageParam query) {
        Page<Course> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (null != param.get("courseId") && !param.get("courseId").toString().trim().isEmpty()) {
            lambdaQueryWrapper.like(Course::getCourseId, param.get("courseId").toString());
        }
        if (null != param.get("teacherId") && !param.get("teacherId").toString().trim().isEmpty()) {
            lambdaQueryWrapper.eq(Course::getTeacherId, Integer.parseInt(param.get("teacherId").toString()));
        }
        if (null != param.get("courseName") && !param.get("courseName").toString().trim().isEmpty()) {
            lambdaQueryWrapper.like(Course::getCourseName, param.get("courseName").toString());
        }
        IPage<Course> result = courseService.page(page, lambdaQueryWrapper);
        for (Course record : result.getRecords()) {
            getCourseExtInfo(record);
        }
        return Result.suc(result.getRecords(), (int)result.getTotal());
    }

    /**
     * 根据教师编号查询该教师的课程
     * 用于 老师页面->成绩录入->选择课程下拉框
     */
    @GetMapping("/teacherCourseList/{teacherId}")
    public Result teacherCourseList(@PathVariable("teacherId") Integer teacherId) {
        LambdaQueryWrapper<Course> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Course::getTeacherId, teacherId);
        List<Course> result = courseService.list(lambdaQueryWrapper);
        return Result.suc(result, (int)Long.parseLong(String.valueOf(result.size())));
    }

    @PostMapping("/save")
    public Result save(@RequestBody Course course) {
        boolean saved = courseService.save(course);
        return saved ? Result.suc() : Result.fail("新增课程时数据库出错");
    }

    @PostMapping("/update")
    public Result update(@RequestBody Course course) {
        Long courseId = course.getCourseId();
        Course old = courseService.getById(courseId);
        if (null == old) {
            return Result.fail("查不到该课程的相关信息");
        }
        boolean updated = courseService.updateById(course);
        return updated ? Result.suc() : Result.fail("更新课程时数据库出错");
    }

    /**
     * 根据课程编号查询课程详情
     * 用于 老师页面->开课详情
     */
    @GetMapping("/detail/{courseId}")
    public Result detail(@PathVariable("courseId") Long courseId) {
        Course info = courseService.getById(courseId);
        if (null != info) {
            getCourseExtInfo(info);
        }
        return null == info ? Result.fail("查不到该课程的相关信息") : Result.suc(info);
    }

    @DeleteMapping("/delete/{courseId}")
    public Result delete(@PathVariable("courseId") Long courseId) {
        Course info = courseService.getById(courseId);
        if (null == info) {
            return Result.fail("查不到该课程的相关信息");
        }
        boolean removed = courseService.removeById(courseId);
        return removed ? Result.suc() : Result.fail("删除课程时数据库出错");
    }

    @DeleteMapping("/delete/{ids}")
    public Result delete(@PathVariable("ids") String ids) {
        String[] split = ids.split(",");
        List<String> list = Arrays.asList(split);
        boolean removed = courseService.removeByIds(list);
        return removed ? Result.suc() : Result.fail("批量删除课程时数据库出错");
    }

    private void getCourseExtInfo(Course course) {
        Integer teacherId = course.getTeacherId();
        Teachers teacher = teachersService.getById(teacherId);
        if (null != teacher && !teacher.getName().trim().isEmpty()) {
            course.setTeacherName(teacher.getName());
        }
        Long courseId = course.getCourseId();
        LambdaQueryWrapper<SelCourses> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SelCourses::getCurrentCourseId, courseId);
        int count = selCoursesService.count(wrapper);
        course.setSelectedCount(count);
    }
}
