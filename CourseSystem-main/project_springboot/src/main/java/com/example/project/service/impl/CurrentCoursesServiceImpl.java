package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.CurrentCourses;
import com.example.project.mapper.CoursePlanMapper;
import com.example.project.mapper.CurrentCoursesMapper;
import com.example.project.service.CurrentCoursesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ge
 * @since 2023-10-22
 */
@Service
public class CurrentCoursesServiceImpl extends ServiceImpl<CurrentCoursesMapper, CurrentCourses> implements CurrentCoursesService {

    @Resource
    private CurrentCoursesMapper currentCoursesMapper;

    @Override
    public IPage pageC(Page<CurrentCourses> page) {
        return currentCoursesMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<CurrentCourses> page, Wrapper wrapper) {
        return currentCoursesMapper.pageCC(page,wrapper);
    }
}
