package com.example.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.project.common.Result;
import com.example.project.entity.*;
import com.example.project.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private StudentsService studentsService;


    /**
     * 课程详情
     */
   /*
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
    */


    @GetMapping("/{userId}/courses")
    public Result kaike(@PathVariable("userId") Integer teacherId) throws JsonProcessingException {
        List<CurrentCourses> selectno=currentCoursesService.lambdaQuery()
                .eq(CurrentCourses::getTeacherId,teacherId).list();
        if(selectno.size()==0)
            return Result.fail();
        List<String> response = new ArrayList<>();
        Integer no;
        int courseid,teacherid;

        Courses courses=new Courses();

        for (int i = 0; i < selectno.size(); i++) {
            no = selectno.get(i).getNo();
            courseid = selectno.get(i).getCourseId();
            courses.setCourse_id(courseid);
            List<CoursePlan> coursesname = coursePlanService.lambdaQuery()
                    .eq(CoursePlan::getCourseId, courseid).list();
            courses.setCourse_name(coursesname.get(0).getCourseName());
            teacherid = selectno.get(i).getTeacherId();
            courses.setTeacher_id(teacherid);
            List<Teachers> teachersname = teachersService.lambdaQuery()
                    .eq(Teachers::getId, teacherid).list();
            courses.setTeacher_name(teachersname.get(0).getName());
            courses.setCapacity(50);
            List<SelectedCourses> selectno1 = selectedCoursesService.lambdaQuery()
                    .eq(SelectedCourses::getCurrentCourseId, no).list();
            courses.setSelected_number(selectno1.size());
            courses.setTime(selectno.get(i).getTime());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(courses);
            response.add(i,json);
        }

        return selectno.size() > 0 ? Result.suc(response,selectno.size()) : Result.fail();
    }

    @GetMapping("/{userId}/courses/{selectedCourse}")
    public Result xueshengmingdan(@PathVariable("userId") Integer teacherId,
                                  @PathVariable("selectedCourse") Integer courseId) throws JsonProcessingException {
        List<CurrentCourses> selectno=currentCoursesService.lambdaQuery()
                .eq(CurrentCourses::getTeacherId,teacherId)
                .eq(CurrentCourses::getCourseId,courseId).list();
        if(selectno.size()==0)
            return Result.fail("没有学生选这个课");
        List<String> response = new ArrayList<>();
        Integer no=selectno.get(0).getNo();
        int studentid;

        Student student=new Student();

        List<SelectedCourses> selectno1 = selectedCoursesService.lambdaQuery()
                .eq(SelectedCourses::getCurrentCourseId, no).list();
        for(int i=0;i<selectno.size();i++)
        {
            studentid=selectno1.get(i).getStudentId();
            List<Students> stuname=studentsService.lambdaQuery()
                    .eq(Students::getId,studentid).list();
            student.setId(studentid);
            student.setName(stuname.get(0).getName());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(student);
            response.add(i,json);
        }

        return selectno.size() > 0 ? Result.suc(response,selectno.size()) : Result.fail();
    }
}
