<template>
  <div class="student-dashboard">
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
          <el-tag type="success" effect="dark" class="role-tag">学生</el-tag>
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
            <el-menu-item index="选课">
              <i class="el-icon-plus"></i>
              <span>选课</span>
            </el-menu-item>
            <el-menu-item index="退课">
              <i class="el-icon-remove"></i>
              <span>退课</span>
            </el-menu-item>
            <el-menu-item index="成绩查询">
              <i class="el-icon-data-line"></i>
              <span>成绩查询</span>
            </el-menu-item>
            <el-menu-item index="课表查询">
              <i class="el-icon-date"></i>
              <span>课表查询</span>
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
          <!-- 选课功能 -->
          <div v-if="selectedFunction === '选课'" class="course-selection">
            <div class="search-panel card">
              <h3 class="panel-title">课程查询</h3>
              <el-form :model="queryInfo" label-position="top">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="12" :md="6">
                    <el-form-item label="课程号">
                      <el-input v-model="queryInfo.CourseId" placeholder="请输入课程号" clearable />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="6">
                    <el-form-item label="课程名">
                      <el-input v-model="queryInfo.CourseName" placeholder="请输入课程名" clearable />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="6">
                    <el-form-item label="教师号">
                      <el-input v-model="queryInfo.TeacherId" placeholder="请输入教师号" clearable />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="6">
                    <el-form-item label="教师姓名">
                      <el-input v-model="queryInfo.TeacherName" placeholder="请输入教师姓名" clearable />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="12">
                    <el-form-item label="上课时间">
                      <el-input v-model="queryInfo.CourseTime" placeholder="请输入上课时间" clearable />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" class="search-buttons">
                    <el-button type="primary" @click="queryCourses" :loading="loading">
                      <i class="el-icon-search"></i> 查询课程
                    </el-button>
                    <el-button @click="resetQuery">
                      <i class="el-icon-refresh"></i> 重置
                    </el-button>
                  </el-col>
                </el-row>
              </el-form>
            </div>

            <div v-if="showForm" class="results-panel card">
              <h3 class="panel-title">查询结果</h3>
              <el-table
                :data="courseInfo"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                :header-cell-style="{ background: '#f5f7fa' }"
                border
                stripe
                highlight-current-row
              >
                <el-table-column type="selection" width="55" />
                <el-table-column prop="course_id" label="课程号" width="100" />
                <el-table-column prop="course_name" label="课程名" min-width="150" />
                <el-table-column prop="teacher_id" label="教师号" width="100" />
                <el-table-column prop="teacher_name" label="教师姓名" min-width="120" />
                <el-table-column prop="capacity" label="课程容量" width="100" />
                <el-table-column prop="selected_number" label="已选人数" width="100" />
                <el-table-column prop="time" label="上课时间" min-width="180" />
              </el-table>
              
              <div class="table-footer">
                <el-button type="primary" @click="selectCourses" :disabled="!selectedCourses.length">
                  <i class="el-icon-check"></i> 确认选课
                </el-button>
                <div class="selection-info" v-if="selectedCourses.length">
                  已选择 {{ selectedCourses.length }} 门课程
                </div>
              </div>
            </div>
          </div>

          <!-- 退课功能 -->
          <div v-else-if="selectedFunction === '退课'" class="course-drop card">
            <h3 class="panel-title">已选课程</h3>
            <el-alert
              v-if="!myCourses.length"
              title="您当前没有已选课程"
              type="info"
              :closable="false"
              show-icon
            />
            <template v-else>
              <el-table
                :data="myCourses"
                style="width: 100%"
                @selection-change="handleSelectionChange_delete"
                :header-cell-style="{ background: '#f5f7fa' }"
                border
                stripe
              >
                <el-table-column type="selection" width="55" />
                <el-table-column prop="course_id" label="课程号" width="100" />
                <el-table-column prop="course_name" label="课程名" min-width="150" />
                <el-table-column prop="teacher_id" label="教师号" width="100" />
                <el-table-column prop="teacher_name" label="教师姓名" min-width="120" />
                <el-table-column prop="capacity" label="课程容量" width="100" />
                <el-table-column prop="selected_number" label="已选人数" width="100" />
                <el-table-column prop="time" label="上课时间" min-width="180" />
              </el-table>
              
              <div class="table-footer">
                <el-button 
                  type="danger" 
                  @click="dropCourses" 
                  :disabled="!deletedCourses.length"
                >
                  <i class="el-icon-delete"></i> 退选所选课程
                </el-button>
                <div class="selection-info" v-if="deletedCourses.length">
                  已选择 {{ deletedCourses.length }} 门课程退选
                </div>
              </div>
            </template>
          </div>

          <!-- 成绩查询功能 -->
          <div v-else-if="selectedFunction === '成绩查询'" class="score-query card">
            <h3 class="panel-title">成绩查询</h3>
            <StudentQueryScore :myCourses="myScores" />
          </div>

          <!-- 课表查询功能 -->
          <div v-else-if="selectedFunction === '课表查询'" class="schedule-query card">
            <h3 class="panel-title">我的课表</h3>
            <CourseSchedule :myCourses="myCourses" />
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import StudentQueryScore from "./StudentQueryScore.vue";
import CourseSchedule from "../components/CourseSchedule.vue";
import { ElMessage } from 'element-plus';

