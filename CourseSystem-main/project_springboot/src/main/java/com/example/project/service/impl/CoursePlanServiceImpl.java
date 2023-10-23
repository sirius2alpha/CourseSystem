package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.User;
import com.example.project.mapper.CoursePlanMapper;
import com.example.project.mapper.UserMapper;
import com.example.project.service.CoursePlanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ge
 * @since 2023-10-20
 */
@Service
public class CoursePlanServiceImpl extends ServiceImpl<CoursePlanMapper, CoursePlan> implements CoursePlanService {

    @Resource
    private CoursePlanMapper coursePlanMapper;
    @Override
    public IPage pageC(Page<CoursePlan> page) {
        return coursePlanMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<CoursePlan> page, Wrapper wrapper) {
        return coursePlanMapper.pageCC(page,wrapper);
    }
}
