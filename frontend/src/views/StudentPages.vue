<template>
  <div class="common-layout">
    <el-container>

      <el-header class="top-bar">
        <el-row>

          <el-col :span="8">
            <div style="display: flex; align-items: center; justify-content: center; height: 100%;">
              <span class="text-xl font-bold">聪明教务系统</span>
            </div>
          </el-col>

          <el-col :span="8" :offset="8">
            <div class="flex items-center justify-end h-full">
              <div style="display: flex; align-items: center; justify-content: center; height: 100%;">
                <el-avatar :size="32" class="mr-4"
                  src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
                <span class="text-lg font-semibold mr-5" style="margin-left: 20px ; margin-right: 10px;">{{ this.userName
                }}</span>
                <span class="text-sm mr-4"
                  style="color: var(--el-text-color-regular); position: relative; top: 2px;margin-right: 10px;">{{
                    this.userId }}</span>
                <el-tag type="success" class="ml-2">学生</el-tag>
              </div>
            </div>
          </el-col>

        </el-row>
      </el-header>

      <el-container>

        <el-aside width="200px">
          <el-menu default-active="1" class="el-menu-vertical-demo">
            <el-menu-item index="1" @click="selectFunction('选课')">选课</el-menu-item>
            <el-menu-item index="2" @click="selectFunction('退课'); fetchCourses()">退课</el-menu-item>
            <el-menu-item index="3" @click="selectFunction('成绩查询'); fetchScores()">成绩查询</el-menu-item>
            <el-menu-item index="4" @click="selectFunction('课表查询')">课表查询</el-menu-item>
          </el-menu>
        </el-aside>

        <el-main>
          <div class="main-content-right">

            <div v-if="selectedFunction === '选课'">
              <el-form :model="queryInfo" label-width="100px">
                <el-row>
                  <el-col :span="6">
                    <el-form-item label="课程号" prop="CourseId">
                      <el-input v-model="queryInfo.CourseId" placeholder="请输入课程号"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="课程名" prop="CourseName">
                      <el-input v-model="queryInfo.CourseName" placeholder="请输入课程名"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="教师号" prop="TeacherId">
                      <el-input v-model="queryInfo.TeacherId" placeholder="请输入教师号"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="教师姓名" prop="TeacherName">
                      <el-input v-model="queryInfo.TeacherName" placeholder="请输入教师姓名"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="12">
                    <el-form-item label="上课时间" prop="CourseTime">
                      <el-input v-model="queryInfo.CourseTime" placeholder="请输入上课时间"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>

              <div style="margin: 10px;">
                <el-button type="primary" @click="queryCourses">提交</el-button>
              </div>

              <div v-if="showForm">
                <el-table :data="courseInfo" style="width: 100%" @selection-change="handleSelectionChange">
                  <el-table-column type="selection"></el-table-column>
                  <el-table-column prop="course_id" label="课程号"></el-table-column>
                  <el-table-column prop="course_name" label="课程名"></el-table-column>
                  <el-table-column prop="teacher_id" label="教师号"></el-table-column>
                  <el-table-column prop="teacher_name" label="教师姓名"></el-table-column>
                  <el-table-column prop="capacity" label="课程容量"></el-table-column>
                  <el-table-column prop="selected_number" label="已选人数"></el-table-column>
                  <el-table-column prop="time" label="上课时间"></el-table-column>
                </el-table>
                <div style="margin: 10px;">
                  <el-button type="primary" @click="selectCourses">确认选课</el-button>
                </div>
              </div>
            </div>

            <div v-else-if="selectedFunction === '退课'">
              <div>
                <el-table :data="myCourses" style="width: 100%" @selection-change="handleSelectionChange_delete">
                  <el-table-column type="selection"></el-table-column>
                  <el-table-column prop="course_id" label="课程号"></el-table-column>
                  <el-table-column prop="course_name" label="课程名"></el-table-column>
                  <el-table-column prop="teacher_id" label="教师号"></el-table-column>
                  <el-table-column prop="teacher_name" label="教师姓名"></el-table-column>
                  <el-table-column prop="capacity" label="课程容量"></el-table-column>
                  <el-table-column prop="selected_number" label="已选人数"></el-table-column>
                  <el-table-column prop="time" label="上课时间"></el-table-column>
                </el-table>
                <div style="margin: 10px;">
                  <el-button type="primary" @click="dropCourses">退选所选课程</el-button>
                </div>
              </div>
            </div>

            <div v-else-if="selectedFunction === '成绩查询'">
              <StudentQueryScore :myCourses="myScores"></StudentQueryScore>
            </div>

            <div v-else-if="selectedFunction === '课表查询'">
              <CourseSchedule :myCourses="myCourses"></CourseSchedule>
            </div>

          </div>
        </el-main>

      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";

