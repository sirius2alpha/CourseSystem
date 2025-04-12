<template>
  <div class="score-container">
    <div v-if="!myCourses || myCourses.length === 0" class="empty-state">
      <i class="el-icon-data-analysis empty-icon"></i>
      <p>暂无成绩数据</p>
    </div>
    <template v-else>
      <div class="score-summary">
        <div class="summary-card">
          <div class="summary-value">{{ averageScore }}</div>
          <div class="summary-label">平均分</div>
        </div>
        <div class="summary-card">
          <div class="summary-value">{{ passedCourses }}</div>
          <div class="summary-label">已通过课程</div>
        </div>
        <div class="summary-card">
          <div class="summary-value">{{ failedCourses }}</div>
          <div class="summary-label">未通过课程</div>
        </div>
        <div class="summary-card">
          <div class="summary-value">{{ unpublishedCourses }}</div>
          <div class="summary-label">未发布成绩</div>
        </div>
      </div>
      
      <el-table 
        :data="myCourses" 
        style="width: 100%" 
        :header-cell-style="{ 'text-align': 'center', 'background': '#f5f7fa' }" 
        border
        stripe
      >
        <el-table-column prop="course_id" label="课程号" align="center" width="120" />
        <el-table-column prop="course_name" label="课程名" align="center" min-width="180" />
        <el-table-column prop="teacher_name" label="教师姓名" align="center" width="120" />
        <el-table-column label="分数" align="center" width="120">
          <template #default="scope">
            <div v-if="isScorePublished(scope.row.score)" class="score-cell" :class="getScoreClass(getDisplayScore(scope.row))">
              {{ getDisplayScore(scope.row) }}
            </div>
            <div v-else class="score-cell unpublished">
              未发布
            </div>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" width="100">
          <template #default="scope">
            <el-tag v-if="isScorePublished(scope.row.score) && getDisplayScore(scope.row) !== '未发布'" :type="getScoreStatus(getDisplayScore(scope.row)).type" size="small">
              {{ getScoreStatus(getDisplayScore(scope.row)).label }}
            </el-tag>
            <el-tag v-else type="info" size="small">
              未发布
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
      
      <div v-if="hasPublishedScores" class="score-chart">
        <h4 class="chart-title">成绩分布</h4>
        <div class="chart-container">
          <div class="score-bars">
            <div v-for="(count, range) in scoreDistribution" :key="range" class="score-bar-container">
              <div class="score-bar" :style="{ height: `${count * 20}px` }" :class="getBarClass(range)"></div>
              <div class="score-range">{{ range }}</div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
