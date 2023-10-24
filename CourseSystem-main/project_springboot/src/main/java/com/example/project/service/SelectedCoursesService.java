package com.example.project.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Selectcourse;
import com.example.project.entity.SelectedCourses;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ge
 * @since 2023-10-24
 */
public interface SelectedCoursesService extends IService<SelectedCourses> {

    IPage pageC(Page<SelectedCourses> page);

    IPage pageCC(Page<SelectedCourses> page, Wrapper wrapper);
}
