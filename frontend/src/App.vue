<template>
  <!-- 添加路由组件 -->
  <router-view></router-view>
</template>

<script>
export default {
  name: 'App',
  components: {}
}
</script>

<style>
:root {
  --primary-color: #3498db;
  --secondary-color: #2ecc71;
  --accent-color: #9b59b6;
  --warning-color: #f39c12;
  --danger-color: #e74c3c;
  --text-primary: #2c3e50;
  --text-secondary: #7f8c8d;
  --bg-light: #f8f9fa;
  --bg-white: #ffffff;
  --shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  --radius: 8px;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Inter', 'Helvetica Neue', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: var(--text-primary);
  background-color: var(--bg-light);
  line-height: 1.6;
}

#app {
  min-height: 100vh;
}

.card {
  background: var(--bg-white);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
  padding: 1.5rem;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.btn {
  padding: 0.5rem 1.5rem;
  border-radius: var(--radius);
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  border: none;
  outline: none;
}

.btn-primary {
  background-color: var(--primary-color);
  color: white;
}

.btn-primary:hover {
  background-color: #2980b9;
}

.container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.section {
  padding: 2rem 0;
}

.text-center {
  text-align: center;
}
</style>
