package com.example.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2023-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SelectedCourses对象", description = "")
public class SelCourses implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 学生编号
     */
    private Integer studentId;
    /**
     * 学生姓名
     */
    @TableField(exist = false)
    private String studentName;
    /**
     * 课程编号
     */
    private Integer currentCourseId;
    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String courseName;
    /**
     * 平时成绩
     */
    private Double pscj;
    /**
     * 考试成绩
     */
    private Double kscj;
    /**
     *
     */
    private Double score;


}