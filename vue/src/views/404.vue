<template>
  <div class="not-found-container">
    <el-row type="flex" justify="center" align="middle" class="not-found">
      <el-col :span="18" sm="16" md="12" lg="10">
        <div class="not-found-content">
          <h1 class="not-found-title">404 - 页面未找到</h1>
          <p class="not-found-description">抱歉，您访问的页面不存在或已被删除。</p>
          <el-button type="primary" @click="goLogin" size="large">返回登录界面</el-button>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'NotFound',
  methods: {
    // 返回首页
    goLogin() {
      this.$router.push('/login');
    },
  },
};
</script>

<style scoped>
.not-found-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f9f9f9;
}

.not-found {
  text-align: center;
}

.not-found-content {
  padding: 40px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
}

.not-found-title {
  font-size: 48px;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

.not-found-description {
  font-size: 18px;
  color: #666;
  margin-bottom: 30px;
}

.el-button {
  font-size: 18px;
  padding: 12px 30px;
}
</style>
