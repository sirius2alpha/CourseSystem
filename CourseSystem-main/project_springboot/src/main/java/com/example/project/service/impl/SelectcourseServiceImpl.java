package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.Selectcourse;
import com.example.project.mapper.CoursePlanMapper;
import com.example.project.mapper.SelectcourseMapper;
import com.example.project.service.SelectcourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ge
 * @since 2023-10-21
 */
@Service
public class SelectcourseServiceImpl extends ServiceImpl<SelectcourseMapper, Selectcourse> implements SelectcourseService {

    @Resource
    private SelectcourseMapper selectcourseMapper;

    @Override
    public IPage pageC(Page<Selectcourse> page) {
        return selectcourseMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<Selectcourse> page, Wrapper wrapper) {
        return selectcourseMapper.pageCC(page,wrapper);
    }
}
