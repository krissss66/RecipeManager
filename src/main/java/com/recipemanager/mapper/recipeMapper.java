package com.recipemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.recipemanager.entity.recipe;
import com.recipemanager.entity.user;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface recipeMapper  extends BaseMapper<recipe> {
}
