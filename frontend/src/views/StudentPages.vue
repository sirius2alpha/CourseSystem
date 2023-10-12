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
          </div>



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
    };
  },
  methods: {
    selectFunction(functionName) {
      this.selectedFunction = functionName;
    },
    async selectCourses() {
      const id = this.userId;
      const course_id=this.CourseId;
      const course_name=this.CourseName;
      const teacher_id=this.TeacherId;
      const teacher_name=this.TeacherName;

      const apiUrl = `/api/students/${id}/courses`;
      const requestBody = {
        course_id,
        course_name,
        teacher_id,
        teacher_name
      };

      try {
        // 发送 POST 请求
        const response = await axios.post(apiUrl, requestBody);
        console.log("登录成功", response.data);
        // 处理登录成功后的逻辑
        // 跳转到选课页面
        this.$router.push("/course");


      } catch (error) {
        console.error("登录失败", error);
        // 处理登录失败后的逻辑
        // 提示输入密码错误
        alert("登录失败，请检查账号和密码是否正确");

      }
    },
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
</style>
  