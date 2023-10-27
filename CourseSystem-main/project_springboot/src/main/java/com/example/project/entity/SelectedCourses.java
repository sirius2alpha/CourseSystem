package com.example.project.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
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
 * @since 2023-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SelectedCourses对象", description="")
public class SelectedCourses implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty
    private Integer studentId;
    @ApiModelProperty
    private Integer currentCourseId;
    @ApiModelProperty
    private Double pscj;
    @ApiModelProperty
    private Double kscj;
    @ApiModelProperty
    private Double score;


}
