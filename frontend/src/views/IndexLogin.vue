<template>
  <div class="login-page">
    <div class="login-container">
      <div class="login-image-container">
        <div class="login-image-overlay"></div>
        <el-image class="login-image" :src="'/cover.png'" fit="cover" />
        <div class="login-image-text">
          <h2>智慧校园</h2>
          <p>让学习与管理更高效</p>
        </div>
      </div>
      
      <div class="login-form-container">
        <div class="login-form-header">
          <div class="window-controls">
            <span class="control red"></span>
            <span class="control yellow"></span>
            <span class="control green"></span>
          </div>
          <h1>智慧教务系统</h1>
        </div>
        
        <div class="login-form">
          <div class="form-group">
            <label for="userId">学号/工号</label>
            <el-input 
              v-model="userId" 
              placeholder="请输入您的学号或工号"
              prefix-icon="el-icon-user"
              class="custom-input"
            />
          </div>
          
          <div class="form-group">
            <label for="password">密码</label>
            <el-input 
              v-model="password" 
              type="password"
              placeholder="请输入您的密码" 
              prefix-icon="el-icon-lock"
              show-password
              class="custom-input"
            />
          </div>
          
          <el-button 
            type="primary" 
            class="login-button" 
            @click="login"
            :loading="loading"
          >
            登录系统
          </el-button>
          
          <div class="login-footer">
            <p>© {{ new Date().getFullYear() }} 智慧教务系统</p>
            <p>技术支持: 校园信息中心</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import md5 from "md5";
import { ElMessage } from 'element-plus';

export default {
  name: "IndexLogin",
  components: {},
  data() {
    return {
      userId: "",
      password: "123456",
      userName: "default",
      host: "http://127.0.0.1:9000",
      loading: false
    };
  },
  methods: {
    async login() {
      if (!this.userId || !this.password) {
        ElMessage.warning("请输入账号和密码");
        return;
      }
      
      this.loading = true;
      const id = this.userId;
      const password = this.password;
      const hashedPassword = md5(id + password);

      try {
        const apiUrl = `${this.host}/api/users/${id}/pwd`;
        const requestBody = { id, password: hashedPassword };
        const response = await axios.post(apiUrl, requestBody);

        if (response.data.code !== 200) {
          ElMessage.error("登录失败，请检查账号和密码是否正确");
          this.loading = false;
          return;
        }

        ElMessage.success("登录成功，正在跳转...");
        
        setTimeout(() => {
          if (response.data.data.roleId === 1) {
            this.$router.push({ 
              name: 'students', 
              params: { 
                userId: id, 
                userName: response.data.data.userName 
              } 
            });
          } else {
            this.$router.push({ 
              name: 'teachers', 
              params: { 
                userId: id, 
                userName: response.data.data.userName 
              } 
            });
          }
          this.loading = false;
        }, 1000);
      } catch (error) {
        console.error("登录失败", error);
        ElMessage.error("网络错误，请稍后再试");
        this.loading = false;
      }
    }
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f5f7fa;
  padding: 2rem;
}

.login-container {
  display: flex;
  width: 1000px;
  max-width: 100%;
  height: 600px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
}

.login-image-container {
  flex: 1;
  position: relative;
  overflow: hidden;
}

.login-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.login-image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, rgba(52, 152, 219, 0.8), rgba(46, 204, 113, 0.8));
  z-index: 1;
}

.login-image-text {
  position: absolute;
  bottom: 40px;
  left: 40px;
  color: white;
  z-index: 2;
}

.login-image-text h2 {
  font-size: 2.5rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.login-image-text p {
  font-size: 1.2rem;
  opacity: 0.9;
}

.login-form-container {
  flex: 1;
  background-color: white;
  padding: 3rem;
  display: flex;
  flex-direction: column;
}

.login-form-header {
  margin-bottom: 2.5rem;
}

.window-controls {
  display: flex;
  gap: 8px;
  margin-bottom: 2rem;
}

.control {
  width: 16px;
  height: 16px;
  border-radius: 50%;
}

.control.red {
  background-color: #ff5f56;
}

.control.yellow {
  background-color: #ffbd2e;
}

.control.green {
  background-color: #27c93f;
}

.login-form-header h1 {
  font-size: 1.8rem;
  font-weight: 700;
  color: #333;
  margin-top: 1rem;
}

.login-form {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #555;
}

.custom-input {
  width: 100%;
}

.login-button {
  margin-top: 1.5rem;
  height: 48px;
  font-size: 1rem;
  font-weight: 600;
  background: linear-gradient(to right, #3498db, #2ecc71);
  border: none;
  transition: all 0.3s ease;
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(46, 204, 113, 0.3);
}

.login-footer {
  margin-top: auto;
  text-align: center;
  color: #999;
  font-size: 0.85rem;
}

.login-footer p {
  margin: 0.3rem 0;
}

@media (max-width: 768px) {
  .login-container {
    flex-direction: column;
    height: auto;
  }
  
  .login-image-container {
    height: 200px;
  }
}
</style>
