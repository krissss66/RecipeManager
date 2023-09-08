package com.recipemanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.recipemanager.entity.ingredients;
import com.recipemanager.entity.user;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ingredientMapper extends BaseMapper<ingredients> {


}