export default {
  name: "StudentQueryScore",
  props: {
    myCourses: {
      type: Array,
      required: true,
    },
  },
  computed: {
    // 过滤出已发布成绩的课程
    publishedCourses() {
      return this.myCourses.filter(course => this.isScorePublished(course.score));
    },
    
    // 判断是否有已发布的成绩
    hasPublishedScores() {
      return this.publishedCourses.length > 0;
    },
    
    // 计算平均分（只计算已发布的成绩）
    averageScore() {
      if (!this.hasPublishedScores) return "暂无";
      
      let validScores = 0;
      const totalScore = this.publishedCourses.reduce((sum, course) => {
        const score = this.getDisplayScore(course);
        // 确保分数是有效数字，而不是"未发布"等文本
        if (score !== "未发布" && !isNaN(parseFloat(score))) {
          validScores++;
          return sum + parseFloat(score);
        }
        return sum;
      }, 0);
      
      if (validScores === 0) return "暂无";
      return (totalScore / validScores).toFixed(1);
    },
    
    // 计算已通过课程数量
    passedCourses() {
      return this.publishedCourses.filter(course => parseFloat(this.getDisplayScore(course)) >= 60).length;
    },
    
    // 计算未通过课程数量
    failedCourses() {
      return this.publishedCourses.filter(course => parseFloat(this.getDisplayScore(course)) < 60).length;
    },
    
    // 计算未发布成绩的课程数量
    unpublishedCourses() {
      return this.myCourses.length - this.publishedCourses.length;
    },
    
    // 计算成绩分布
    scoreDistribution() {
      const distribution = {
        '90-100': 0,
        '80-89': 0,
        '70-79': 0,
        '60-69': 0,
        '0-59': 0
      };
      
      this.publishedCourses.forEach(course => {
        const score = parseFloat(this.getDisplayScore(course));
        if (score >= 90) {
          distribution['90-100']++;
        } else if (score >= 80) {
          distribution['80-89']++;
        } else if (score >= 70) {
          distribution['70-79']++;
        } else if (score >= 60) {
          distribution['60-69']++;
        } else {
          distribution['0-59']++;
        }
      });
      
      return distribution;
    }
  },
  methods: {
    // 判断成绩是否已发布
    isScorePublished(score) {
      // 检查总评成绩
      if (score !== null && score !== undefined && score !== "" && score !== "未发布") {
        return true;
      }
      
      // 如果总评成绩不存在，还可以检查行数据的平时成绩和考试成绩
      const row = this.myCourses.find(course => course.score === score);
      if (row) {
        const hasDailyScore = row.daily_score !== null && row.daily_score !== undefined && row.daily_score !== 0;
        const hasExamScore = row.examination_score !== null && row.examination_score !== undefined && row.examination_score !== 0;
        
        // 如果平时成绩或考试成绩有值，也认为成绩已发布
        return hasDailyScore || hasExamScore;
      }
      
      return false;
    },
    
    // 获取成绩样式类
    getScoreClass(score) {
      const numScore = parseFloat(score);
      if (numScore >= 90) return 'excellent';
      if (numScore >= 80) return 'good';
      if (numScore >= 70) return 'average';
      if (numScore >= 60) return 'pass';
      return 'fail';
    },
    
    // 获取成绩状态
    getScoreStatus(score) {
      // 如果分数是"未发布"或不是有效数字，返回"未发布"状态
      if (score === "未发布" || isNaN(parseFloat(score))) {
        return { type: 'info', label: '未发布' };
      }
      
      const numScore = parseFloat(score);
      if (numScore >= 90) return { type: 'success', label: '优秀' };
      if (numScore >= 80) return { type: 'success', label: '良好' };
      if (numScore >= 70) return { type: 'success', label: '中等' };
      if (numScore >= 60) return { type: 'success', label: '及格' };
      return { type: 'danger', label: '不及格' };
    },
    
    // 获取柱状图样式类
    getBarClass(range) {
      switch (range) {
        case '90-100': return 'excellent';
        case '80-89': return 'good';
        case '70-79': return 'average';
        case '60-69': return 'pass';
        default: return 'fail';
      }
    },
    
    // 获取显示分数
    getDisplayScore(row) {
      // 如果有总评成绩，直接返回
      if (row.score !== null && row.score !== undefined && row.score !== "" && row.score !== "未发布") {
        return row.score;
      }
      
      // 否则根据平时成绩和考试成绩计算总评成绩
      const dailyScore = parseFloat(row.daily_score || 0);
      const examScore = parseFloat(row.examination_score || 0);
      
      // 只有当平时成绩和考试成绩都有有效值时才计算
      if (!isNaN(dailyScore) && !isNaN(examScore) && (dailyScore > 0 || examScore > 0)) {
        // 按照 40% 平时成绩 + 60% 考试成绩计算
        const finalScore = (dailyScore * 0.4 + examScore * 0.6).toFixed(1);
        return finalScore;
      }
      
      return "未发布";
    }
  }
};
</script>

<style scoped>
.score-container {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 3rem;
  color: var(--text-secondary);
}

.empty-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.score-summary {
  display: flex;
  gap: 1.5rem;
  flex-wrap: wrap;
}

.summary-card {
  flex: 1;
  min-width: 150px;
  background: white;
  border-radius: var(--radius);
  padding: 1.5rem;
  box-shadow: var(--shadow);
  text-align: center;
  transition: all 0.3s ease;
}

.summary-card:hover {
  transform: translateY(-5px);
}

.summary-value {
  font-size: 2rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  color: var(--primary-color);
}

.summary-label {
  font-size: 0.9rem;
  color: var(--text-secondary);
}

.score-cell {
  font-weight: 600;
  padding: 0.25rem 0.75rem;
  border-radius: 4px;
}

.excellent {
  color: #f56c6c;
  background-color: rgba(245, 108, 108, 0.1);
}

.good {
  color: #e6a23c;
  background-color: rgba(230, 162, 60, 0.1);
}

.average {
  color: #409eff;
  background-color: rgba(64, 158, 255, 0.1);
}

.pass {
  color: #67c23a;
  background-color: rgba(103, 194, 58, 0.1);
}

.fail {
  color: #909399;
  background-color: rgba(144, 147, 153, 0.1);
}

.unpublished {
  color: #909399;
  background-color: rgba(144, 147, 153, 0.1);
  font-style: italic;
}

.chart-title {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 1rem;
  color: var(--text-primary);
}

.chart-container {
  background: white;
  border-radius: var(--radius);
  padding: 1.5rem;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

.score-bars {
  display: flex;
  justify-content: space-around;
  align-items: flex-end;
  height: 200px;
}

.score-bar-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 60px;
}

.score-bar {
  width: 40px;
  min-height: 4px;
  border-radius: 4px 4px 0 0;
  transition: height 0.5s ease;
}

.score-range {
  margin-top: 0.5rem;
  font-size: 0.8rem;
  color: var(--text-secondary);
}

.score-chart {
  margin-top: 1.5rem;
}
</style>