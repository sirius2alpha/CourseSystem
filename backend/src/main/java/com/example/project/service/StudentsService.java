package com.example.project.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Students;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project.entity.Teachers;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ge
 * @since 2023-11-01
 */
public interface StudentsService extends IService<Students> {

    IPage pageC(Page<Students> page);

    IPage pageCC(Page<Students> page, Wrapper wrapper);
}
