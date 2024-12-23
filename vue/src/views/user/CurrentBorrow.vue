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
      <el-table-column prop="dueDate" label="归还日期" :formatter="formatDate"></el-table-column>
      <el-table-column prop="days" label="借阅天数"></el-table-column>
      <el-table-column label="状态">
        <template slot-scope="scope">
          <span :style="getStatusStyle(scope.row.dueDate)">
            {{ getStatusText(scope.row.dueDate) }}
          </span>
        </template>
      </el-table-column>
      <!-- 计算积分 -->
      <el-table-column label="积分" width="100">
        <template slot-scope="scope">
          {{ calculateScore(scope.row) }}
        </template>
      </el-table-column>
      <el-table-column prop="createtime" label="创建时间" :formatter="formatDate"></el-table-column>
      <el-table-column prop="updatetime" label="更新时间" :formatter="formatDate"></el-table-column>
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

    // 计算借阅天数
    calculateDays(row) {
      const borrowDate = new Date(row.borrowDate); // 假设借阅日期存储在 borrowDate 字段
      const dueDate = new Date(row.dueDate);
      const diffTime = dueDate - borrowDate;
      const diffDays = diffTime / (1000 * 3600 * 24); // 将时间差转换为天数
      return diffDays > 0 ? diffDays : 0; // 如果借阅日期比归还日期晚，显示 0
    },

    // 获取借书状态
    getStatusText(dueDate) {
      const currentDate = new Date();
      const due = new Date(dueDate);
      const timeDifference = due - currentDate;

      if (timeDifference < 0) {
        return "逾期";  // 如果到期时间在当前时间之前，则逾期
      } else if (timeDifference <= 3 * 24 * 60 * 60 * 1000) {
        return "即将到期";  // 如果到期时间在未来3天内，则显示快到期
      } else {
        return "未到期";  // 否则，表示未到期
      }
    },

    // 获取状态的样式
    getStatusStyle(dueDate) {
      const currentDate = new Date();
      const due = new Date(dueDate);
      const timeDifference = due - currentDate;

      if (timeDifference < 0) {
        return "color: red;";  // 逾期时使用红色
      } else if (timeDifference <= 3 * 24 * 60 * 60 * 1000) {
        return "color: orange;";  // 快到期时使用橙色
      } else {
        return "color: green;";  // 未到期时使用绿色
      }
    },

    // 计算积分，假设每借阅一天积1分
    calculateScore(row) {
      const score = row.score || 0;  // 假设 `score` 是借阅记录中的属性
      const days = row.days || 0;    // 假设 `days` 是借阅记录中的属性（表示借书天数）
      return score * days;  // 积分 = score * days
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
