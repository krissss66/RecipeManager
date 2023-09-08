package com.recipemanager.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class recipe implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long recipeId;
    private String recipeName;
    private String description;
    private String instruction;
    private Long categoryId;
    private byte[] image;
    private String ingredients;
    @TableField(fill = FieldFill.INSERT) //fill while instert
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE) //fill while instert and update
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;



}
