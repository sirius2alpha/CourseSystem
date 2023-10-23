package com.example.project.entity;

import java.io.Serializable;
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
 * @since 2023-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Selectcourse对象", description="")
public class Selectcourse implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty
    private Integer course_id;
    @ApiModelProperty
    private String course_name;
    @ApiModelProperty
    private Integer teacher_id;
    @ApiModelProperty
    private String teacher_name;
    @ApiModelProperty
    private String course_time;


}
