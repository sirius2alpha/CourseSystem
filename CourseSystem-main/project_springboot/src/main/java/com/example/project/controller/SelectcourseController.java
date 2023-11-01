package com.example.project.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.project.common.JsonResultUtil;
import com.example.project.common.Result;
import com.example.project.entity.*;
import com.example.project.mapper.SelectedCoursesMapper;
import com.example.project.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.core._ArrayEnumeration;
import io.swagger.models.auth.In;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.sql.Types.NULL;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ge
 * @since 2023-10-21
 */
@RestController
@RequestMapping("/api")
public class SelectcourseController {
    private static final Logger logger = LoggerFactory.getLogger(SelectcourseController.class);

    @Autowired
    private CurrentCoursesService currentCoursesService;

    @Autowired
    private CoursePlanService coursePlanService;

    @Autowired
    private SelectedCoursesService selectedCoursesService;

    @Autowired
    private TeachersService teachersService;

    @GetMapping("/list")
    public List<CurrentCourses> list() {
        return currentCoursesService.list();
    }

    @GetMapping("/courses")
    public Result selclass(@RequestParam(name = "course_id", required = false) Integer course_id,
                                   @RequestParam(name = "course_name", required = false) String course_name,
                                   @RequestParam(name = "teacher_id", required = false) Integer teacher_id,
                                   @RequestParam(name = "teacher_name", required = false) String teacher_name,
                                   @RequestParam(name = "course_time", required = false) String course_time) throws JsonProcessingException {
        // 在这里使用查询参数 course_id, course_name, teacher_id, teacher_name, course_time 来处理请求
        // 你可以根据这些参数来过滤和查询数据
        // 注意：`required = false` 表示这些参数是可选的，如果客户端未提供某个参数，它将为null或空字符串
        // 你可以根据需要进行参数验证和数据处理

        Integer courseid = course_id;
        String coursename = course_name;
        Integer teacherid = teacher_id;
        String teachername = teacher_name;
        String coursetime = course_time;


        // new
        /*
        // 根据传入的参数构建查询条件
        YourQueryParams queryParams = new YourQueryParams(course_id, course_name, teacher_id, teacher_name, course_time);

        // 调用Service方法查询数据
        List<YourDataModel> result = yourService.getYourData(queryParams);

        // 构建JSON响应
        List<Map<String, Object>> response = new ArrayList<>();
        for (YourDataModel data : result) {
            Map<String, Object> item = new HashMap<>();
            item.put("course_id", data.getCourseId());
            item.put("course_name", data.getCourseName());
            item.put("teacher_id", data.getTeacherId());
            item.put("teacher_name", data.getTeacherName());
            item.put("capacity", data.getCapacity());
            item.put("selected_number", data.getNumber());
            item.put("time", data.getTime());
            response.add(item);
        }

        return list.size() > 0 ? Result.suc(response) : Result.fail();
        */
        // old
        List<Teachers> teacherslist = teachersService.lambdaQuery()
                .like(Teachers::getName, teachername).list();

        List<CoursePlan> courselist = coursePlanService.lambdaQuery()
                .like(CoursePlan::getCourseName, coursename).list();

        if (teacherid != null && teachername != null) {
            if (!Objects.equals(teacherslist.get(0).getName(), teachername)) {
                return Result.fail();
            }
        }

        if (courseid != null && coursename != null) {
            if (!Objects.equals(courselist.get(0).getCourseName(), coursename)) {
                return Result.fail();
            }
        }

        if (courseid == null && coursename != null) {
            courseid = courselist.get(0).getCourseId();
        }

        if (teacherid == null && teachername != null) {
            teacherid = teacherslist.get(0).getId();
        }

        List<CurrentCourses> list = null;
        if (coursetime == null) {
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
        } else {
            if (teacherid == null && courseid != null) {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getCourseId, courseid)
                        .like(CurrentCourses::getTime, coursetime).list();
            } else if (teacherid != null && courseid == null) {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getTime, coursetime)
                        .like(CurrentCourses::getTeacherId, teacherid).list();
            } else if (teacherid != null && courseid != null) {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getTime, coursetime)
                        .like(CurrentCourses::getTeacherId, teacherid)
                        .like(CurrentCourses::getCourseId, courseid).list();
            } else {
                list = currentCoursesService.lambdaQuery()
                        .like(CurrentCourses::getTime, coursetime).list();
            }
        }

        /*
        // 构建JSON响应
        List<Map<String, Object>> response = new ArrayList<>();
        for (YourDataModel data : result) {
            Map<String, Object> item = new HashMap<>();
            item.put("course_id", data.getCourseId());
            item.put("course_name", data.getCourseName());
            item.put("teacher_id", data.getTeacherId());
            item.put("teacher_name", data.getTeacherName());
            item.put("capacity", data.getCapacity());
            item.put("selected_number", data.getNumber());
            item.put("time", data.getTime());
            response.add(item);
        }
        */
        // 构建JSON响应
        List<String> response = new ArrayList<>();
        Integer no;

        Courses courses=new Courses();

        for (int i = 0; i < list.size(); i++) {
            courseid = list.get(i).getCourseId();
            courses.setCourse_id(courseid);
            //res.put("course_id", courseid);
            List<CoursePlan> coursesname = coursePlanService.lambdaQuery()
                    .eq(CoursePlan::getCourseId, courseid).list();
            courses.setCourse_name(coursesname.get(0).getCourseName());
            //res.put("course_name", coursesname.get(0).getCourseName());
            teacherid = list.get(i).getTeacherId();
            courses.setTeacher_id(teacherid);
            //res.put("teacher_id", teacherid);
            List<Teachers> teachersname = teachersService.lambdaQuery()
                    .eq(Teachers::getId, teacherid).list();
            courses.setTeacher_name(teachersname.get(0).getName());
            //res.put("teacher_name", teachersname.get(0).getName());
            //res.put("capacity", 50);
            courses.setCapacity(50);
            no = list.get(i).getNo();
            List<SelectedCourses> selectno = selectedCoursesService.lambdaQuery()
                    .eq(SelectedCourses::getCurrentCourseId, no).list();
            courses.setSelected_number(selectno.size());
            //res.put("selected_number", selectno.size());
            courses.setTime(list.get(i).getTime());
            //res.put("course_time", list.get(i).getTime());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(courses);
            response.add(i,json);
        }

        return list.size() > 0 ? Result.suc(response,list.size()) : Result.fail();
    }

    @GetMapping("/students/{userId}/courses")
    public Result selectedclass(@PathVariable("userId") String userId) throws JsonProcessingException {
        List<SelectedCourses> selectno=selectedCoursesService.lambdaQuery()
                .eq(SelectedCourses::getStudentId,userId).list();
        if(selectno.size()==0)
            return Result.fail();
        List<String> response = new ArrayList<>();
        Integer no;
        int courseid,teacherid;

        Courses courses=new Courses();

        for (int i = 0; i < selectno.size(); i++) {
            no = selectno.get(i).getCurrentCourseId();
            List<CurrentCourses> list = currentCoursesService.lambdaQuery()
                    .eq(CurrentCourses::getNo, no).list();
            courseid = list.get(0).getCourseId();
            courses.setCourse_id(courseid);
            List<CoursePlan> coursesname = coursePlanService.lambdaQuery()
                    .eq(CoursePlan::getCourseId, courseid).list();
            courses.setCourse_name(coursesname.get(0).getCourseName());
            teacherid = list.get(0).getTeacherId();
            courses.setTeacher_id(teacherid);
            List<Teachers> teachersname = teachersService.lambdaQuery()
                    .eq(Teachers::getId, teacherid).list();
            courses.setTeacher_name(teachersname.get(0).getName());
            courses.setCapacity(50);
            no = list.get(0).getNo();
            List<SelectedCourses> selectno1 = selectedCoursesService.lambdaQuery()
                    .eq(SelectedCourses::getCurrentCourseId, no).list();
            courses.setSelected_number(selectno1.size());
            courses.setTime(list.get(0).getTime());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(courses);
            response.add(i,json);
        }

        return selectno.size() > 0 ? Result.suc(response,selectno.size()) : Result.fail();
    }

    @PostMapping("/students/{userId}/courses")
    public Result selectedclass(@PathVariable("userId") String userid,
                                @RequestBody List<Courses> courses)
    {
        Integer i = null;
        if(userid!=null){
            i = Integer.valueOf(userid);
        }
        for(int j=0;j<courses.size();j++) {
            List<CurrentCourses> selectcourse = currentCoursesService.lambdaQuery()
                    .eq(CurrentCourses::getTime, courses.get(j).getTime())
                    .eq(CurrentCourses::getCourseId, courses.get(j).getCourse_id())
                    .eq(CurrentCourses::getTeacherId, courses.get(j).getTeacher_id()).list();
            List<SelectedCourses> selectedCourse = selectedCoursesService.lambdaQuery()
                    .eq(SelectedCourses::getStudentId, userid)
                    .eq(SelectedCourses::getCurrentCourseId, selectcourse.get(0).getNo()).list();
            SelectedCourses selectedCourses = new SelectedCourses();
            if (selectedCourse.size() > 0)
                return Result.fail();
            else {
                selectedCourses.setCurrentCourseId(selectcourse.get(0).getNo());
                selectedCourses.setStudentId(i);
                selectedCourses.setKscj(null);
                selectedCourses.setPscj(null);
                selectedCourses.setScore(null);
                boolean savecourse = selectedCoursesService.save(selectedCourses);
                if (savecourse)
                    continue;
                else
                    return Result.fail();
            }
        }
        return Result.suc();
    }


    @DeleteMapping("/students/{userId}/delete")
    public Result delcourse(@RequestBody List<Courses> courses,
                            @PathVariable("userId") String userid)
    {
        Integer i = null;
        if(userid!=null){
            i = Integer.valueOf(userid);
        }
        for(int j=0;j<courses.size();j++) {
            List<CurrentCourses> selectcourse = currentCoursesService.lambdaQuery()
                    .eq(CurrentCourses::getTime, courses.get(j).getTime())
                    .eq(CurrentCourses::getCourseId, courses.get(j).getCourse_id())
                    .eq(CurrentCourses::getTeacherId, courses.get(j).getTeacher_id()).list();
            List<SelectedCourses> selectedCourse = selectedCoursesService.lambdaQuery()
                    .eq(SelectedCourses::getStudentId, userid)
                    .eq(SelectedCourses::getCurrentCourseId, selectcourse.get(0).getNo()).list();
            if (selectedCourse.size() == 0)
                return Result.fail();
            else {
                Map<String,Object> selmap=new HashMap<>();
                selmap.put("student_id",i);
                selmap.put("current_course_id",selectcourse.get(0).getNo());
                boolean savecourse = selectedCoursesService.removeByMap(selmap);
                if (savecourse)
                    continue;
                else
                    return Result.fail();
            }
        }
        return Result.suc();
    }

    @GetMapping("/students/{userId}/courses/score")
    public Result scoreclass(@PathVariable("userId") String userid) throws JsonProcessingException {

        List<SelectedCourses> selectno=selectedCoursesService.lambdaQuery()
                .eq(SelectedCourses::getStudentId,userid).list();
        if(selectno.size()==0)
            return Result.fail();
        List<String> response = new ArrayList<>();
        Integer no;
        int courseid,teacherid;

        CourseScore courseScore=new CourseScore();

        for (int i = 0; i < selectno.size(); i++) {
            no = selectno.get(i).getCurrentCourseId();
            List<CurrentCourses> list = currentCoursesService.lambdaQuery()
                    .eq(CurrentCourses::getNo, no).list();
            courseid = list.get(0).getCourseId();
            courseScore.setCourse_id(courseid);
            List<CoursePlan> coursesname = coursePlanService.lambdaQuery()
                    .eq(CoursePlan::getCourseId, courseid).list();
            courseScore.setCourse_name(coursesname.get(0).getCourseName());
            teacherid = list.get(0).getTeacherId();
            List<Teachers> teachersname = teachersService.lambdaQuery()
                    .eq(Teachers::getId, teacherid).list();
            courseScore.setTeacher_name(teachersname.get(0).getName());
            courseScore.setScore(selectno.get(i).getScore());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(courseScore);
            response.add(i,json);
        }

        return selectno.size() > 0 ? Result.suc(response,selectno.size()) : Result.fail();
    }

}
