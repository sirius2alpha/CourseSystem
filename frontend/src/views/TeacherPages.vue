<template>
  <div class="teacher-dashboard">
    <!-- 顶部导航栏 -->
    <header class="dashboard-header">
      <div class="header-container">
        <div class="logo-container">
          <img src="/logo.png" alt="Logo" class="logo" onerror="this.src='/favicon.ico'" />
          <h1 class="site-title">智慧教务系统</h1>
        </div>
        
        <div class="user-info">
          <el-dropdown trigger="click">
            <div class="user-profile">
              <el-avatar :size="40" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
              <div class="user-details">
                <span class="user-name">{{ userName }}</span>
                <span class="user-id">{{ userId }}</span>
              </div>
              <i class="el-icon-arrow-down"></i>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="showContactMessage">个人信息</el-dropdown-item>
                <el-dropdown-item @click="showContactMessage">修改密码</el-dropdown-item>
                <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <el-tag type="warning" effect="dark" class="role-tag">教师</el-tag>
        </div>
      </div>
    </header>

    <div class="dashboard-content">
      <!-- 侧边栏导航 -->
      <aside class="sidebar">
        <div class="menu-container">
          <div class="menu-header">
            <span class="menu-title">功能导航</span>
          </div>
          <el-menu
            :default-active="activeMenu"
            class="sidebar-menu"
            @select="handleMenuSelect"
          >
            <el-menu-item index="开课详情">
              <i class="el-icon-notebook-1"></i>
              <span>开课详情</span>
            </el-menu-item>
            <el-menu-item index="成绩录入">
              <i class="el-icon-edit"></i>
              <span>成绩录入</span>
            </el-menu-item>
            <el-menu-item index="成绩分析">
              <i class="el-icon-data-analysis"></i>
              <span>成绩分析</span>
            </el-menu-item>
            <el-menu-item index="开设课程">
              <i class="el-icon-plus"></i>
              <span>开设课程</span>
            </el-menu-item>
          </el-menu>
        </div>
      </aside>

      <!-- 主内容区域 -->
      <main class="main-content">
        <div class="content-header">
          <h2 class="page-title">{{ selectedFunction }}</h2>
          <div class="breadcrumb">
            <span>首页</span>
            <i class="el-icon-arrow-right"></i>
            <span>{{ selectedFunction }}</span>
          </div>
        </div>

        <div class="content-body">
          <!-- 开课详情 -->
          <div v-if="selectedFunction === '开课详情'" class="card">
            <h3 class="panel-title">我的课程</h3>
            <el-table 
              :data="myCourses" 
              style="width: 100%" 
              :header-cell-style="{ background: '#f5f7fa' }"
              border
              stripe
              highlight-current-row
            >
              <el-table-column prop="course_id" label="课程号" width="100" />
              <el-table-column prop="course_name" label="课程名" min-width="150" />
              <el-table-column prop="teacher_id" label="教师号" width="100" />
              <el-table-column prop="teacher_name" label="教师姓名" width="120" />
              <el-table-column prop="capacity" label="课程容量" width="100" />
              <el-table-column prop="selected_number" label="已选人数" width="100" />
              <el-table-column prop="time" label="上课时间" min-width="180" />
              <el-table-column label="操作" width="120" align="center">
                <template #default="scope">
                  <el-button type="text" @click="viewStudents(scope.row)">
                    查看学生
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>

          <!-- 成绩录入 -->
          <div v-else-if="selectedFunction === '成绩录入'" class="card">
            <h3 class="panel-title">成绩录入</h3>
            <div class="course-select-container">
              <el-form :inline="true">
                <el-form-item label="选择课程班级">
                  <el-select 
                    v-model="selectedCourse" 
                    placeholder="请选择课程" 
                    @change="fetchStudents"
                    style="width: 300px;"
                  >
                    <el-option 
                      v-for="course in myCourses" 
                      :key="course.course_id" 
                      :label="`${course.course_name} (${course.course_id})`" 
                      :value="course.course_id" 
                    />
                  </el-select>
                </el-form-item>
              </el-form>
            </div>

            <div v-if="tableData.length > 0" class="student-score-table">
              <el-table 
                :data="tableData" 
                style="width: 100%" 
                :header-cell-style="{ background: '#f5f7fa' }"
                border
                stripe
              >
                <el-table-column prop="student_id" label="学号" width="120" />
                <el-table-column prop="student_name" label="姓名" width="120" />
                <el-table-column label="平时成绩" width="180">
                  <template #default="scope">
                    <div v-if="scope.row.isEditing || !scope.row.hasScore">
                      <el-input-number 
                        v-model="scope.row.daily_score" 
                        :min="0" 
                        :max="100" 
                        :precision="1"
                        controls-position="right"
                        size="small"
                      />
                    </div>
                    <div v-else class="score-display">
                      {{ scope.row.daily_score }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="考试成绩" width="180">
                  <template #default="scope">
                    <div v-if="scope.row.isEditing || !scope.row.hasScore">
                      <el-input-number 
                        v-model="scope.row.examination_score" 
                        :min="0" 
                        :max="100" 
                        :precision="1"
                        controls-position="right"
                        size="small"
                      />
                    </div>
                    <div v-else class="score-display">
                      {{ scope.row.examination_score }}
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="总评成绩" width="120">
                  <template #default="scope">
                    <span class="final-score">
                      {{ calculateFinalScore(scope.row) }}
                    </span>
                  </template>
                </el-table-column>
                <el-table-column label="操作" width="120">
                  <template #default="scope">
                    <el-button 
                      v-if="!scope.row.isEditing && scope.row.hasScore" 
                      type="text" 
                      @click="editScore(scope.row)"
                    >
                      修改
                    </el-button>
                    <el-button 
                      v-else-if="scope.row.isEditing" 
                      type="text" 
                      @click="cancelEdit(scope.row)"
                    >
                      取消
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
              
              <div class="table-footer">
                <el-button type="primary" @click="submitScore" :loading="submitting">
                  <i class="el-icon-check"></i> 保存成绩
                </el-button>
                <el-button @click="resetScores">
                  <i class="el-icon-refresh"></i> 重置
                </el-button>
              </div>
            </div>
            
            <div v-else-if="selectedCourse" class="empty-state">
              <i class="el-icon-user empty-icon"></i>
              <p>该课程暂无学生</p>
            </div>
            
            <div v-else class="empty-state">
              <i class="el-icon-select empty-icon"></i>
              <p>请先选择一个课程</p>
            </div>
          </div>

          <!-- 成绩分析 -->
          <div v-else-if="selectedFunction === '成绩分析'" class="card">
            <h3 class="panel-title">成绩分析</h3>
            <div class="empty-state">
              <i class="el-icon-data-line empty-icon"></i>
              <p>功能开发中，敬请期待</p>
            </div>
          </div>

          <!-- 开设课程 -->
          <div v-else-if="selectedFunction === '开设课程'" class="card">
            <h3 class="panel-title">开设新课程</h3>
            <div class="empty-state">
              <i class="el-icon-plus empty-icon"></i>
              <p>功能开发中，敬请期待</p>
            </div>
          </div>
        </div>
      </main>
    </div>
    
    <!-- 学生名单对话框 -->
    <el-dialog
      title="课程学生名单"
      v-model="studentsDialogVisible"
      width="600px"
    >
      <el-table 
        :data="courseStudents" 
        style="width: 100%" 
        :header-cell-style="{ background: '#f5f7fa' }"
        border
        stripe
      >
        <el-table-column prop="student_id" label="学号" width="120" />
        <el-table-column prop="student_name" label="姓名" width="120" />
        <el-table-column prop="daily_score" label="平时成绩" width="100" />
        <el-table-column prop="examination_score" label="考试成绩" width="100" />
        <el-table-column label="总评成绩" width="100">
          <template #default="scope">
            {{ calculateFinalScore(scope.row) }}
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
    
<script>
import axios from "axios";
import { ElMessage } from 'element-plus'

export default {
  name: "TeacherPages",
  components: {},
  props: {},
  created() {
    this.userId = this.$route.params.userId;
    this.userName = this.$route.params.userName;
    this.fetchCourses();
  },
  data() {
    return {
      host: "http://127.0.0.1:9000",
      selectedFunction: "开课详情",
      activeMenu: "开课详情",
      selectedCourse: "",
      myCourses: [],
      tableData: [],
      SubmitData: [],
      submitting: false,
      studentsDialogVisible: false,
      courseStudents: [],
      originalScores: {},
    };
  },
  methods: {
    handleMenuSelect(key) {
      this.selectedFunction = key;
      this.activeMenu = key;
      
      if (key === '开课详情') {
        this.fetchCourses();
      }
    },
    
    async fetchCourses() {
      try {
        const apiUrl = `${this.host}/api/teachers/${this.userId}/courses`;
        const response = await axios.get(apiUrl);

        if (response.data.code === 200) {
          const courseData = response.data;
          this.myCourses = courseData.data.map(course => JSON.parse(course));
        } else {
          ElMessage.error("课程信息查询失败");
        }
      } catch (error) {
        console.error("课程信息查询失败", error);
        ElMessage.error("课程信息查询失败");
      }
    },

    async fetchStudents() {
      if (!this.selectedCourse) return;
      
      this.loading = true;
      try {
        const apiUrl = `${this.host}/api/teachers/${this.userId}/courses/${this.selectedCourse}`;
        console.log('请求URL:', apiUrl);
        
        const response = await axios.get(apiUrl);
        console.log('获取到的学生数据:', response.data);
        
        if (response.data.code === 200) {
          const studentsData = response.data.data;
          console.log('学生数据结构:', studentsData);
          
          if (Array.isArray(studentsData)) {
            this.tableData = studentsData.map(studentJson => {
              const student = JSON.parse(studentJson);
              console.log('解析后的学生数据:', student);
              
              const hasScore = student.daily_score !== null && student.daily_score !== undefined && 
                              student.examination_score !== null && student.examination_score !== undefined &&
                              (student.daily_score > 0 || student.examination_score > 0);
              
              return {
                student_id: student.student_id,
                student_name: student.student_name,
                daily_score: parseFloat(student.daily_score),
                examination_score: parseFloat(student.examination_score),
                hasScore: hasScore,
                isEditing: !hasScore
              };
            });
            
            this.fetchStudentNames();
            
            this.saveOriginalScores();
          } else {
            console.error('返回的数据不是数组格式:', studentsData);
            this.$message.error('数据格式错误');
            this.tableData = [];
          }
        } else {
          this.$message.error("学生信息查询失败");
          this.tableData = [];
        }
      } catch (error) {
        console.error('获取学生列表失败:', error);
        this.$message.error('获取学生列表失败');
        this.tableData = [];
      } finally {
        this.loading = false;
      }
    },
    
    viewStudents(course) {
      this.selectedCourse = course.course_id;
      this.fetchStudentList();
    },
    
    async fetchStudentList() {
      try {
        const apiUrl = `${this.host}/api/teachers/${this.userId}/courses/${this.selectedCourse}`;
        const response = await axios.get(apiUrl);

        if (response.data.code === 200) {
          const courseData = response.data;
          this.courseStudents = courseData.data.map(course => JSON.parse(course));
          this.studentsDialogVisible = true;
        } else {
          ElMessage.error("学生名单查询失败");
        }
      } catch (error) {
        console.error("学生名单查询失败", error);
        ElMessage.error("学生名单查询失败");
      }
    },

    async submitScore() {
      if (!this.selectedCourse || this.tableData.length === 0) {
        this.$message.warning('请先选择课程并确保有学生数据');
        return;
      }
      
      this.submitting = true;
      try {
        const apiUrl = `${this.host}/api/teachers/${this.userId}/courses/${this.selectedCourse}`;
        
        const submitData = this.tableData.map(student => ({
          student_id: student.student_id,
          daily_score: student.daily_score,
          examination_score: student.examination_score
        }));
        
        console.log('提交的成绩数据:', submitData);
        
        const response = await axios.post(apiUrl, submitData);
        
        if (response.data.code === 200) {
          this.$message.success('成绩保存成功');
          
          this.tableData.forEach(student => {
            student.hasScore = true;
            student.isEditing = false;
          });
          
          this.saveOriginalScores();
        } else {
          this.$message.error(`成绩保存失败: ${response.data.message || '未知错误'}`);
        }
      } catch (error) {
        console.error('成绩保存失败:', error);
        this.$message.error('成绩保存失败，请稍后重试');
      } finally {
        this.submitting = false;
      }
    },
    
    resetScores() {
      this.tableData.forEach(student => {
        if (this.originalScores[student.student_id]) {
          student.daily_score = this.originalScores[student.student_id].daily_score;
          student.examination_score = this.originalScores[student.student_id].examination_score;
        }
        student.isEditing = !student.hasScore;
      });
    },
    
    calculateFinalScore(student) {
      const dailyScore = parseFloat(student.daily_score) || 0;
      const examScore = parseFloat(student.examination_score) || 0;
      
      const finalScore = (dailyScore * 0.4 + examScore * 0.6).toFixed(1);
      return finalScore;
    },
    
    logout() {
      ElMessage.success("退出登录成功");
      this.$router.push('/');
    },
    
    showContactMessage() {
      this.$message({
        message: '请联系教务处进行修改',
        type: 'info',
        duration: 3000
      });
    },
    
    saveOriginalScores() {
      this.originalScores = {};
      this.tableData.forEach(student => {
        this.originalScores[student.student_id] = {
          daily_score: student.daily_score,
          examination_score: student.examination_score
        };
      });
    },
    
    editScore(row) {
      this.originalScores[row.student_id] = {
        daily_score: row.daily_score,
        examination_score: row.examination_score
      };
      
      row.isEditing = true;
    },
    
    cancelEdit(row) {
      if (this.originalScores[row.student_id]) {
        row.daily_score = this.originalScores[row.student_id].daily_score;
        row.examination_score = this.originalScores[row.student_id].examination_score;
      }
      
      row.isEditing = false;
    },
    
    async fetchStudentNames() {
      const studentsWithoutNames = this.tableData.filter(student => 
        student.student_id && (!student.student_name || student.student_name === '未知姓名')
      );
      
      if (studentsWithoutNames.length === 0) return;
      
      try {
        const studentIds = studentsWithoutNames.map(student => student.student_id);
        console.log('需要获取姓名的学生ID:', studentIds);
        
        const apiUrl = `${this.host}/api/students/batch`;
        const response = await axios.post(apiUrl, { studentIds });
        
        if (response.data.code === 200) {
          const studentsInfo = response.data.data;
          
          this.tableData = this.tableData.map(student => {
            const studentInfo = studentsInfo.find(info => info.student_id === student.student_id);
            if (studentInfo) {
              return {
                ...student,
                student_name: studentInfo.student_name
              };
            }
            return student;
          });
        }
      } catch (error) {
        console.error('获取学生姓名失败:', error);
      }
    },
    
  }
};
</script>
    
<style scoped>
.teacher-dashboard {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa;
}

.dashboard-header {
  background: linear-gradient(135deg, #f39c12, #e67e22);
  color: white;
  padding: 0.75rem 1.5rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1400px;
  margin: 0 auto;
  width: 100%;
}

.logo-container {
  display: flex;
  align-items: center;
}

.logo {
  height: 40px;
  margin-right: 1rem;
}

.site-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: var(--radius);
  transition: background-color 0.3s;
}

.user-profile:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.user-details {
  display: flex;
  flex-direction: column;
  line-height: 1.2;
}

.user-name {
  font-weight: 600;
  font-size: 0.95rem;
}

.user-id {
  font-size: 0.8rem;
  opacity: 0.8;
}

.role-tag {
  font-size: 0.75rem;
  height: 24px;
  line-height: 22px;
}

.dashboard-content {
  display: flex;
  flex: 1;
  max-width: 1400px;
  margin: 0 auto;
  width: 100%;
  padding: 1.5rem;
  gap: 1.5rem;
}

.sidebar {
  width: 220px;
  flex-shrink: 0;
}

.menu-container {
  background-color: white;
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  overflow: hidden;
}

.menu-header {
  padding: 1rem;
  background: linear-gradient(to right, #f39c12, #e67e22);
  color: white;
}

.menu-title {
  font-weight: 600;
  font-size: 1rem;
}

.sidebar-menu {
  border-right: none;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
}

.page-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0;
  color: var(--text-primary);
}

.breadcrumb {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  color: var(--text-secondary);
  font-size: 0.85rem;
}

.content-body {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.card {
  background: white;
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 1.5rem;
}

.panel-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin-top: 0;
  margin-bottom: 1.5rem;
  color: var(--text-primary);
  border-bottom: 1px solid #eee;
  padding-bottom: 0.75rem;
}

.course-select-container {
  margin-bottom: 1.5rem;
}

.student-score-table {
  margin-top: 1rem;
}

.table-footer {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
}

.final-score {
  font-weight: 600;
  color: #409eff;
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

@media (max-width: 768px) {
  .dashboard-content {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
  }
}

.score-display {
  font-weight: 600;
  padding: 0 8px;
  line-height: 32px;
}
</style>
    