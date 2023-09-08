package com.recipemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recipemanager.entity.recipe;
import com.recipemanager.mapper.recipeMapper;
import com.recipemanager.service.recipeService;
import org.springframework.stereotype.Service;


@Service
public class recipeServiceImpl extends ServiceImpl<recipeMapper, recipe> implements recipeService {



}
