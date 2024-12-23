<template>
  <div class="revert-book-container">
    <el-form :model="revertForm" ref="revertForm" label-width="120px" class="revert-form">

      <!-- 用户账号 -->
      <el-form-item label="用户账号" prop="account" :rules="[{ required: true, message: '请输入用户账号', trigger: 'blur' }]">
        <el-input v-model="revertForm.account" @blur="fetchUserDetails" placeholder="请输入用户账号" />
      </el-form-item>

      <!-- 用户名 -->
      <el-form-item label="用户名" prop="username">
        <el-input v-model="revertForm.username" :disabled="true" placeholder="自动填充" />
      </el-form-item>

      <!-- 用户电话 -->
      <el-form-item label="用户电话" prop="phone">
        <el-input v-model="revertForm.phone" :disabled="true" placeholder="自动填充" />
      </el-form-item>

      <!-- 用户名 -->
      <el-form-item label="用户余额" prop="username">
        <el-input v-model="revertForm.balance" :disabled="true" placeholder="自动填充" />
      </el-form-item>

      <!-- 书籍编号 -->
      <el-form-item label="书籍编号" prop="bookNo" :rules="[{ required: true, message: '请输入书籍编号', trigger: 'blur' }]">
        <el-input v-model="revertForm.bookNo" @blur="fetchBookDetails" placeholder="请输入书籍编号" />
      </el-form-item>

      <!-- 书籍名称 -->
      <el-form-item label="书籍名称" prop="name">
        <el-input v-model="revertForm.name" :disabled="true" placeholder="自动填充" />
      </el-form-item>

      <!-- 图书积分 -->
      <el-form-item label="当前数量" prop="score">
        <el-input v-model="revertForm.nums" :disabled="true" type="number" placeholder="自动填充" />
      </el-form-item>

      <!-- 提交与重置按钮 -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">归还</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "revertBook",
  data() {
    return {
      revertForm: {
        name: "",
        bookNo: "",
        days: 1,
        score: null,
        nums:"",
        account: "",
        balance:"",
        password:"",
        phone: "",
        username: ""
      }
    };
  },
  computed: {
    // 计算所需积分
    calculatedScore() {
      return this.revertForm.score * this.revertForm.days;
    }
  },
  methods: {
    // 根据用户账号获取用户信息
    async fetchUserDetails() {
      if (!this.revertForm.account){
        return;
      }
      try {
        const res = await request.get(`User/GetAuthInfo/${this.revertForm.account}`);
        if (res.code === "200") {
          this.revertForm.username = res.data.userProfile.username || "";
          this.revertForm.balance = res.data.balance || "";
          this.revertForm.phone = res.data.userProfile.phone || "";
        } else {
          this.$message.error("未找到该用户");
        }
      } catch (error) {
        this.$message.error("获取用户信息失败");
      }
    },
    // 根据书籍编号获取书籍信息
    async fetchBookDetails() {
      if (!this.revertForm.bookNo) {
        return;
      }
      try {
        const res = await request.get(`/Book/GetByBookNo/${this.revertForm.bookNo}`);
        if (res.code === "200") {
          this.revertForm.name = res.data.name || "";
          this.revertForm.score = res.data.score || null;
          this.revertForm.nums = res.data.nums || 0;
        } else {
          this.revertForm.name = "";
          this.revertForm.score = "";
          this.$message.error("未找到该书籍");
        }
      } catch (error) {
        this.revertForm.name = "";
        this.revertForm.score = "";
        this.$message.error("未找到该书籍");
      }
    },
    // 提交归还书籍请求
    async handleSubmit() {
      try {
        // 验证表单
        const isValid = await this.$refs.revertForm.validate();
        if (!isValid) return;

        // 构建还书请求数据
        const revertRequest = {
          bookNo: this.revertForm.bookNo,
          account: this.revertForm.account
        };

        // 发送归还请求
        const res = await request.put("/Borrow/RevertBook", revertRequest);
        if (res.code === "200") {
          this.$message.success(res.msg);
          this.$refs.revertForm.resetFields();
        } else {
          this.$message.error(res.msg);
        }
      } catch (error) {
        this.$message.error("归还失败");
      }
    },

    // 重置表单
    handleReset() {
      this.$refs.revertForm.resetFields();
    }
  }
};
</script>

<style scoped>
.revert-book-container {
  width: 500px;  /* 容器宽度设置 */
  margin: 0 auto; /* 居中显示 */
  padding: 20px; /* 内边距 */
}

.revert-form {
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
