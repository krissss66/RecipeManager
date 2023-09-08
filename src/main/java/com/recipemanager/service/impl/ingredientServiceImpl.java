package com.recipemanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recipemanager.entity.ingredients;
import com.recipemanager.mapper.ingredientMapper;
import com.recipemanager.service.ingredientService;
import org.springframework.stereotype.Service;


@Service
public class ingredientServiceImpl extends ServiceImpl<ingredientMapper, ingredients> implements ingredientService{


}
