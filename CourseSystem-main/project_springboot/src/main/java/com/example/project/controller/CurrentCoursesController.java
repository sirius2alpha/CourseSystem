package com.example.project.controller;


import com.example.project.common.Result;
import com.example.project.entity.CurrentCourses;
import com.example.project.entity.User;
import com.example.project.service.CurrentCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ge
 * @since 2023-10-23
 */
@RestController
@RequestMapping("/current-courses")
public class CurrentCoursesController {
    @Autowired
    private CurrentCoursesService currentCoursesService;
    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List list = currentCoursesService.lambdaQuery().like(CurrentCourses::getCourseId,roleId).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }
}
