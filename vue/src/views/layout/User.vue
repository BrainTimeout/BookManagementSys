<template>
  <div class="layout">
    <div class="left-content" :class="{ visible: sidebarVisible }">
      <div class="sidebar-header">
        <img :src="logo" alt="Logo" class="sidebar-logo" />
        <span class="sidebar-title">图书馆管理系统</span>
      </div>
      <--- 菜单栏内容 --->
      <el-menu :default-active="$route.path" router class="el-menu-demo">
        <el-menu-item index="/">首页</el-menu-item>
        <el-submenu-item index="2">
          <template slot="title">关于页面</template>
          <el-menu-item index="/about">dfasd</el-menu-item>
        </el-submenu-item>

      </el-menu>
    </div>
    <div class="right-content" :class="{ shifted: sidebarVisible }">
      <header class="header">
        <button @click="toggleSidebar" class="sidebar-toggle">
          <span :class="sidebarVisible ? 'mdi mdi-menu-open' : 'mdi mdi-menu'" />
          <span class="button-text">菜单</span>
        </button>
        <div class="user-info" @click="toggleDropdown">
          <img :src="user.avatar" alt="Avatar" class="avatar" />
          <span class="username">{{ user.username }}</span>
          <span :class="dropdownVisible ? 'mdi mdi-menu-down-outline' : 'mdi mdi-menu-right-outline'"></span>
        </div>
      </header>
      <main class="main">
        <router-view/>
        <div v-if="dropdownVisible" class="dropdown">
          <ul>
            <li>个人信息</li>
            <li>修改密码</li>
            <li>退出登录</li>
          </ul>
        </div>
      </main>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: { username: '张三', avatar: require("@/assets/images/captcha.png") },
      logo: require("@/assets/images/captcha.png"),
      dropdownVisible: false,
      sidebarVisible: true,
    };
  },
  methods: {
    toggleSidebar() { this.sidebarVisible = !this.sidebarVisible; },
    toggleDropdown() { this.dropdownVisible = !this.dropdownVisible; }
  }
};
</script>

<style scoped>
.layout {
  display: flex;
  height: 100vh;
}

.left-content {
  width: 15%;
  position: fixed;
  top: 0;
  left: 0;
  background-color: #f4f4f4;
  height: 100%;
  transform: translateX(-100%);
  transition: transform 0.3s;
}

.left-content.visible { transform: translateX(0); }

.sidebar-header, .header {
  display: flex;
  align-items: center;
  padding: 0 20px;
}

.sidebar-header { height: 10%; border-bottom: 1px solid #ddd; }

.sidebar-logo {
  width: 30px;
  height: 30px;
  border-radius: 50%;
  margin-right: 10px;
}

.sidebar-title { font-size: 18px; font-weight: bold; color: #333; }

.right-content {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  transition: margin-left 0.3s;
}

.right-content.shifted { margin-left: 15%; }

.header {
  background-color: #e0e0e0;
  justify-content: flex-start;
  height: 10%;
}

.user-info {
  display: flex;
  align-items: center;
  margin-left: auto;
  cursor: pointer;
}

.avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 15px;
}

.username { margin-right: 10px; font-size: 18px; color: #333; }

.mdi { font-size: 24px; }

.sidebar-toggle {
  display: flex;
  align-items: center;
  background-color: transparent;
  border: none;
  font-size: 16px;
  cursor: pointer;
}

.sidebar-toggle .mdi { margin-right: 8px; font-size: 24px; }

.dropdown {
  position: absolute;
  right: 10px;
  top: 10%;
  background-color: #fff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 150px;
  z-index: 10;
}

.dropdown ul {
  list-style: none;
  padding: 10px 0;
  margin: 0;
}

.dropdown li {
  padding: 10px;
  font-size: 17px;
  color: #333;
  cursor: pointer;
}

.dropdown li:hover { background-color: #f0f0f0; }

.main { flex-grow: 1; background-color: #fff; }
</style>
