package com.example.project.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ge
 * @since 2023-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CoursePlan对象", description="")
public class CoursePlan implements Serializable {

    private static final long serialVersionUID = 1L;
    private String courseName;
    private Integer courseId;
    public Integer collegeId;
}
