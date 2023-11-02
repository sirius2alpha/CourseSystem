package com.example.project.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.common.QueryPageParam;
import com.example.project.common.Result;
import com.example.project.entity.Students;
import com.example.project.entity.Teachers;
import com.example.project.entity.User;
import com.example.project.service.StudentsService;
import com.example.project.service.TeachersService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ge
 * @since 2023-10-17
 */
@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TeachersService teachersService;

    @Autowired
    private StudentsService studentsService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

//    @GetMapping("/findByNo")
//    public Result findByNo(@RequestParam String no){
//        List list = userService.lambdaQuery().eq(User::getNo,no).list();
//        return list.size()>0?Result.suc(list):Result.fail();
//    }

    //add
    @PostMapping("/save")
    public boolean save(@RequestBody User user){
        return userService.save(user);
    }
    //fix
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }
    //add or fix
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }
    //delete
    @GetMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }
    //query

    //login
    @PostMapping("/{id}/pwd")
    public Result msg(@RequestBody User user){
        List<User> list = userService.lambdaQuery()
                .eq(User::getId,user.getId())
                .eq(User::getPassword,user.getPassword()).list();
        if(list.size()<=0)
            return Result.fail();
        LinkedHashMap usermap=new LinkedHashMap<>();
        usermap.put("roleId",list.get(0).getRoleId());
        if(list.get(0).getRoleId()==1){
            List<Students> stulist=studentsService.lambdaQuery()
                    .eq(Students::getId,user.getId()).list();
            usermap.put("userName",stulist.get(0).getName());
        }
        else {
            List<Teachers> tealist = teachersService.lambdaQuery()
                    .eq(Teachers::getId, user.getId()).list();
            usermap.put("userName",tealist.get(0).getName());
        }

        return Result.suc(usermap);
    }

   /* @PostMapping("/listP")
    public List<User> listP(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getId()) {
            lambdaQueryWrapper.eq(User::getName,user.getName());
        }
        return userService.list(lambdaQueryWrapper);
    }*/
    //分页
    /*@PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){

        //System.out.println("name=="+(String)param.get("name"));
        //System.out.println("num=="+query.getPageNum());
        //System.out.println("size=="+query.getPageSize());

        HashMap param = query.getParam();
        String name=(String)param.get("name");
        System.out.println("name=="+(String)param.get("name"));
        //System.out.println("no=="+(String)param.get("no"));
//        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(User::getName,user.getName());
//        return userService.list(lambdaQueryWrapper);

        Page<User> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName,name);

        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }*/

    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name=(String)param.get("name");

        Page<User> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getId,name);

        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        Map param = query.getParam();
        String name=(String)param.get("name");

        Page<User> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getId,name);

        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println("total=="+result.getTotal());

        return Result.suc(result.getRecords());
    }
}
