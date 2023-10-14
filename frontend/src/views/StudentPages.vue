<template>
  <div>
    <header class="top-bar">
      <div class="top-bar-content">
        <span>聪明选课系统</span>
        <span class="user-name">{{ userName }}</span>
      </div>
    </header>

    <div class="main-content">
      <aside class="sidebar">
        <ul>
          <li @click="selectFunction('选课')">选课</li>
          <li @click="selectFunction('退课')">退课</li>
          <li @click="selectFunction('成绩查询')">成绩查询</li>
          <li @click="selectFunction('课表查询')">课表查询</li>
        </ul>
      </aside>

      <div class="main-content-right">

        <!-- 选课功能内容 -->
        <div v-if="selectedFunction === '选课'">
          <!-- 选课功能内容 -->

          <!-- 包括输入课程号、教师号、教师姓名的表单 -->
          <div class="input-row">
            <div class="input-group">
              <label for="CourseId">课程号</label>
              <input type="number" id="CourseId" v-model="CourseId">
            </div>

            <div class="input-group">
              <label for="CourseName">课程名</label>
              <input type="text" id="CourseName" v-model="CourseName">
            </div>

            <div class="input-group">
              <label for="TeacherId">教师号</label>
              <input type="number" id="TeacherId" v-model="TeacherId">
            </div>

            <div class="input-group">
              <label for="TeacherName">教师姓名</label>
              <input type="text" id="TeacherName" v-model="TeacherName">
            </div>

            <div class="input-group">
              <label for="CourseTime">上课时间</label>
              <input type="text" id="CourseTime" v-model="CourseTime">
            </div>

            <!--提交按钮-->
            <input type="button" value="提交" @click="fetchCourses">


          </div>

          <!--选课记录返回，用列表进行呈现-->

          <!--展示查询到的选课信息-->
          <form v-if="showForm">
            <table class="course-table">
              <tr>
                <th>课程号</th>
                <th>课程名</th>
                <th>教师号</th>
                <th>教师姓名</th>
                <th>课程容量</th>
                <th>已选人数</th>
                <th>上课时间</th>
              </tr>
              <tr v-for="course in courseInfo" :key="course.course_id">
                <td>{{ course.course_id }}</td>
                <td>{{ course.course_name }}</td>
                <td>{{ course.teacher_id }}</td>
                <td>{{ course.teacher_name }}</td>
                <td>{{ course.capacity }}</td>
                <td>{{ course.selected_number }}</td>
                <td>{{ course.time }}</td>
              </tr>
            </table>
          </form>


        </div>

        <div v-else-if="selectedFunction === '退课'">
          <!-- 退课功能内容 -->
          <!--先把课表查出来，前面再加一个多选框，直接选择然后点击退课就行-->
        </div>

        <div v-else-if="selectedFunction === '成绩查询'">
          <!-- 成绩查询功能内容 -->
        </div>

        <div v-else-if="selectedFunction === '课表查询'">
          <!-- 课表查询功能内容 -->
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
import axios from "axios";

export default {
  name: "StudentPages",
  data() {
    return {
      userName: "用户名称", // 用户名，你可以从登录信息中获取
      selectedFunction: "选课", // 默认选中的功能
      showForm: false, // 选课信息的表单点击选课查询之后才会显示

      courseInfo: [{
        "course_id": "course_id",
        "course_name": "course_name",
        "teacher_id": "teacher_id",
        "teacher_name": "teacher_name",
        "capacity": 0,
        "selected_number": 0,
        "time": "time"
      }]
    };
  },
  methods: {
    // 选择功能
    selectFunction(functionName) {
      this.selectedFunction = functionName;
    },

    // 选课功能
    async fetchCourses() {
      // 把v-model数据保存到变量中
      const id = this.userId;
      const course_id = this.CourseId;
      const course_name = this.CourseName;
      const teacher_id = this.TeacherId;
      const teacher_name = this.TeacherName;
      const course_time = this.CourseTime;

      // 构造请求体
      const apiUrl = `/api/students/${id}/courses`;
      const requestBody = {
        course_id,
        course_name,
        teacher_id,
        teacher_name,
        course_time
      };

      try {
        // 发送 POST 请求
        const response = await axios.post(apiUrl, requestBody);
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
    mounted() {
      this.fetchCourses();
      this.showForm = false; // 隐藏表单组件
    }
  },
};
</script>
  
<style scoped>
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
  border-collapse: collapse;
  font-family: Arial, sans-serif;
  background-color: #f2f2f2;
  width: 100%;
}

.course-table th, .course-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.course-table th {
  background-color: #8ac9e2;
  color: white;
}
</style>
  