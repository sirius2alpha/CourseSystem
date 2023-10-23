package com.example.project.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.CurrentCourses;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ge
 * @since 2023-10-22
 */
public interface CurrentCoursesService extends IService<CurrentCourses> {

    IPage pageC(Page<CurrentCourses> page);

    IPage pageCC(Page<CurrentCourses> page, Wrapper wrapper);
}
