package com.example.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Selectcourse;
import com.example.project.entity.SelectedCourses;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ge
 * @since 2023-10-24
 */
public interface SelectedCoursesMapper extends BaseMapper<SelectedCourses> {

    IPage pageC(Page<SelectedCourses> page);

    IPage pageCC(Page<SelectedCourses> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
