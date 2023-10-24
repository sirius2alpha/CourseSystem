package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Selectcourse;
import com.example.project.entity.SelectedCourses;
import com.example.project.mapper.SelectcourseMapper;
import com.example.project.mapper.SelectedCoursesMapper;
import com.example.project.service.SelectedCoursesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ge
 * @since 2023-10-24
 */
@Service
public class SelectedCoursesServiceImpl extends ServiceImpl<SelectedCoursesMapper, SelectedCourses> implements SelectedCoursesService {

    @Resource
    private SelectedCoursesMapper selectedCoursesMapper;

    @Override
    public IPage pageC(Page<SelectedCourses> page) {
        return selectedCoursesMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<SelectedCourses> page, Wrapper wrapper) {
        return selectedCoursesMapper.pageCC(page,wrapper);
    }
}
