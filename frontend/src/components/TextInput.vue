<template>
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
      <input type="text" placeholder="请输入您的账号" class="input-component" />
      <input type="password" placeholder="请输入您的密码" class="input-component" />
    </div>
  </div>
</template>

<style scoped>
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