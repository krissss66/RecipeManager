package com.recipemanager;

import com.recipemanager.entity.ingredients;
import com.recipemanager.entity.recipe;
import com.recipemanager.entity.user;
import com.recipemanager.mapper.recipeMapper;
import com.recipemanager.mapper.userMapper;
import com.recipemanager.service.ingredientService;
import com.recipemanager.service.recipeService;
import com.recipemanager.service.userService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RecipeManagerApplicationTests {


    @Qualifier("ingredientServiceImpl")
    @Autowired
    private ingredientService ss;


    @Test
    public void testSelect(){
        System.out.println("--------selectAll method test-------");
        List<ingredients> userBeanList = ss.list();
        userBeanList.forEach(System.out::println);
    }
}