import StudentQueryScore from "./StudentQueryScore.vue";
import CourseSchedule from "../components/CourseSchedule.vue";

import { ElMessage } from 'element-plus'

export default {
  name: "StudentPages",
  components: {
    StudentQueryScore,
    CourseSchedule
  },

  // 来自父组件的数据
  props: {

  },

  // 在created生命周期钩子中访问路由参数
  created() {
    // console.log("this.$route",this.$route);
    this.userId = this.$route.params.userId;
    this.userName = this.$route.params.userName;
    // console.log("userId", this.userId);
    // console.log("userName", this.userName);
  },

  // data()函数部分
  data() {
    return {
      host: "http://127.0.0.1:9000",
      selectedFunction: "选课", // 默认选中的功能

      // 选课功能中的输入框
      showForm: false,
      queryInfo: {
        CourseId: null,
        CourseName: null,
        TeacherId: 100027,
        TeacherName: null,
        CourseTime: null,
      },

      // 选课功能中的课程信息
      courseInfo: [{
        course_id: "course_id",
        course_name: "course_name",
        teacher_id: "teacher_id",
        teacher_name: "teacher_name",
        capacity: 0,
        selected_number: 0,
        time: "time"
      }],

      // 选课功能中选中的课程号
      selectedCourses: [{
        course_id: "course_id",
        course_name: "course_name",
        teacher_id: "teacher_id",
        teacher_name: "teacher_name",
        capacity: 0,
        selected_number: 0,
        time: "time"
      }],

      // 退课功能中选中的课程号
      deletedCourses: [{
        course_id: "course_id",
        course_name: "course_name",
        teacher_id: "teacher_id",
        teacher_name: "teacher_name",
        capacity: 0,
        selected_number: 0,
        time: "time"
      }],

      // 学生已经选的课程
      myCourses: [{
        course_id: "course_id",
        course_name: "course_name",
        teacher_id: "teacher_id",
        teacher_name: "teacher_name",
        capacity: 0,
        selected_number: 0,
        time: "time",
      }],

      // 学生成绩信息
      myScores: [{
        course_id: "",
        course_name: "",
        teacher_name: "",
        score: 0
      }]
    };
  },

  methods: {
    // 选择功能
    selectFunction(functionName) {
      this.selectedFunction = functionName;
    },

    // 查询功能
    async queryCourses() {
      // 把v-model数据保存到变量中
      const course_id = this.queryInfo.CourseId;
      const course_name = this.queryInfo.CourseName;
      const teacher_id = this.queryInfo.TeacherId;
      const teacher_name = this.queryInfo.TeacherName;
      const course_time = this.queryInfo.CourseTime;

      // 构造请求体
      const apiUrl = `${this.host}/api/courses`;
      const queryParams = {
        course_id: course_id,
        course_name: course_name,
        teacher_id: teacher_id,
        teacher_name: teacher_name,
        course_time: course_time
      };
      await axios.get(apiUrl, { params: queryParams })
        .then(response => {

          // 将查询选课的结果显示到页面上
          const courseData = response.data.data;

          // 把courseData中的数据传递给this.courseInfo
          if (courseData != null) {
            // 显示响应结果
            ElMessage.success('选课信息查询成功');

            console.log("queryCourses method return response.data", response.data);
            // console.log("response.data.data: courseData", courseData);

            this.courseInfo = courseData.map((course) => {
              const selectedCourse = JSON.parse(course);
              return {
                course_id: selectedCourse.course_id,
                course_name: selectedCourse.course_name,
                teacher_id: selectedCourse.teacher_id,
                teacher_name: selectedCourse.teacher_name,
                capacity: selectedCourse.capacity,
                selected_number: selectedCourse.selected_number,
                time: selectedCourse.time
              };
            });
            this.showForm = true;
          } else {
            ElMessage.error('选课信息查询失败');
          }
          console.log("this.courseInfo", this.courseInfo);
          // 显示表单组件

        }, error => {
          // 处理响应失败的情况
          console.error("选课信息查询失败", error);
          ElMessage.error('选课信息查询失败')
        })
    },

    // 查询已选课程
    async fetchCourses() {

      // 构造请求体
      const apiUrl = `${this.host}/api/students/${this.userId}/courses`;

      try {
        // 发送 GET 请求
        const response = await axios.get(apiUrl);

        console.log("return from fetchCourses, response: ", response);

        const courseData = response.data;
        this.myCourses = courseData.data.map(course => JSON.parse(course));
        console.log("this.myCourses", this.myCourses);

      } catch (error) {
        console.error("课表信息查询失败", error);
        ElMessage.error("课表信息查询失败");
      }
    },

    // 更新选课功能中的选中课程到selectedCourse
    handleSelectionChange(selectedRows) {
      console.log("选中的课程 selectedRows:", selectedRows);
      this.selectedCourses = selectedRows;
    },

    // 更新退课功能中的选中课程到deletedCourses
    handleSelectionChange_delete(selectedRows) {
      console.log("选中的课程 selectedRows:", selectedRows);
      this.deletedCourses = selectedRows;
    },

    // 选课功能
    async selectCourses() {
      try {
        // 创建一个空数组，用于存储请求体数据
        const requestBody = [];
        // console.log("selectedCourses", this.selectedCourses);

        // 使用 forEach 方法遍历 selectedCourses 数组
        this.selectedCourses.forEach((course) => {
          // 将每个课程信息转换为一个包含课程信息的对象，并将其添加到 requestBody 数组中
          requestBody.push({
            course_id: course.course_id,
            course_name: course.course_name,
            teacher_id: course.teacher_id,
            teacher_name: course.teacher_name,
            capacity: course.capacity,
            selected_number: course.selected_number,
            time: course.time,
          });
        });

        console.log("选课请求发送的 requestBody", requestBody);

        const apiUrl = `${this.host}/api/students/${this.userId}/courses`;
        const response = await axios.post(apiUrl, requestBody);

        console.log("selectCourses return response: ", response);

        const result = response.data;
        if (result.code == 200) {
          ElMessage.success("选课成功");
          this.selectedCourses = []; // 清空已选课程
          this.fetchCourses(); // 重新查询课表
        }
        else if (result.code == 401) {
          ElMessage.warning(response.data.msg);
        }
        else {
          ElMessage.error("选课失败：" + result.message);
        }
      } catch (error) {
        console.error("选课操作失败", error);
        ElMessage.error("选课操作失败");
      }
    },

    // 退课功能
    async dropCourses() {
      // 发送请求进行退课操作
      try {
        const requestBody = [];

        console.log("deletedCourses", this.deletedCourses);

        this.deletedCourses.forEach((course) => {
          requestBody.push({
            course_id: course.course_id,
            course_name: course.course_name,
            teacher_id: course.teacher_id,
            teacher_name: course.teacher_name,
            capacity: course.capacity,
            selected_number: course.selected_number,
            time: course.time,
          });
        });

        console.log("退课请求发送的 requestBody", requestBody);

        const apiUrl = `${this.host}/api/students/${this.userId}/courses`;
        const response = await axios.delete(apiUrl, { data: requestBody });

        console.log("response return from dropCourses()", response);

        if (response.data.code == 200) {

          ElMessage.success("退课成功");
          this.deletedCourses = []; // 清空已选课程
          this.fetchCourses(); // 重新查询课表
        }
        else {
          ElMessage.error("退课失败：" + response.data.msg);
        }
      } catch (error) {
        console.error("退课操作失败", error);
        ElMessage.error("退课操作失败");
      }
    },

    // 成绩查询功能
    async fetchScores() {
      // 构造请求体
      const apiUrl = `${this.host}/api/students/${this.userId}/courses/score`;
      try {
        // 发送 GET 请求
        const response = await axios.get(apiUrl);

        console.log("return from fetchScores, response: ", response);
        if (response.data.code == 200) {
          ElMessage.success("成绩信息查询成功");
        }
        else {
          ElMessage.error("成绩信息查询失败");
          return;
        }
        const scoreData = response.data;
        this.myScores = scoreData.data.map(score => JSON.parse(score));
        console.log("this.myScores", this.myScores);

      } catch (error) {
        console.error("成绩信息查询失败", error);
        ElMessage.error("成绩信息查询失败");
      }
    },

    mounted() {
      this.fetchCourses();
      this.showForm = false; // 隐藏表单组件
    }
  },
};
</script>

<style>
.top-bar {
  background: #208fcb;
  color: #fff;
  padding: 10px 20px;
  text-align: center;
  border-radius: 10px;
}

.top-bar-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.user-name {
  font-weight: bold;
}

.main-content {
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
}

.sidebar {
  margin-top: 10px;
  width: 100px;
  background: #aee3ed;
  padding: 10px;
  border-radius: 10px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  cursor: pointer;
  padding: 5px;
  border-bottom: 1px solid #ccc;
}

.main-content-right {
  flex: 1;
  padding: 20px;
}

.input-row {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

label {
  font-weight: bold;
  font-size: 14px;
  color: #333;
}

input {
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 14px;
}


.course-table {
  margin-top: 20px;
  margin-bottom: 20px;
  border-collapse: collapse;
  font-family: Arial, sans-serif;
  background-color: #f2f2f2;
  width: 100%;
}

.course-table th,
.course-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.course-table th {
  background-color: #8ac9e2;
  color: white;
}
</style>
  