export default {
  name: "StudentPages",
  components: {
    StudentQueryScore,
    CourseSchedule
  },
  created() {
    this.userId = this.$route.params.userId;
    this.userName = this.$route.params.userName;
    this.activeMenu = this.selectedFunction;
  },
  data() {
    return {
      host: "http://127.0.0.1:9000",
      selectedFunction: "选课",
      activeMenu: "选课",
      loading: false,
      showForm: false,
      queryInfo: {
        CourseId: null,
        CourseName: null,
        TeacherId: null,
        TeacherName: null,
        CourseTime: null,
      },
      courseInfo: [],
      selectedCourses: [],
      deletedCourses: [],
      myCourses: [],
      myScores: []
    };
  },
  methods: {
    handleMenuSelect(key) {
      this.selectedFunction = key;
      this.activeMenu = key;
      
      if (key === '退课') {
        this.fetchCourses();
      } else if (key === '成绩查询') {
        this.fetchScores();
      } else if (key === '课表查询') {
        this.fetchCourses();
      }
    },
    
    resetQuery() {
      this.queryInfo = {
        CourseId: null,
        CourseName: null,
        TeacherId: null,
        TeacherName: null,
        CourseTime: null,
      };
      this.showForm = false;
    },
    
    async queryCourses() {
      this.loading = true;
      const course_id = this.queryInfo.CourseId;
      const course_name = this.queryInfo.CourseName;
      const teacher_id = this.queryInfo.TeacherId;
      const teacher_name = this.queryInfo.TeacherName;
      const course_time = this.queryInfo.CourseTime;

      const apiUrl = `${this.host}/api/courses`;
      const queryParams = {
        course_id: course_id,
        course_name: course_name,
        teacher_id: teacher_id,
        teacher_name: teacher_name,
        course_time: course_time
      };
      
      try {
        const response = await axios.get(apiUrl, { params: queryParams });
        const courseData = response.data.data;

        if (courseData != null) {
          ElMessage.success('选课信息查询成功');
          this.courseInfo = courseData.map((course) => {
            const selectedCourse = typeof course === 'string' ? JSON.parse(course) : course;
            return {
              course_id: selectedCourse.course_id || selectedCourse.courseId,
              course_name: selectedCourse.course_name || selectedCourse.courseName,
              teacher_id: selectedCourse.teacher_id || selectedCourse.teacherId,
              teacher_name: selectedCourse.teacher_name || selectedCourse.teacherName,
              capacity: selectedCourse.capacity,
              selected_number: selectedCourse.selected_number || selectedCourse.selectedNumber,
              time: selectedCourse.time
            };
          });
          this.showForm = true;
        } else {
          ElMessage.warning('未找到符合条件的课程');
        }
      } catch (error) {
        console.error("选课信息查询失败", error);
        ElMessage.error('选课信息查询失败');
      } finally {
        this.loading = false;
      }
    },

    async fetchCourses() {
      const apiUrl = `${this.host}/api/students/${this.userId}/courses`;

      try {
        const response = await axios.get(apiUrl);
        const courseData = response.data;
        if (courseData.code === 200 && courseData.data) {
          this.myCourses = courseData.data.map(course => {
            const courseObj = typeof course === 'string' ? JSON.parse(course) : course;
            return {
              course_id: courseObj.course_id || courseObj.courseId,
              course_name: courseObj.course_name || courseObj.courseName,
              teacher_id: courseObj.teacher_id || courseObj.teacherId,
              teacher_name: courseObj.teacher_name || courseObj.teacherName,
              capacity: courseObj.capacity,
              selected_number: courseObj.selected_number || courseObj.selectedNumber,
              time: courseObj.time
            };
          });
        } else {
          ElMessage.warning(courseData.message || "当前没有已选课程");
          this.myCourses = [];
        }
      } catch (error) {
        console.error("课表信息查询失败", error);
        ElMessage.error("课表信息查询失败");
        this.myCourses = [];
      }
    },

    handleSelectionChange(selectedRows) {
      this.selectedCourses = selectedRows;
    },

    handleSelectionChange_delete(selectedRows) {
      this.deletedCourses = selectedRows;
    },

    async selectCourses() {
      if (!this.selectedCourses.length) {
        ElMessage.warning("请先选择课程");
        return;
      }
      
      try {
        const requestBody = this.selectedCourses.map(course => ({
          courseId: course.course_id,
          courseName: course.course_name,
          teacherId: course.teacher_id,
          teacherName: course.teacher_name,
          capacity: course.capacity,
          selectedNumber: course.selected_number,
          time: course.time,
        }));

        console.log("选课请求数据:", JSON.stringify(requestBody));
        const apiUrl = `${this.host}/api/students/${this.userId}/courses`;
        const response = await axios.post(apiUrl, requestBody);

        if (response.data.code == 200) {
          ElMessage.success("选课成功");
          this.selectedCourses = [];
          this.fetchCourses();
        } else if (response.data.code == 401) {
          ElMessage.warning(response.data.msg);
        } else {
          ElMessage.error("选课失败：" + response.data.message);
        }
      } catch (error) {
        console.error("选课操作失败", error);
        if (error.response && error.response.data) {
          ElMessage.error("选课失败：" + error.response.data.message);
        } else {
          ElMessage.error("选课操作失败，请稍后重试");
        }
      }
    },

    async dropCourses() {
      if (!this.deletedCourses.length) {
        ElMessage.warning("请先选择要退选的课程");
        return;
      }
      
      try {
        const requestBody = this.deletedCourses.map(course => ({
          courseId: course.course_id,
          courseName: course.course_name,
          teacherId: course.teacher_id,
          teacherName: course.teacher_name,
          capacity: course.capacity,
          selectedNumber: course.selected_number,
          time: course.time,
        }));

        console.log("退课请求数据:", JSON.stringify(requestBody));
        const apiUrl = `${this.host}/api/students/${this.userId}/courses`;
        const response = await axios.delete(apiUrl, { data: requestBody });

        if (response.data.code == 200) {
          ElMessage.success("退课成功");
          this.deletedCourses = [];
          this.fetchCourses();
        } else {
          ElMessage.error("退课失败：" + response.data.message);
        }
      } catch (error) {
        console.error("退课操作失败", error);
        if (error.response && error.response.data) {
          ElMessage.error("退课失败：" + error.response.data.message);
        } else {
          ElMessage.error("退课操作失败，请稍后重试");
        }
      }
    },

    async fetchScores() {
      const apiUrl = `${this.host}/api/students/${this.userId}/courses/score`;
      try {
        console.log('开始获取成绩数据...');
        const response = await axios.get(apiUrl);
        console.log('获取到的原始成绩数据:', response.data);
        
        const scoreData = response.data;
        
        if (scoreData.code === 200 && scoreData.data) {
          // 标准化成绩数据格式
          this.myScores = scoreData.data.map(score => {
            // 如果是字符串，先解析为对象
            const scoreObj = typeof score === 'string' ? JSON.parse(score) : score;
            console.log('处理成绩数据项:', scoreObj);
            
            // 返回标准化后的成绩对象，兼容两种命名格式
            return {
              course_id: scoreObj.course_id || scoreObj.courseId,
              course_name: scoreObj.course_name || scoreObj.courseName,
              teacher_id: scoreObj.teacher_id || scoreObj.teacherId,
              teacher_name: scoreObj.teacher_name || scoreObj.teacherName,
              time: scoreObj.time,
              daily_score: scoreObj.daily_score || scoreObj.dailyScore,
              examination_score: scoreObj.examination_score || scoreObj.examinationScore,
              score: scoreObj.score,
              capacity: scoreObj.capacity,
              selected_number: scoreObj.selected_number || scoreObj.selectedNumber
            };
          });
          
          console.log('成绩数据加载成功:', this.myScores);
        } else {
          console.warn('成绩数据为空或格式不正确:', scoreData);
          ElMessage.warning(scoreData.message || "暂无成绩信息");
          this.myScores = [];
        }
      } catch (error) {
        console.error("成绩信息查询失败", error);
        console.error("错误详情:", error.response ? error.response.data : '无响应数据');
        ElMessage.error("成绩信息查询失败");
        this.myScores = [];
      }
    },
    
    logout() {
      ElMessage.success("退出登录成功");
      this.$router.push('/');
    },

    searchCourses() {
      this.queryInfo.CourseId = this.searchCourseId;
      this.queryInfo.CourseName = this.searchCourseName;
      this.queryInfo.TeacherName = this.searchTeacherName;
      this.queryCourses();
    },

    resetSearch() {
      this.searchCourseId = null;
      this.searchCourseName = null;
      this.searchTeacherName = null;
      this.resetQuery();
    },

    showContactMessage() {
      this.$message({
        message: '请联系教务处进行修改',
        type: 'info',
        duration: 3000
      });
    }
  },
  watch: {
    selectedFunction(newVal) {
      if (newVal === '成绩查询') {
        this.fetchScores();
      }
    }
  },
  mounted() {
    this.fetchCourses();
    if (this.selectedFunction === '成绩查询') {
      this.fetchScores();
    }
  }
};
</script>

<style scoped>
.student-dashboard {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa;
}

.dashboard-header {
  background: linear-gradient(135deg, #3498db, #2ecc71);
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
  background: linear-gradient(to right, #3498db, #2ecc71);
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

.panel-title {
  font-size: 1.2rem;
  font-weight: 600;
  margin-top: 0;
  margin-bottom: 1.5rem;
  color: var(--text-primary);
  border-bottom: 1px solid #eee;
  padding-bottom: 0.75rem;
}

.search-buttons {
  display: flex;
  align-items: flex-end;
  gap: 1rem;
}

.table-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 1.5rem;
}

.selection-info {
  color: var(--text-secondary);
  font-size: 0.9rem;
}

.search-buttons-row {
  display: flex;
  justify-content: center;
  margin-top: 1rem;
  margin-bottom: 1rem;
}

.search-buttons-container {
  display: flex;
  gap: 1rem;
}

@media (max-width: 768px) {
  .dashboard-content {
    flex-direction: column;
  }
  
  .sidebar {
    width: 100%;
  }
}
</style>
  