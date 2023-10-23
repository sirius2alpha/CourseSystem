package com.example.project.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.Selectcourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ge
 * @since 2023-10-21
 */
public interface SelectcourseService extends IService<Selectcourse> {

    IPage pageC(Page<Selectcourse> page);

    IPage pageCC(Page<Selectcourse> page, Wrapper wrapper);
}
