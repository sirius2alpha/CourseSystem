import { createApp } from 'vue'
import App from './App.vue'

// 引入element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

// 引入路由
import router from './router';

// 配置axios
import axios from 'axios';

// 设置axios全局默认值
axios.defaults.withCredentials = true; // 允许跨域请求携带凭证
axios.defaults.timeout = 10000; // 设置超时时间为10秒

// 添加请求拦截器
axios.interceptors.request.use(
  config => {
    // 在发送请求之前做些什么
    return config;
  },
  error => {
    // 对请求错误做些什么
    console.error('请求发送失败:', error);
    return Promise.reject(error);
  }
);

createApp(App).use(router).use(ElementPlus).mount('#app');

