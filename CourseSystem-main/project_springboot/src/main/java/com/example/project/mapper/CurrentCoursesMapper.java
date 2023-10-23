package com.example.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CurrentCourses;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.entity.Selectcourse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ge
 * @since 2023-10-22
 */
public interface CurrentCoursesMapper extends BaseMapper<CurrentCourses> {

    IPage pageC(Page<CurrentCourses> page);

    IPage pageCC(Page<CurrentCourses> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
