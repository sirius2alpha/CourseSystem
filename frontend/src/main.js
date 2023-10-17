import { createApp } from 'vue'
import App from './App.vue'
import router from './router'; // 确保正确导入 router 对象

createApp(App).use(router).mount('#app');