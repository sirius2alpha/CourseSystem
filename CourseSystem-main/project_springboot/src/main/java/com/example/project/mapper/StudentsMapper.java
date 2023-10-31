package com.example.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.Students;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.entity.Teachers;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ge
 * @since 2023-11-01
 */
public interface StudentsMapper extends BaseMapper<Students> {

    IPage pageC(Page<Students> page);

    IPage pageCC(Page<Students> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
