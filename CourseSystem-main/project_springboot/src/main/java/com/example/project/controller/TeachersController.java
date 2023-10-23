package com.example.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.common.QueryPageParam;
import com.example.project.common.Result;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.Teachers;
import com.example.project.entity.User;
import com.example.project.service.CoursePlanService;
import com.example.project.service.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ge
 * @since 2023-10-21
 */
@RestController
@RequestMapping("/teachers")
public class TeachersController {

    @Autowired
    private TeachersService teachersService;

    @PostMapping("/listp")
    public Result msg(@RequestBody Teachers teachers){
        List<Teachers> list = teachersService.lambdaQuery()
                .eq(Teachers::getId,teachers.getId()).list();
        //.eq(User::getPassword,user.getPassword()).list();
        return list.size()>0?Result.suc(list.get(0)):Result.fail();
    }

    @GetMapping("/list")
    public List<Teachers> list(){
        return teachersService.list();
    }

}
