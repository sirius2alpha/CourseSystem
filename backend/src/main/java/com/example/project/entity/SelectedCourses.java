package com.example.project.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 已选课程实体类
 * 记录学生选择的课程信息和成绩
 *
 * @author ge
 * @since 2023-10-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("selected_courses")
@ApiModel(value = "SelectedCourses对象", description = "已选课程信息")
public class SelectedCourses implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 学生ID
     */
    @MppMultiId
    @ApiModelProperty(value = "学生ID", required = true)
    private String studentId;

    /**
     * 学生姓名（非数据库字段）
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 当前课程ID
     */
    @MppMultiId
    @ApiModelProperty(value = "当前课程ID", required = true)
    private Integer currentCourseId;

    /**
     * 课程名称（非数据库字段）
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    /**
     * 平时成绩
     */
    @ApiModelProperty(value = "平时成绩")
    private Double pscj;

    /**
     * 考试成绩
     */
    @ApiModelProperty(value = "考试成绩")
    private Double kscj;

    /**
     * 总成绩
     */
    @ApiModelProperty(value = "总成绩")
    private Double score;

    /**
     * 获取平时成绩 (兼容方法)
     */
    public Double getDailyScore() {
        return pscj;
    }

    /**
     * 设置平时成绩 (兼容方法)
     */
    public void setDailyScore(Double dailyScore) {
        this.pscj = dailyScore;
    }

    /**
     * 获取考试成绩 (兼容方法)
     */
    public Double getExaminationScore() {
        return kscj;
    }

    /**
     * 设置考试成绩 (兼容方法)
     */
    public void setExaminationScore(Double examinationScore) {
        this.kscj = examinationScore;
    }
}
