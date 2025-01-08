package com.example.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.common.QueryPageParam;
import com.example.project.common.Result;
import com.example.project.entity.Course;
import com.example.project.entity.SelCourses;
import com.example.project.entity.SelectedCourses;
import com.example.project.entity.Students;
import com.example.project.service.CourseService;
import com.example.project.service.SelCoursesService;
import com.example.project.service.SelectedCoursesService;
import com.example.project.service.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 成绩录入-控制器
 *
 * @author ge
 * @since 2023-10-24
 */
@RestController
@RequestMapping("/score")
public class SelectedCoursesController {
    @Autowired
    private SelCoursesService selCoursesService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentsService studentsService;

    @PostMapping("/save")
    public Result save(@RequestBody SelCourses selCourses) {
        boolean saved = selCoursesService.save(selCourses);
        return saved ? Result.suc() : Result.fail();
    }

    @PostMapping("/page")
    public Result page(@RequestBody QueryPageParam query) {
        Page<SelCourses> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        LambdaQueryWrapper<SelCourses> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (null != param.get("courseId") && !param.get("courseId").toString().trim().isEmpty()) {
            lambdaQueryWrapper.like(SelCourses::getCurrentCourseId, param.get("courseId").toString());
        }
        if (null != param.get("studentId") && !param.get("studentId").toString().trim().isEmpty()) {
            lambdaQueryWrapper.eq(SelCourses::getStudentId, Integer.parseInt(param.get("studentId").toString()));
        }
        IPage<SelCourses> result = selCoursesService.page(page, lambdaQueryWrapper);
        for (SelCourses record : result.getRecords()) {
            getCourseExtInfo(record);
        }
        return Result.suc(result.getRecords(), (int)result.getTotal());
    }

    private void getCourseExtInfo(SelCourses course) {
        Integer currentCourseId = course.getCurrentCourseId();
        Integer studentId = course.getStudentId();
        Course c = courseService.getById(currentCourseId);
        if (null != c && !c.getCourseName().trim().isEmpty()) {
            course.setCourseName(c.getCourseName());
        }
        Students stu = studentsService.getById(studentId);
        if (null != stu && !stu.getName().trim().isEmpty()) {
            course.setStudentName(stu.getName());
        }
    }


}


