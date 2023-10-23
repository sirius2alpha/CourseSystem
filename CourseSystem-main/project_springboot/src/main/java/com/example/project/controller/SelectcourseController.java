package com.example.project.controller;


import com.example.project.common.Result;
import com.example.project.entity.*;
import com.example.project.service.CoursePlanService;
import com.example.project.service.CurrentCoursesService;
import com.example.project.service.SelectcourseService;
import com.example.project.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static java.sql.Types.NULL;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ge
 * @since 2023-10-21
 */
@RestController
@RequestMapping("/api")
public class SelectcourseController {

    @Autowired
    private CurrentCoursesService currentCoursesService;

    @Autowired
    private CoursePlanService coursePlanService;

    @Autowired
    private SelectcourseService selectcourseService;

    @Autowired
    private TeachersService teachersService;

    @GetMapping("/list")
    public List<CurrentCourses> list(){
        return currentCoursesService.list();
    }

    @PostMapping("/courses")
    public Result msg(@RequestBody Selectcourse selectcourse){
        Integer courseid=selectcourse.getCourse_id();
        String coursename=selectcourse.getCourse_name();
        Integer teacherid=selectcourse.getTeacher_id();
        String teachername=selectcourse.getTeacher_name();
        String coursetime=selectcourse.getCourse_time();

        List<Teachers> teacherslist=teachersService.lambdaQuery()
                .like(Teachers::getName,teachername).list();

        List<CoursePlan> courselist=coursePlanService.lambdaQuery()
                .like(CoursePlan::getCourseName,coursename).list();

        if(teacherid!=null&&teachername!=null)
        {
            if(!Objects.equals(teacherslist.get(0).getName(), teachername)){
                return Result.fail();
            }
        }

        if(courseid!=null&&coursename!=null)
        {
            if(!Objects.equals(courselist.get(0).getCourseName(), coursename)){
                return Result.fail();
            }
        }

        if(courseid==null&&coursename!=null)
        {
            courseid=courselist.get(0).getCourseId();
        }

        if(teacherid==null&&teachername!=null)
        {
            teacherid=teacherslist.get(0).getId();
        }

        List<CurrentCourses> list=null;
        if(coursetime==null) {
            if (teacherid == null && courseid != null) {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getCourseId, courseid).list();
            } else if (teacherid != null && courseid == null) {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getTeacherId, teacherid).list();
            } else {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getTeacherId, teacherid)
                        .like(CurrentCourses::getCourseId, courseid).list();
            }
        }
        else{
            if (teacherid == null && courseid != null) {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getCourseId, courseid)
                        .like(CurrentCourses::getTime,coursetime).list();
            } else if (teacherid != null && courseid == null) {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getTime,coursetime)
                        .like(CurrentCourses::getTeacherId, teacherid).list();
            } else if(teacherid != null && courseid != null){
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getTime,coursetime)
                        .like(CurrentCourses::getTeacherId, teacherid)
                        .like(CurrentCourses::getCourseId, courseid).list();
            }
            else{
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getTime,coursetime).list();
            }
        }



        return list.size()>0?Result.suc(list):Result.fail();
    }
}
