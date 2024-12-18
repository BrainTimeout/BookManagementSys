<template>
  <div class="layout">
    <div class="left-content" :class="{ visible: sidebarVisible }">
      <div class="sidebar-header">
        <img :src="logo" alt="Logo" class="sidebar-logo" />
        <span class="sidebar-title">图书馆管理系统</span>
      </div>
      <el-menu :default-active="$route.path" :default-openeds="['1','2']" router class="el-menu-demo">
        <el-menu-item index="/">
          <i class="mdi mdi-home"></i>
          <span>首页</span>
        </el-menu-item>
        <el-submenu index="1">
          <template #title>
            <i class="mdi mdi-book-open-page-variant"></i>
            <span>书籍管理</span>
          </template>
          <el-menu-item index="/bookCategoryManage">
            <i class="mdi mdi-bookmark-outline"></i>
            <span>书籍分类管理</span>
          </el-menu-item>
        </el-submenu>
        <el-submenu index="2">
          <template #title>
            <i class="mdi mdi-account-settings"></i>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/userManage">
            <i class="mdi mdi-account-edit"></i>
            <span>用户信息管理</span>
          </el-menu-item>
          <el-menu-item index="/accountManage">
            <i class="mdi mdi-account-cog"></i>
            <span>用户账号管理</span>
          </el-menu-item>
          <el-menu-item index="/userJoin">
            <i class="mdi mdi-account-plus"></i>
            <span>用户注册</span>
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </div>
    <div class="right-content" :class="{ shifted: sidebarVisible }">
      <header class="header">
        <button @click="toggleSidebar" class="sidebar-toggle">
          <span :class="sidebarVisible ? 'mdi mdi-menu-open' : 'mdi mdi-menu'"></span>
          <span class="button-text">菜单</span>
        </button>
        <div class="user-info" @click="toggleDropdown">
          <img :src="userlogo" alt="Avatar" class="avatar" />
          <span class="username">{{ userProfile.username }}</span>
          <span :class="dropdownVisible ? 'mdi mdi-menu-down' : 'mdi mdi-menu-up'"></span>
        </div>
      </header>
      <main class="main">
        <router-view/>
        <div v-if="dropdownVisible" class="dropdown">
          <ul>
            <li @click="showUserInfoDialog"><i class="mdi mdi-account-circle"></i> 个人信息</li>
            <li @click="showChangePasswordDialog"><i class="mdi mdi-lock"></i> 修改密码</li>
            <li @click="logout"><i class="mdi mdi-logout"></i> 退出登录</li>
          </ul>
        </div>
      </main>
    </div>
    <!-- 个人信息对话框 -->
    <el-dialog title="个人信息" :visible.sync="userInfoDialogVisible" width="50%">
      <div class="avatar-container">
        <!-- 头像 -->
        <el-upload
            class="avatar-uploader"
            :action="avatarUploadUrl"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
            :on-change="handleAvatarChange"
            :on-success="handleAvatarSuccess"
            :on-error="handleAvatarError"
        >
          <img v-if="userProfile.avatar" :src="userProfile.avatar" class="avatar" />
          <i v-else class="mdi mdi-account-circle"></i>
          <span class="upload-text">点击上传头像</span>
        </el-upload>
      </div>

      <el-form :model="userProfile" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="userProfile.username" :style="{ width: '30%' }" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="userProfile.age" :style="{ width: '20%' }" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="userProfile.sex" :style="{ width: '20%' }">
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="userProfile.address" :style="{ width: '45%' }" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="userProfile.phone" :style="{ width: '45%' }" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="userProfile.email" :style="{ width: '45%' }" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input type="textarea" v-model="userProfile.introduce" :style="{ width: '90%' }" rows="4" />
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="userInfoDialogVisible = false">关闭</el-button>
        <el-button type="primary" @click="saveUserInfo">保存</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改密码" :visible.sync="changePasswordDialogVisible" width="30%">
      <div>
        <el-input v-model="newPassword" placeholder="请输入新密码" type="password"></el-input>
        <el-input v-model="confirmPassword" placeholder="确认新密码" type="password"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
      <el-button @click="changePasswordDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="changePassword">确定</el-button>
    </span>
    </el-dialog>
  </div>

</template>

<script>
export default {
  data() {
    return {
      userProfile: {
        username: '张三',  // 示例数据
        age: '20',
        sex: '男',
        address: '北京市',
        phone: '1234567890',
        email: 'zhangsan@example.com',
        introduce: '这是张三的个人简介',
        avatar: "@/assets/images/captcha.png",
      },
      userLogo: "",
      logo: require("@/assets/images/captcha.png"),
      avatarUploadUrl: 'http://localhost:8089/File/Avatar', // 完整的上传路径
      dropdownVisible: false,
      sidebarVisible: true,
      userInfoDialogVisible: false,
      changePasswordDialogVisible: false,
    };
  },
  methods: {
    toggleSidebar() { this.sidebarVisible = !this.sidebarVisible; },
    toggleDropdown() { this.dropdownVisible = !this.dropdownVisible; },
    showUserInfoDialog() {
      this.userInfoDialogVisible = true;
    },
    // 上传头像前的处理（例如限制文件类型）
    beforeAvatarUpload(file) {
      const isImage = file.type.startsWith('image/');
      if (!isImage) {
        this.$message.error('只能上传图片文件！');
      }
      return isImage;
    },

    // 处理头像上传后的变更
    handleAvatarChange(file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.userProfile.avatar = reader.result;  // 设置上传后的头像路径
      };
      reader.readAsDataURL(file.raw);
    },
    // 上传成功后的回调
    handleAvatarSuccess(response, file, fileList) {
      if (response && response.data && response.data.avatarUrl) {
        this.userProfile.avatar = response.data.avatarUrl;  // 假设服务器返回头像的URL
        this.$message.success('头像上传成功');
      }
    },

    // 上传失败的回调
    handleAvatarError(error, file, fileList) {
      this.$message.error('头像上传失败');
    },
    saveUserInfo() {
      // 保存用户信息的逻辑
      this.$message.success('个人信息已保存');
      this.userInfoDialogVisible = false;
    },
    showChangePasswordDialog() {
      this.changePasswordDialogVisible = true;
    },
    changePassword() {
      if (this.newPassword === this.confirmPassword) {
        // 在此处理密码更改的逻辑
        this.$message.success('密码修改成功');
        this.changePasswordDialogVisible = false;
      } else {
        this.$message.error('两次输入的密码不一致');
      }
    },
    logout() {
      this.$router.push('/login'); // 退出登录，跳转到登录页
    }
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

.avatar-container {
  position: absolute;
  top: 10%;  /* 头像稍微上移，距离顶部10% */
  right: 10%;  /* 头像稍微向左移动，距离左边10% */
  z-index: 10;
}

.avatar-uploader img,
.avatar-uploader .mdi{
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  width: 180px;  /* 增大头像的宽度 */
  height: 180px;  /* 增大头像的高度 */
  border-radius: 50%;
  border: 2px solid #ddd;
  background-color: #f5f5f5;
  position: relative;
}

.dialog-footer {
  text-align: right;
}

.upload-text {
  position: absolute;
  color: #999;
  font-size: 14px;  /* 稍微增大文字的字体 */
  top: 180px;
  right: 60px;
  text-align: center;
}


.right-content {
  width: 100%;
  display: flex;
  flex-direction: column;
  transition: margin-left 0.3s ,width 0.3s;
}

.right-content.shifted {
  margin-left: 15%;
  width: 85%;
}

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
