package com.example.project.service.impl;

import com.example.project.entity.User;
import com.example.project.mapper.UserMapper;
import com.example.project.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ge
 * @since 2023-10-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
