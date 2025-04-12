package com.example.project.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.project.common.Result;
import com.example.project.entity.*;
import com.example.project.mapper.SelectedCoursesMapper;
import com.example.project.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private SelectedCoursesMapper selectedCoursesMapper;

    /**
     * 课程详情
     */
    /*
     * @GetMapping("/{userId}/courses/{courseId}")
     * public Result courses(@PathVariable("userId") Integer
     * teacherId, @PathVariable("courseId") Integer courseId) {
     * LambdaQueryWrapper<CurrentCourses> currentCoursesQueryWrapper = new
     * LambdaQueryWrapper<>();
     * currentCoursesQueryWrapper.eq(CurrentCourses::getCourseId, courseId);
     * currentCoursesQueryWrapper.eq(CurrentCourses::getTeacherId, teacherId);
     * CurrentCourses currentCourses =
     * currentCoursesService.getOne(currentCoursesQueryWrapper);
     * 
     * LambdaQueryWrapper<Teachers> teachersQueryWrapper = new
     * LambdaQueryWrapper<>();
     * teachersQueryWrapper.eq(Teachers::getId, teacherId);
     * Teachers teachers = teachersService.getOne(teachersQueryWrapper);
     * if (null != teachers) {
     * currentCourses.setTeacherName(teachers.getName());
     * }
     * 
     * LambdaQueryWrapper<CoursePlan> coursePlanQueryWrapper = new
     * LambdaQueryWrapper<>();
     * coursePlanQueryWrapper.eq(CoursePlan::getCourseId, courseId);
     * CoursePlan coursePlan = coursePlanService.getOne(coursePlanQueryWrapper);
     * if (null != coursePlan) {
     * currentCourses.setCourseName(coursePlan.getCourseName());
     * }
     * 
     * LambdaQueryWrapper<SelectedCourses> selectedCoursesQueryWrapper = new
     * LambdaQueryWrapper<>();
     * selectedCoursesQueryWrapper.like(SelectedCourses::getCurrentCourseId,
     * currentCourses.getNo());
     * int count = selectedCoursesService.count(selectedCoursesQueryWrapper);
     * currentCourses.setSelectedCount(count);
     * 
     * //课程容量先固定写100
     * currentCourses.setCapacity(100);
     * 
     * return Result.suc(currentCourses);
     * }
     */
    @GetMapping("/{userId}/courses")
    public Result kaike(@PathVariable("userId") Integer teacherId) throws JsonProcessingException {
        List<CurrentCourses> selectno = currentCoursesService.lambdaQuery()
                .eq(CurrentCourses::getTeacherId, teacherId).list();
        if (selectno.size() == 0)
            return Result.fail();
        List<String> response = new ArrayList<>();
        Integer no;
        int courseid, teacherid;

        Courses courses = new Courses();

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
            response.add(i, json);
        }

        return selectno.size() > 0 ? Result.suc(response, selectno.size()) : Result.fail();
    }

    @GetMapping("/{userId}/courses/{selectedCourse}")
    public Result xueshengmingdan(@PathVariable("userId") Integer teacherId,
            @PathVariable("selectedCourse") Integer courseId) throws JsonProcessingException {
        List<CurrentCourses> selectno = currentCoursesService.lambdaQuery()
                .eq(CurrentCourses::getTeacherId, teacherId)
                .eq(CurrentCourses::getCourseId, courseId).list();
        if (selectno.size() == 0)
            return Result.fail("没有学生选这个课");
        List<String> response = new ArrayList<>();
        Integer no = selectno.get(0).getNo();

        List<SelectedCourses> selectno1 = selectedCoursesService.lambdaQuery()
                .eq(SelectedCourses::getCurrentCourseId, no).list();
        for (int i = 0; i < selectno1.size(); i++) {
            score Score = new score();
            Score.setTeacher_id(teacherId);
            Score.setCourse_id(courseId);

            // 获取学生ID和成绩信息
            Integer studentid = selectno1.get(i).getStudentId();
            // 从数据库获取真实成绩，如果为null则设为0
            Double pscj = selectno1.get(i).getPscj() != null ? selectno1.get(i).getPscj() : 0.0;
            Double kscj = selectno1.get(i).getKscj() != null ? selectno1.get(i).getKscj() : 0.0;

            // 设置真实成绩
            Score.setDaily_score(pscj);
            Score.setExamination_score(kscj);

            // 获取学生姓名
            List<Students> stuname = studentsService.lambdaQuery()
                    .eq(Students::getId, studentid).list();
            Score.setStudent_id(studentid);
            Score.setStudent_name(stuname.get(0).getName());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(Score);
            response.add(i, json);
        }

        return selectno.size() > 0 ? Result.suc(response, selectno.size()) : Result.fail();
    }

    @PostMapping("/{userId}/courses/{selectedCourse}")
    public Result updatescore(@RequestBody List<score> Score,
            @PathVariable("userId") Integer teacherId,
            @PathVariable("selectedCourse") Integer courseId) throws JsonProcessingException {
        List<CurrentCourses> selectcourse = currentCoursesService.lambdaQuery()
                .eq(CurrentCourses::getCourseId, courseId)
                .eq(CurrentCourses::getTeacherId, teacherId).list();
        double pscj, kscj, score;
        for (int j = 0; j < Score.size(); j++) {
            pscj = Score.get(j).getDaily_score();
            kscj = Score.get(j).getExamination_score();
            score = pscj * 0.3 + kscj * 0.7;
            LambdaUpdateWrapper<SelectedCourses> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(SelectedCourses::getStudentId, Score.get(j).getStudent_id())
                    .eq(SelectedCourses::getCurrentCourseId, selectcourse.get(0).getNo())
                    .set(SelectedCourses::getPscj, pscj)
                    .set(SelectedCourses::getKscj, kscj)
                    .set(SelectedCourses::getScore, score);
            selectedCoursesMapper.update(null, lambdaUpdateWrapper);
        }
        return Result.suc();
    }

    @PostMapping("/{userId}/courses/{selectedCourse}/scores")
    public Result updateScores(@RequestBody List<score> Score,
                              @PathVariable("userId") Integer teacherId,
                              @PathVariable("selectedCourse") Integer courseId) throws JsonProcessingException {
        return updatescore(Score, teacherId, courseId);
    }
}