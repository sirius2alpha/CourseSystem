package com.example.project.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ge
 * @since 2023-10-20
 */
public interface CoursePlanService extends IService<CoursePlan> {

    IPage pageC(Page<CoursePlan> page);

    IPage pageCC(Page<CoursePlan> page, Wrapper wrapper);
}
