package com.recipemanager;

import com.recipemanager.entity.user;
import com.recipemanager.mapper.userMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RecipeManagerApplicationTests {

    @Autowired
    private userMapper userMapper;


    @Test
    public void testSelect(){
        System.out.println("--------selectAll method test-------");
        List<user> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
