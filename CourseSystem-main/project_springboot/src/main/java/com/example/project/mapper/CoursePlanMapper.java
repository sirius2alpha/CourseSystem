package com.example.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.CoursePlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ge
 * @since 2023-10-20
 */
@Mapper
public interface CoursePlanMapper extends BaseMapper<CoursePlan> {

    IPage pageC(Page<CoursePlan> page);

    IPage pageCC(Page<CoursePlan> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
