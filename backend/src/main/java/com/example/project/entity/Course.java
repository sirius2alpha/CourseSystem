package com.example.project.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 课程表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Course对象", description = "课程")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 课程编号
     */
    @TableId
    private Long courseId;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 教师编号
     */
    public Integer teacherId;
    /**
     * 教师姓名(非数据库字段)
     */
    @TableField(exist = false)
    private String teacherName;
    /**
     * 课程容量
     */
    private Integer capacity;
    /**
     * 已选人数(非数据库字段)
     */
    @TableField(exist = false)
    private Integer selectedCount;
    /**
     * 上课时间
     */
    private String time;
}
