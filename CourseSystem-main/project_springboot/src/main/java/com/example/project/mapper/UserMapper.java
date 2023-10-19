package com.example.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ge
 * @since 2023-10-17
 */
public interface UserMapper extends BaseMapper<User> {

    IPage pageC(Page<User> page);

    IPage pageCC(Page<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
