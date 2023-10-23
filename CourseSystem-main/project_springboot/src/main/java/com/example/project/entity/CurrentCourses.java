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
 * @since 2023-10-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CurrentCourses对象", description="")
public class CurrentCourses implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty
    @TableId(value = "no", type = IdType.AUTO)
    private Integer no;
    @ApiModelProperty
    private String time;
    @ApiModelProperty
    private Integer teacherId;
    @ApiModelProperty
    private Integer courseId;
}
