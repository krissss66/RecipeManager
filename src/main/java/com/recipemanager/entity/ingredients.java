package com.recipemanager.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data                                       //no need to create getter and setter methods for the fields below anymore with @Data
public class ingredients implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long ingredientId;
    private String ingredientName;
    @TableField(fill = FieldFill.INSERT) //fill while instert
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE) //fill while instert and update
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
