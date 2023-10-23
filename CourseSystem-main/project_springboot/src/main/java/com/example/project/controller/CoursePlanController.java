package com.example.project.controller;


import com.example.project.common.Result;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.User;
import com.example.project.service.CoursePlanService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ge
 * @since 2023-10-20
 */
@RestController
@RequestMapping("/course-plan")
public class CoursePlanController {

    @Autowired
    private CoursePlanService coursePlanService;

    @PostMapping("/listp")
    public Result msg(@RequestBody CoursePlan coursePlan){
        List<CoursePlan> list = coursePlanService.lambdaQuery()
                .eq(CoursePlan::getCourseName,coursePlan.getCourseName()).list();
        //.eq(User::getPassword,user.getPassword()).list();
        return list.size()>0?Result.suc(list.get(0)):Result.fail();
    }

    @GetMapping("/list")
    public List<CoursePlan> list(){
        return coursePlanService.list();
    }

}
