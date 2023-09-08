package com.recipemanager.common;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//
// Class: MyMetaObjectHandler
//
// Description:
// MyMetaObjectHandler is a class responsible for filling in the create and update time and user
// id when a new entity(recipe, user, ingredients ) is created or updated
//
//
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("insert.... ");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

        metaObject.setValue("createUser", baseContext.getUserID());
        metaObject.setValue("updateUser", baseContext.getUserID());


    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", baseContext.getUserID());


    }
}
