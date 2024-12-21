<template>
  <div class="borrow-book-container">
    <el-form :model="borrowForm" ref="borrowForm" label-width="120px" class="borrow-form">
      <!-- 书籍编号 -->
      <el-form-item label="书籍编号" prop="bookNo" :rules="[{ required: true, message: '请输入书籍编号', trigger: 'blur' }]">
        <el-input v-model="borrowForm.bookNo" @blur="fetchBookDetails" placeholder="请输入书籍编号" />
      </el-form-item>

      <!-- 书籍名称 -->
      <el-form-item label="书籍名称" prop="name">
        <el-input v-model="borrowForm.name" :disabled="true" placeholder="自动填充" />
      </el-form-item>

      <!-- 图书积分 -->
      <el-form-item label="图书积分" prop="score">
        <el-input v-model="borrowForm.score" :disabled="true" type="number" placeholder="自动填充" />
      </el-form-item>

      <!-- 用户账号 -->
      <el-form-item label="用户账号" prop="account" :rules="[{ required: true, message: '请输入用户账号', trigger: 'blur' }]">
        <el-input v-model="borrowForm.account" placeholder="请输入用户账号" />
      </el-form-item>

      <!-- 用户密码 -->
      <el-form-item label="用户密码" prop="password" :rules="[{ required: true, message: '请输入用户密码', trigger: 'blur' }]">
        <el-input v-model="borrowForm.password" @blur="fetchUserDetails" placeholder="请输入用户密码" />
      </el-form-item>

      <!-- 用户电话 -->
      <el-form-item label="用户电话" prop="phone">
        <el-input v-model="borrowForm.phone" :disabled="true" placeholder="自动填充" />
      </el-form-item>

      <!-- 用户名 -->
      <el-form-item label="用户名" prop="username">
        <el-input v-model="borrowForm.username" :disabled="true" placeholder="自动填充" />
      </el-form-item>

      <!-- 提交与重置按钮 -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">借阅</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "BorrowBook",
  data() {
    return {
      borrowForm: {
        name: "",
        bookNo: "",
        account: "",
        password:"",
        phone: "",
        username: "",
        score: null
      }
    };
  },
  methods: {
    // 根据书籍编号获取书籍信息
    async fetchBookDetails() {
      if (!this.borrowForm.bookNo) return;
      try {
        const res = await request.get(`/Book/GetByBookNo/${this.borrowForm.bookNo}`);
        if (res.code === "200") {
          this.borrowForm.name = res.data.name || "";
          this.borrowForm.score = res.data.score || null;
        } else {
          this.borrowForm.name = "";
          this.borrowForm.score = "";
          this.$message.error("未找到该书籍");
        }
      } catch (error) {
        this.borrowForm.name = "";
        this.borrowForm.score = "";
        this.$message.error("未找到该书籍");
      }
    },

    // 根据用户账号获取用户信息
    async fetchUserDetails() {
      if (!this.borrowForm.account) return;

      try {
        const res = await request.post(`Auth/UserProfile`,{ account:this.borrowForm.account,password:this.borrowForm.password });
        if (res.code === "200") {
          this.borrowForm.username = res.data.username || "";
          this.borrowForm.phone = res.data.phone || "";
        } else {
          this.$message.error("未找到该用户");
        }
      } catch (error) {
        this.$message.error("获取用户信息失败");
      }
    },

    // 提交借阅书籍请求
    async handleSubmit() {
      try {
        // 验证表单
        const isValid = await this.$refs.borrowForm.validate();
        if (!isValid) return;

        // 构建借阅请求数据
        const borrowRequest = {
          bookNo: this.borrowForm.bookNo,
          account: this.borrowForm.account
        };

        // 发送借阅请求
        const res = await request.post("/Borrow/Insert", borrowRequest);
        if (res.code === "200") {
          this.$message.success(res.msg);
          this.$refs.borrowForm.resetFields();
        } else {
          this.$message.error(res.msg);
        }
      } catch (error) {
        this.$message.error("借阅失败");
      }
    },

    // 重置表单
    handleReset() {
      this.$refs.borrowForm.resetFields();
    }
  }
};
</script>

<style scoped>
.borrow-book-container {
  width: 500px;  /* 容器宽度设置 */
  margin: 0 auto; /* 居中显示 */
  padding: 20px; /* 内边距 */
}

.borrow-form {
  padding: 20px;
  background-color: #fff;
  border: none;
  box-shadow: none;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-button {
  margin-right: 10px;
}
</style>
