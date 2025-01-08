package com.example.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project.entity.Course;
import com.example.project.mapper.CourseMapper;
import com.example.project.service.CourseService;
import org.springframework.stereotype.Service;

/**
 * 课程服务实现类
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

}
