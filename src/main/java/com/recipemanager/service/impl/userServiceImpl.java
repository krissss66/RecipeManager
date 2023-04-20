package com.recipemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recipemanager.entity.user;
import com.recipemanager.mapper.userMapper;
import com.recipemanager.service.userService;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl extends ServiceImpl<userMapper, user> implements userService {


}