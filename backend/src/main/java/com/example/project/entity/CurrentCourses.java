package com.example.project.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@TableName("current_courses")
@ApiModel(value = "CurrentCourses对象", description = "")
public class CurrentCourses implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer no;

    private String time;

    public Integer teacherId;

    /**
     * 教师姓名(非数据库字段)
     */
    @TableField(exist = false)
    private String teacherName;

    private Integer courseId;

    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String courseName;

    /**
     * 已选人数
     */
    @TableField(exist = false)
    private Integer selectedCount;

    /**
     * 课程容量
     */
    @TableField(exist = false)
    private Integer capacity;
}
