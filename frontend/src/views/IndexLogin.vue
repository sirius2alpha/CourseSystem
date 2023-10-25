<template>
  <div>
    <h1>聪明教务管理系统</h1>
    <div class="content">
      <div class="container">
        <div class="circle-icons">
          <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 21 21" fill="none">
            <circle cx="10.5" cy="10.5" r="10.5" fill="#F34B4B" />
          </svg>
          <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 21 21" fill="none">
            <circle cx="10.5" cy="10.5" r="10.5" fill="#FFC700" />
          </svg>
          <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 21 21" fill="none">
            <circle cx="10.5" cy="10.5" r="10.5" fill="#1097F9" />
          </svg>
        </div>
        <div class="input-fields">
          <input type="text" placeholder="请输入您的账号" class="input-component" v-model="userId" />
          <input type="password" placeholder="请输入您的密码" class="input-component" v-model="password" />
        </div>
      </div>
    </div>
    <!-- bind the method "login" on the input -->
    <input type="button" value="登录" @click="login">
    <input type="button" value="学生页面" @click="jumpStudents">
    <input type="button" value="老师页面" @click="jumpTeachers">

  </div>
</template>

<script>
import axios from "axios";
import md5 from "md5";
import { ElMessage } from 'element-plus'


export default {
  // define a component
  name: "IndexLogin",
  components: {

  },

  // data of the component
  data() {
    return {
      userId: "231295",
      password: "123456",
      host:"http://127.0.0.1:9000",
    };
  },

  // methods of the component
  methods: {
    async login() {
      const id = this.userId;
      const password = this.password;

      // 使用 md5 对密码进行摘要处理
      const hashedPassword = md5(id + password);

      const apiUrl = `${this.host}/api/users/${id}/pwd`;
      const requestBody = {
        id,
        password: hashedPassword,
      };

      try {
        // 发送 POST 请求
        const response = await axios.post(apiUrl, requestBody);
        if(response.data.code !== 200){
          ElMessage.error("登录失败，请检查账号和密码是否正确");
          return;
        }
        ElMessage.success("登录成功");
        console.log("登录成功", response.data.data.roleId);
        // 处理登录成功后的逻辑
        if(response.data.data.roleId === 1){
          this.$router.push('students');
        }
        else{
          this.$router.push('teachers');
        }
      } 
      catch (error) {
        console.error("登录失败", error);
        // 处理登录失败后的逻辑
        // 提示输入密码错误
        ElMessage.error("登录失败，请检查账号和密码是否正确");

      }
    },
    
    jumpStudents() {
      this.$router.push('students');
    },

    jumpTeachers(){
      this.$router.push('teachers');
    }
  },
};
</script>

<style scoped>
.content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* 垂直居中 */
  align-items: center;
  /* 水平居中 */
  background: #fff;
  padding: 20px;
  border-radius: 10px;
}

.container {
  width: 410px;
  display: flex;
  flex-direction: column;
  align-items: left;
  justify-content: center;
  gap: 10px;
  border-radius: 20px;
}

.input-fields {
  display: flex;
  flex-direction: column;
  left: 20px;
  top: 20px;
}

.input-component {
  width: 380px;
  height: 38px;
  border-radius: 20px;
  background: #c8def1;
  margin: 10px;
  padding-left: 15px;
}

.circle-icons {
  display: flex;
  gap: 10px;
  align-items: center;
  justify-content: center;
  margin: 20px;
  margin-left: 15px;
  margin-bottom: 5px;
}
</style>
