package com.example.project.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ge
 * @since 2023-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Students对象", description="")
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty
    private String name;
    @ApiModelProperty
    private Integer yxh;


}



