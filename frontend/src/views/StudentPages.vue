<template>
  <div>
    <header class="top-bar">
      <div class="top-bar-content">
        <span>聪明教务系统</span>
        <span class="user-name">{{ userName }}</span>
      </div>
    </header>

    <div class="main-content">
      <aside class="sidebar">
        <ul>
          <li @click="selectFunction('选课')">选课</li>
          <!--选中退课的时候同时触发方法fetchCourse()-->
          <li @click="selectFunction('退课'); fetchCourses()">退课</li>
          <li @click="selectFunction('成绩查询')">成绩查询</li>
          <li @click="selectFunction('课表查询')">课表查询</li>
        </ul>
      </aside>

      <div class="main-content-right">

        <div v-if="selectedFunction === '选课'">
          <!-- 包括输入课程号、教师号、教师姓名的表单 -->
          <div class="input-row">

            <div class="input-group">
              <label for="CourseId">课程号</label>
              <input type="number" id="CourseId" v-model="queryInfo.CourseId">
            </div>

            <div class="input-group">
              <label for="CourseName">课程名</label>
              <input type="text" id="CourseName" v-model="queryInfo.CourseName">
            </div>

            <div class="input-group">
              <label for="TeacherId">教师号</label>
              <input type="number" id="TeacherId" v-model="queryInfo.TeacherId">
            </div>

            <div class="input-group">
              <label for="TeacherName">教师姓名</label>
              <input type="text" id="TeacherName" v-model="queryInfo.TeacherName">
            </div>

            <div class="input-group">
              <label for="CourseTime">上课时间</label>
              <input type="text" id="CourseTime" v-model="queryInfo.CourseTime">
            </div>

          </div>
          <!--提交按钮-->
          <div style="margin: 10px;">
            <input type="button" value="提交" @click="queryCourses">
          </div>
          <!--选课记录返回，用列表进行呈现-->

          <!--展示查询到的选课信息-->
          <form v-if="showForm">
            <table class="course-table">
              <tr>
                <th></th>
                <th>课程号</th>
                <th>课程名</th>
                <th>教师号</th>
                <th>教师姓名</th>
                <th>课程容量</th>
                <th>已选人数</th>
                <th>上课时间</th>
              </tr>
              <tr v-for="course in courseInfo" :key="course.course_id">
                <td><input type="checkbox" v-model="selectedCourses" :value="course.course_id"></td>
                <td>{{ course.course_id }}</td>
                <td>{{ course.course_name }}</td>
                <td>{{ course.teacher_id }}</td>
                <td>{{ course.teacher_name }}</td>
                <td>{{ course.capacity }}</td>
                <td>{{ course.selected_number }}</td>
                <td>{{ course.time }}</td>
              </tr>
            </table>
            <button @click="selectCourses">确认选课</button>
          </form>
        </div>

        <div v-else-if="selectedFunction === '退课'">
          <!--先把课表查出来，前面再加一个多选框，直接选择然后点击退课就行-->
          <div>
            <form>
              <table class="course-table">
                <tr>
                  <th></th>
                  <th>课程号</th>
                  <th>课程名</th>
                  <th>教师号</th>
                  <th>教师姓名</th>
                  <th>课程容量</th>
                  <th>已选人数</th>
                  <th>上课时间</th>
                </tr>
                <tr v-for="course in myCourses" :key="course.course_id">
                  <td><input type="checkbox" v-model="deletedCourses" :value="course.course_id"></td>
                  <td>{{ course.course_id }}</td>
                  <td>{{ course.course_name }}</td>
                  <td>{{ course.teacher_id }}</td>
                  <td>{{ course.teacher_name }}</td>
                  <td>{{ course.capacity }}</td>
                  <td>{{ course.selected_number }}</td>
                  <td>{{ course.time }}</td>
                </tr>
              </table>
              <button @click="dropCourses">退选所选课程</button>
            </form>
          </div>
        </div>

        <div v-else-if="selectedFunction === '成绩查询'">
          <StudentQueryScore :myCourses="myCourses"></StudentQueryScore>
        </div>

        <div v-else-if="selectedFunction === '课表查询'">
          <CourseSchedule :myCourses="myCourses"></CourseSchedule>
        </div>

      </div>
    </div>
  </div>
</template>
  
<script>
import axios from "axios";

import StudentQueryScore from "./StudentQueryScore.vue";
import CourseSchedule from "../components/CourseSchedule.vue";

export default {
  name: "StudentPages",
  components: {
    StudentQueryScore,
    CourseSchedule
  },

  // 来自父组件的数据
  props: {
    userId: {
      type: String,
      required: true,
    },
    userName: {
      type: String,
      required: true,
    }
  },

  // data()函数部分
  data() {
    return {
      host: "https://127.0.0.1:9000",
      selectedFunction: "选课", // 默认选中的功能

      // 选课功能中的输入框
      showForm: false,
      queryInfo: {
        CourseId: "CourseId",
        CourseName: "CourseName",
        TeacherId: "TeacherId",
        TeacherName: "TeacherName",
        CourseTime: "CourseTime",
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
        score: 0
      }],
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

      try {
        // 发送 GET 请求
        const response = await axios.get(apiUrl, { params: queryParams });
        console.log("选课信息查询成功", response.data);
        // 将查询选课的结果显示到页面上

        // 用JSON.parse()方法将字符串转换为JSON对象
        const courseData = JSON.parse(response.data);
        this.courseInfo = courseData;
        this.showForm = true; // 显示表单组件

      }
      catch (error) {
        console.error("选课信息查询失败", error);
        alert("选课信息查询失败");
      }
    },

    // 查询已选课程
    async fetchCourses() {

      // 构造请求体
      const apiUrl = `${this.host}/api/students/${this.userId}/courses`;

      try {
        // 发送 GET 请求
        const response = await axios.get(apiUrl);
        console.log("选课信息查询成功", response.data);

        // 用JSON.parse()方法将字符串转换为JSON对象
        const courseData = JSON.parse(response.data);
        this.myCourses = courseData;
      }
      catch (error) {
        console.error("选课信息查询失败", error);
        alert("选课信息查询失败");
      }
    },

    // 选课功能
    async selectCourses() {
      try {
        // 创建一个空数组，用于存储请求体数据
        const requestBody = [];

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

        const apiUrl = `${this.host}/api/students/${this.userId}/courses`;
        const response = await axios.post(apiUrl, requestBody);

        const result = JSON.parse(response.data);
        if (result.success) {
          alert("选课成功");
          this.selectedCourses = []; // 清空已选课程
          this.fetchCourses(); // 重新查询课表
        } else {
          alert("选课失败：" + result.message);
        }
      } catch (error) {
        console.error("选课操作失败", error);
        alert("选课操作失败");
      }
    },

    // 退课功能
    async dropCourses() {
      // 发送请求进行退课操作
      try {
        const requestBody = [];
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

        const apiUrl = `${this.host}/api/students/${this.userId}/courses`;
        const response = await axios.delete(apiUrl, requestBody);

        const result = JSON.parse(response.data);
        if (result.success) {
          alert("退课成功");
          this.deletedCourses = []; // 清空已选课程
          this.queryCourses(); // 重新查询课表
        } else {
          alert("退课失败：" + result.message);
        }
      } catch (error) {
        console.error("退课操作失败", error);
        alert("退课操作失败");
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
  