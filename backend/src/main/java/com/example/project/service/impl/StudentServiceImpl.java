package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Students;
import com.example.project.mapper.StudentMapper;
import com.example.project.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ge
 * @since 2023-11-01
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Students> implements StudentService {

    @Resource
    private StudentMapper studentMapper;
    @Override
    public IPage pageC(Page<Students> page) {
        return studentMapper.pageC(page);
    }

    @Override
    public IPage pageCC(Page<Students> page, Wrapper wrapper) {
        return studentMapper.pageCC(page,wrapper);
    }
}
