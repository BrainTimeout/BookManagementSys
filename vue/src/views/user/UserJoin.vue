<template>
  <div class="user-join-container">
    <el-form :model="userForm" ref="userForm" label-width="120px" class="user-form">
      <!-- 账号 -->
      <el-form-item label="账号" prop="account" :rules="[{ required: true, message: '请输入账号', trigger: 'blur' }]">
        <el-input v-model="userForm.account" placeholder="请输入账号"></el-input>
      </el-form-item>

      <!-- 用户名 -->
      <el-form-item label="用户名" prop="username" :rules="[{ required: true, message: '请输入用户名', trigger: 'blur' }]">
        <el-input v-model="userForm.username" placeholder="请输入用户名"></el-input>
      </el-form-item>

      <!-- 密码 -->
      <el-form-item label="密码" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
        <el-input v-model="userForm.password" type="password" placeholder="请输入密码"></el-input>
      </el-form-item>

      <!-- 确认密码 -->
      <el-form-item label="确认密码" prop="confirmPassword" :rules="[
        { required: true, message: '请确认密码', trigger: 'blur' },
        { validator: validateConfirmPassword, trigger: 'blur' }
      ]">
        <el-input v-model="userForm.confirmPassword" type="password" placeholder="请再次输入密码"></el-input>
      </el-form-item>

      <!-- 账户类型 -->
      <el-form-item label="账户类型" prop="accountType" :rules="[{ required: true, message: '请选择账户类型', trigger: 'change' }]">
        <el-select v-model="userForm.accountType" placeholder="请选择账户类型">
          <el-option label="管理员" value="admin"></el-option>
          <el-option label="用户" value="user"></el-option>
        </el-select>
      </el-form-item>

      <!-- 年龄 -->
      <el-form-item label="年龄" prop="age">
        <el-input v-model="userForm.age" type="number" placeholder="请输入年龄"></el-input>
      </el-form-item>

      <!-- 性别 -->
      <el-form-item label="性别" prop="gender">
        <el-select v-model="userForm.gender" placeholder="请选择性别">
          <el-option label="男" value="male"></el-option>
          <el-option label="女" value="female"></el-option>
        </el-select>
      </el-form-item>

      <!-- 地址 -->
      <el-form-item label="地址" prop="address">
        <el-input v-model="userForm.address" placeholder="请输入地址"></el-input>
      </el-form-item>

      <!-- 电话 -->
      <el-form-item label="电话" prop="phone">
        <el-input v-model="userForm.phone" placeholder="请输入电话"></el-input>
      </el-form-item>

      <!-- 邮箱 -->
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userForm.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>

      <!-- 简介 -->
      <el-form-item label="简介" prop="introduce">
        <el-input v-model="userForm.introduce" type="textarea" placeholder="请输入简介"></el-input>
      </el-form-item>

      <!-- 提交与重置按钮 -->
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">提交</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UserJoin",
  data() {
    return {
      userForm: {
        account: "",
        username: "",
        password: "",
        confirmPassword: "",
        accountType: "", // admin 或 user
        age: null,
        gender: "",
        address: "",
        phone: "",
        email: "",
        introduce: ""
      }
    };
  },
  methods: {
    // 二次密码确认的验证方法
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.userForm.password) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    },

    async handleSubmit() {
      try {
        // 验证表单
        const isValid = await this.$refs.userForm.validate();
        if (!isValid) return;

        // 提交数据
        const res = await request.post("/user/UserJoin", this.userForm);
        if (res.code === "200") {
          this.$message.success(res.msg);
          this.$router.push({ name: "UserList" });
        } else {
          this.$message.error(res.msg);
        }
      } catch (error) {
        this.$message.error(res.msg);
      }
    },
    handleReset() {
      this.$refs.userForm.resetFields();
    }
  }
};
</script>

<style scoped>
.user-join-container {
  width: 500px;
  margin: 0 auto;
}

.user-form {
  padding: 20px;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-button {
  margin-right: 10px;
}
</style>
