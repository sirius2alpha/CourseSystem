package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.Teachers;
import com.example.project.mapper.CoursePlanMapper;
import com.example.project.mapper.TeachersMapper;
import com.example.project.service.TeachersService;
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
public class TeachersServiceImpl extends ServiceImpl<TeachersMapper, Teachers> implements TeachersService {

    @Resource
    private TeachersMapper teachersMapper;
    @Override
    public IPage pageC(Page<Teachers> page) {
        return teachersMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<Teachers> page, Wrapper wrapper) {
        return teachersMapper.pageCC(page,wrapper);
    }
}
