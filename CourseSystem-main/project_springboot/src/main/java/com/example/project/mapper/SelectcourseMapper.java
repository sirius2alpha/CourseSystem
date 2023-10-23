package com.example.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.example.project.entity.Selectcourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ge
 * @since 2023-10-21
 */
public interface SelectcourseMapper extends BaseMapper<Selectcourse> {

    IPage pageC(Page<Selectcourse> page);

    IPage pageCC(Page<Selectcourse> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
