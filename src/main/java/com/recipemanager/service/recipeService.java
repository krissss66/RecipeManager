package com.recipemanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.recipemanager.entity.recipe;
import com.recipemanager.entity.user;
import org.springframework.stereotype.Service;


@Service
public interface recipeService extends IService<recipe> {
}
