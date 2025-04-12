package com.example.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.project.common.ApiResponse;
import com.example.project.entity.Students;
import com.example.project.entity.Teachers;
import com.example.project.entity.User;
import com.example.project.service.StudentService;
import com.example.project.service.TeacherService;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用户API控制器
 * 整合用户相关功能
 */
@RestController
@RequestMapping("/api/users")
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    /**
     * 用户登录
     */
    @PostMapping("/{id}/pwd")
    public ApiResponse<?> login(@PathVariable String id, @RequestBody User user) {
        // 验证用户密码
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("password", user.getPassword());
        List<User> users = userService.list(queryWrapper);

        if (users.isEmpty()) {
            return ApiResponse.fail("账号或密码错误");
        }

        // 返回用户角色和姓名信息
        User foundUser = users.get(0);
        LinkedHashMap<String, Object> userInfo = new LinkedHashMap<>();
        userInfo.put("roleId", foundUser.getRoleId());

        if (foundUser.getRoleId() == 1) {
            // 学生
            QueryWrapper<Students> studentWrapper = new QueryWrapper<>();
            studentWrapper.eq("id", id);
            Students student = studentService.getOne(studentWrapper);
            userInfo.put("userName", student != null ? student.getName() : "未知");
        } else {
            // 教师
            QueryWrapper<Teachers> teacherWrapper = new QueryWrapper<>();
            teacherWrapper.eq("id", id);
            Teachers teacher = teacherService.getOne(teacherWrapper);
            userInfo.put("userName", teacher != null ? teacher.getName() : "未知");
        }

        return ApiResponse.success(userInfo);
    }

    /**
     * 获取所有用户
     */
    @GetMapping("/list")
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.success(userService.list());
    }

    /**
     * 添加用户
     */
    @PostMapping("/save")
    public ApiResponse<?> saveUser(@RequestBody User user) {
        boolean success = userService.save(user);
        return success ? ApiResponse.success() : ApiResponse.fail("添加用户失败");
    }

    /**
     * 修改用户
     */
    @PostMapping("/mod")
    public ApiResponse<?> updateUser(@RequestBody User user) {
        boolean success = userService.updateById(user);
        return success ? ApiResponse.success() : ApiResponse.fail("修改用户失败");
    }

    /**
     * 删除用户
     */
    @GetMapping("/delete")
    public ApiResponse<?> deleteUser(@RequestParam Integer id) {
        boolean success = userService.removeById(id);
        return success ? ApiResponse.success() : ApiResponse.fail("删除用户失败");
    }
}