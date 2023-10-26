package com.example.project.controller;


import com.example.project.common.Result;
import com.example.project.entity.*;
import com.example.project.service.*;
import io.swagger.models.auth.In;
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
                           @RequestParam(name = "course_time", required = false) String course_time) {
        // 在这里使用查询参数 course_id, course_name, teacher_id, teacher_name, course_time 来处理请求
        // 你可以根据这些参数来过滤和查询数据
        // 注意：`required = false` 表示这些参数是可选的，如果客户端未提供某个参数，它将为null或空字符串
        // 你可以根据需要进行参数验证和数据处理

        Integer courseid = course_id;
        String coursename = course_name;
        Integer teacherid = teacher_id;
        String teachername = teacher_name;
        String coursetime = course_time;

        // 你可以在这里使用 logger 打印日志
        logger.info("course_id: " + course_id);
        logger.info("course_name: " + course_name);
        logger.info("teacher_id: " + teacher_id);
        logger.info("teacher_name: " + teacher_name);
        logger.info("course_time: " + course_time);

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
        List<LinkedHashMap> response = new ArrayList<>();
        Integer no;

        for (int i = 0; i < list.size(); i++) {
            LinkedHashMap res = new LinkedHashMap();
            courseid = list.get(i).getCourseId();
            res.put("course_id", courseid);
            List<CoursePlan> coursesname = coursePlanService.lambdaQuery()
                    .eq(CoursePlan::getCourseId, courseid).list();
            res.put("course_name", coursesname.get(0).getCourseName());
            teacherid = list.get(i).getTeacherId();
            res.put("teacher_id", teacherid);
            List<Teachers> teachersname = teachersService.lambdaQuery()
                    .eq(Teachers::getId, teacherid).list();
            res.put("teacher_name", teachersname.get(0).getName());
            res.put("capacity", 50);
            no = list.get(i).getNo();
            List<SelectedCourses> selectno = selectedCoursesService.lambdaQuery()
                    .eq(SelectedCourses::getCurrentCourse_id, no).list();
            res.put("selected_number", selectno.size());
            res.put("course_time", list.get(i).getTime());
            response.add(i, res);
        }

        return list.size() > 0 ? Result.suc(response) : Result.fail();
    }

    @PostMapping("/students/courses")
    public Result selectedclass(@RequestBody SelectedCourses selectedCourses) {
        Integer courseId = selectedCourses.getCurrentCourse_id();
        List<SelectedCourses> selectno = selectedCoursesService.lambdaQuery()
                .eq(SelectedCourses::getCurrentCourse_id, courseId).list();
        if (selectno.size() == 0)
            return Result.fail();
        /*
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

         */
        return Result.suc(selectno);
    }
}
