<template>
  <div class="lyear-wrapper" :style="wrapperStyle">
    <div class="lyear-login">
      <div class="login-center">
        <div class="login-header text-center">
          <a class="logo">图书馆管理系统</a>
        </div>
        <el-form @submit.prevent="handleLogin" :model="msg" label-width="80px" class="login-form">
          <el-form-item label="用户名" prop="account">
            <el-input v-model="msg.account" placeholder="请输入用户名">
              <template #prepend><i class="mdi mdi-account"></i></template>
            </el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="msg.password" type="password" placeholder="请输入密码">
              <template #prepend><i class="mdi mdi-lock"></i></template>
            </el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="captcha">
            <el-row type="flex" justify="space-between" align="middle">
              <!-- 验证码输入框 -->
              <el-col :span="16">
                <el-input v-model="msg.captcha" placeholder="请输入验证码"></el-input>
              </el-col>
              <!-- 验证码图片 -->
              <el-col :span="7">
                <img :src="captchaImage" class="captcha-image" @click="refreshCaptcha" title="点击刷新" alt="captcha">
              </el-col>
            </el-row>
          </el-form-item>
          <!-- 角色选择 -->
          <el-form-item label="身份" prop="usertype">
            <el-radio-group v-model="msg.usertype">
              <el-radio label="admin">管理员</el-radio>
              <el-radio label="user">用户</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" class="btn btn-block btn-primary" @click="handleLogin">立即登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  data() {
    return {
      msg: {
        account: '',
        password: '',
        usertype: '',
      },
      captchaImage: require('@/assets/images/captcha.png'), // 引入验证码图片
      logo: require('@/assets/images/logo-sidebar.png') // 引入logo图片
    };
  },
  methods: {
    async handleLogin() {
      try {
        const res = await request.post('/Auth/Login',this.msg);
        if(res.code === "200") {
          this.$notify.success("登录成功");
          if(res.data.usertype === "admin"){
            await this.$router.push({path: '/admin'});
          }else{
            await this.$router.push({path: '/'});
          }
        }
        else{
          this.$notify.error(res.msg);
        }
      }catch {
        this.$message.error(res.msg);
      }
    },
    refreshCaptcha() {
      // 点击刷新验证码
      this.captchaImage = require(`@/assets/images/captcha.png?d=${Math.random()}`);
    }
  },
  computed: {
    wrapperStyle() {
      return {
        backgroundImage: `url(${require('@/assets/images/login-bg-3.jpg')})`, // 引入背景图
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        height: '100vh', // 背景图覆盖整个屏幕高度
      };
    }
  }
};
</script>

<style scoped>
.lyear-wrapper {
  position: relative;
}

.lyear-login {
  display: flex !important;
  min-height: 100vh;
  align-items: center !important;
  justify-content: center !important;
}

.login-header {
  margin-bottom: 1.5rem !important;
}

.logo{
  font-size: 32px; /* 设置字体大小，使文字更大 */
  font-weight: 600; /* 设置字体加粗，使文字更有力量 */
  color: #2c3e50; /* 设置字体颜色为深色，便于阅读 */
  text-decoration: none; /* 去除默认的下划线 */
  letter-spacing: 2px; /* 增加字间距，使文字更分明 */
  padding: 5px 15px; /* 增加内边距，使文字周围留有空间 */
  background-color: transparent; /* 设置背景颜色 */
  border-radius: 8px; /* 添加圆角边框，提升美观度 */
}

/* 如果需要更突出，可以增加阴影效果 */
.logo{
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1); /* 添加文字阴影，增强视觉效果 */
}

.login-center {
  background-color: rgba(255, 255, 255, 0.45);
  min-width: 29.25rem;
  padding: 2.14286em 3.57143em;
  border-radius: 3px;
  margin: 2.85714em;
}

::v-deep .login-center .el-form-item label {
  color: black;
}

.mdi {
  font-size: 15px; /* 设置合适的图标大小 */
  color: #409EFF; /* 设置图标颜色 */
}


.login-center .el-input .el-input__inner {
  background-color: rgba(255, 255, 255, .075);
  border-color: rgba(255, 255, 255, .075);
}

.login-center .el-button {
  width: 100%;
  background-color: #409EFF;
  border-color: #409EFF;
}

/* 让验证码图片适应输入框高度 */
.captcha-image {
  width: 120px;
  height: 40px;
  margin-top: 4px; /* 微调图片与输入框的间距 */
  vertical-align: middle; /* 使图片与输入框垂直居中 */
}

/* 统一按钮的样式 */
.login-center .el-button {
  height: 40px; /* 调整按钮高度 */
  margin-left: -34px; /* 向左移动按钮（根据需要调整值） */
}

</style>
