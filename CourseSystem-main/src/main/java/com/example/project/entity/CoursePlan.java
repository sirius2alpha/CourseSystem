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
 * @since 2023-10-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CoursePlan对象", description="")
public class CoursePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    private String courseName;

    private Integer courseId;

    private Integer collegeId;


}
