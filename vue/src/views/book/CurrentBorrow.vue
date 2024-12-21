<template>
  <div class="borrowed-books">
    <h1>借阅记录</h1>
    <el-table :data="borrowedBooks" border style="width: 100%">
      <el-table-column prop="id" label="记录ID" width="80"></el-table-column>
      <el-table-column prop="name" label="书名"></el-table-column>
      <el-table-column prop="bookNo" label="书籍编号"></el-table-column>
      <el-table-column prop="username" label="借阅人"></el-table-column>
      <el-table-column prop="account" label="账号"></el-table-column>
      <el-table-column prop="phone" label="联系电话"></el-table-column>
      <el-table-column
          prop="dueDate"
          label="归还日期"
          :formatter="formatDate"
      ></el-table-column>
      <el-table-column prop="score" label="积分" width="100"></el-table-column>
      <el-table-column
          prop="createtime"
          label="创建时间"
          :formatter="formatDate"
      ></el-table-column>
      <el-table-column
          prop="updatetime"
          label="更新时间"
          :formatter="formatDate"
      ></el-table-column>
    </el-table>
    <div v-if="borrowedBooks.length === 0" class="no-data">
      <p>暂无借阅记录。</p>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "currentBorrow",
  props: ["account"],
  data() {
    return {
      borrowedBooks: [], // 存储借阅记录
    };
  },
  methods: {
    // 获取借阅记录
    async fetchBorrowedBooks() {
      try {
        const res = await request.get(`/User/ListByAccount/${this.account}`);
        if (res.code === "200") {
          this.borrowedBooks = res.data;
        } else {
          this.$message.error("加载借阅记录失败");
        }
      } catch {
        this.$message.error("加载借阅记录失败");
      }
    },
    // 格式化日期
    formatDate(row, column, cellValue) {
      if (!cellValue) return "未归还";
      const options = { year: "numeric", month: "2-digit", day: "2-digit" };
      return new Date(cellValue).toLocaleDateString("zh-CN", options);
    },
  },
  created() {
    this.fetchBorrowedBooks();
  },
};
</script>

<style scoped>
.borrowed-books {
  padding: 20px;
}
.no-data {
  margin-top: 20px;
  font-size: 16px;
  color: #666;
  text-align: center;
}
</style>
