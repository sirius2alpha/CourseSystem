package com.example.project.controller;


import com.example.project.common.Result;
import com.example.project.entity.*;
import com.example.project.service.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    private SelectedCoursesService selectedCoursesService;

    @Autowired
    private TeachersService teachersService;

    @GetMapping("/list")
    public List<CurrentCourses> list(){
        return currentCoursesService.list();
    }

    @GetMapping ("/courses")
    public Result selclass(@RequestBody Selectcourse selectcourse){
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

        List<LinkedHashMap> response=new ArrayList<>();
        Integer no;

        for(int i=0;i<list.size();i++)
        {
            LinkedHashMap res=new LinkedHashMap();
            courseid=list.get(i).getCourseId();
            res.put("course_id",courseid);
            List<CoursePlan> coursesname=coursePlanService.lambdaQuery()
                    .eq(CoursePlan::getCourseId,courseid).list();
            res.put("course_name",coursesname.get(0).getCourseName());
            teacherid=list.get(i).getTeacherId();
            res.put("teacher_id",teacherid);
            List<Teachers> teachersname=teachersService.lambdaQuery()
                    .eq(Teachers::getId,teacherid).list();
            res.put("teacher_name",teachersname.get(0).getName());
            res.put("capacity",50);
            no=list.get(i).getNo();
            List<SelectedCourses> selectno=selectedCoursesService.lambdaQuery()
                    .eq(SelectedCourses::getCurrentCourse_id,no).list();
            res.put("selected_number",selectno.size());
            res.put("course_time",list.get(i).getTime());
            response.add(i,res);
        }

        return list.size()>0?Result.suc(response):Result.fail();
    }

    @GetMapping("/students/{userId}/courses")
    public Result seletedclass(@PathVariable("userId") String userId)
    {
        Integer userid = Integer.valueOf(userId);
        System.out.println(userid);
        List<SelectedCourses> selectno=selectedCoursesService.lambdaQuery()
                .eq(SelectedCourses::getStudent_id,userid).list();
        if(selectno.size()==0)
            return Result.fail();
        List<LinkedHashMap> response=new ArrayList<>();
        int courseno,courseid,teacherid;
        for(int i=0;i<selectno.size();i++)
        {
            LinkedHashMap res=new LinkedHashMap();
            courseno=selectno.get(i).getCurrentCourse_id();
            List<CurrentCourses> list = currentCoursesService.lambdaQuery()
                    .eq(CurrentCourses::getNo,courseno).list();
            courseid=list.get(0).getCourseId();
            res.put("course_id",courseid);
            List<CoursePlan> coursesname=coursePlanService.lambdaQuery()
                    .eq(CoursePlan::getCourseId,courseid).list();
            res.put("course_name",coursesname.get(0).getCourseName());
            teacherid=list.get(0).getTeacherId();
            res.put("teacher_id",teacherid);
            List<Teachers> teachersname=teachersService.lambdaQuery()
                    .eq(Teachers::getId,teacherid).list();
            res.put("teacher_name",teachersname.get(0).getName());
            res.put("capacity",50);
            List<SelectedCourses> selectno1=selectedCoursesService.lambdaQuery()
                    .eq(SelectedCourses::getCurrentCourse_id,courseno).list();
            res.put("selected_number",selectno1.size());
            res.put("course_time",list.get(0).getTime());
            response.add(i,res);
        }
        return Result.suc(response);
    }
}
