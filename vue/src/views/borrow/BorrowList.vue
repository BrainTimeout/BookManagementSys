<template>
  <div class="book-container">
    <!-- 搜索框 -->
    <el-row class="filter-row" type="flex" align="middle">
      <el-input
          v-model="filters.name"
          placeholder="图书名称"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.bookNo"
          placeholder="书籍标准码"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.account"
          placeholder="用户账号"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.username"
          placeholder="用户名"
          class="filter-input"
          @input="handleInput"
      />
      <el-date-picker
          v-model="filters.dueDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="借书到期时间"
          style="width: 200px;"
          @change="handleInput"
      />
      <el-switch v-model="autoSearch" @change="handleSearch" />
      <span class="auto-search-text">自动搜索</span>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button type="warning" @click="handleReset">重置</el-button>
    </el-row>

    <!-- 书籍列表表格 -->
    <el-table
        :data="tableData"
        stripe
        :default-sort="{ prop: 'bookNo', order: 'ascending' }"
        @sort-change="handleSortChange"
        row-key="id"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="书籍积分">
              <span>{{ props.row.score || '暂无信息' }}</span>
            </el-form-item>
            <el-form-item label="用户联系方式">
              <span>{{ props.row.phone || '暂无信息' }}</span>
            </el-form-item>
            <el-form-item label="创建时间">
              <span>{{ props.row.createtime || '暂无信息' }}</span>
            </el-form-item>
            <el-form-item label="更新时间">
              <span>{{ props.row.updatetime || '暂无信息' }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <!-- 基本书籍信息列 -->
      <el-table-column prop="id" label="编号" />
      <el-table-column prop="name" label="书名" />
      <el-table-column prop="bookNo" label="书籍编号" />
      <el-table-column prop="account" label="用户账号" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="days" label="借阅天数" />
      <el-table-column prop="dueDate" label="借书到期时间" sortable />
      <!-- 新增状态列 -->
      <el-table-column label="状态">
        <template slot-scope="scope">
          <span :style="getStatusStyle(scope.row.dueDate)">
            {{ getStatusText(scope.row.dueDate) }}
          </span>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        :current-page="params.pageNum"
        :page-size="params.pageSize"
        :total="total"
        @current-change="handlePageChange"
        layout="total, prev, pager, next, jumper"
        class="pagination"
    />
  </div>
</template>


<script>
import request from "@/utils/request"; // 请根据实际请求方式调整

export default {
  name: "BookList",
  data() {
    return {
      tableData: [],  // 存储借阅数据
      total: 0,       // 数据总数
      params: {
        pageNum: 1,
        pageSize: 6,
      },
      filters: {
        account: "",       // 用户账号
        bookNo: "",        // 书籍编号
        username: "",      // 用户名
        name: "",      // 图书名称
        dueDate: "",       // 借书到期时间
        sortOrder: "ascending",  // 排序顺序
      },
      autoSearch: false,  // 控制是否开启自动搜索
    };
  },
  methods: {
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
    // 加载数据
    async load() {
      try {
        const res = await request.get("/Borrow/Page", {
          params: { ...this.params, ...this.filters },
        });
        if (res.code === "200") {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error("加载数据失败");
        }
      } catch (error) {
        this.$message.error("加载数据失败");
      }
    },

    // 处理输入变化并自动搜索
    handleInput() {
      if (this.autoSearch) {
        this.params.pageNum = 1;
        this.load();
      }
    },

    // 手动搜索按钮点击事件
    handleSearch() {
      this.params.pageNum = 1;
      this.load();
    },

    // 重置搜索条件
    handleReset() {
      this.filters = {
        account: "",
        bookNo: "",
        username: "",
        name: "",
        dueDate: "",
        sortOrder: "ascending",
      };
      this.params.pageNum = 1;
      this.load();
    },

    // 处理排序变化
    handleSortChange({ prop, order }) {
      this.filters.sortOrder = order === "ascending" ? "ascending" : "descending";
      this.load();
    },

    // 分页改变
    handlePageChange(page) {
      this.params.pageNum = page;
      this.load();
    },
  },
  created() {
    this.load();  // 初始化加载数据
  },
};
</script>

<style scoped>
.auto-search-text {
  font-size: 14px;
  color: #333;
  margin-right: 10px;
}

.book-container {
  padding: 20px;
}

.filter-row {
  margin-bottom: 20px;
}

.filter-input {
  width: 200px;
  margin-right: 10px;
}

.pagination {
  margin-top: 20px;
}
</style>
