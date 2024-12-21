<template>
  <div class="book-container">
    <!-- 搜索框 -->
    <el-row class="filter-row" type="flex" align="middle">
      <el-input
          v-model="filters.name"
          placeholder="书名"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.author"
          placeholder="作者"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.publisher"
          placeholder="出版社"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.category"
          placeholder="分类"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.detail"
          placeholder="其它信息"
          class="filter-input"
          @input="handleInput"
      />
      <el-switch v-model="autoSearch" @change="handleSearch" />
      <span class="auto-search-text">自动搜索</span>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
      <el-button type="warning" @click="handleReset">重置</el-button>
    </el-row>

    <!-- 书籍卡片展示 -->
    <div class="book-card-container">
      <el-card
          v-for="book in tableData"
          :key="book.id"
          class="book-card"
      >
        <div class="card-header" @click="navigateToDetail(book.id)">
          <img :src="book.cover || defaultCover" alt="封面" class="book-cover" />
          <h3 class="book-title">{{ book.name }}</h3>
        </div>
      </el-card>
    </div>

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
import request from "@/utils/request";

export default {
  name: "bookList",
  data() {
    return {
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 6,
      },
      filters: {
        name: "",
        author: "",
        publisher: "",
        category: "",
        detail: "",
        sortOrder: "ascending",
      },
      autoSearch: false,
    };
  },
  methods: {
    navigateToDetail(id) {
      console.log("尼玛");
      this.$router.push({ name: "bookDetail", params: { id } });
    },
    async load() {
      try {
        const res = await request.get("/User/Page", {
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
    handleInput() {
      if (this.autoSearch) {
        this.params.pageNum = 1;
        this.load();
      }
    },
    handleSearch() {
      this.params.pageNum = 1;
      this.load();
    },
    handleReset() {
      this.filters = {
        name: "",
        author: "",
        publisher: "",
        category: "",
        detail: "",
      };
      this.params.pageNum = 1;
      this.load();
    },
    handleSortChange({ prop, order }) {
      this.filters.sortOrder = order === "ascending" ? "ascending" : "descending";
      this.load();
    },
    handlePageChange(page) {
      this.params.pageNum = page;
      this.load();
    },
  },
  created() {
    this.load();
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

.book-card-container {
  display: flex;
  flex-wrap: wrap;
  gap: 0; /* 卡片间距 */
}


.book-card {
  width: 240px; /* 保持卡片宽度为 240px */
  border: none; /* 确保边框完全移除 */
  border-radius: 0; /* 确保没有圆角 */
  overflow: hidden;
  cursor: pointer;
  box-shadow: none; /* 移除任何阴影 */
  margin: 0; /* 确保卡片之间没有额外的间距 */
  padding: 0; /* 移除内部填充 */
  background-color: transparent; /* 确保背景透明，无色 */
}

.card-header {
  text-align: center;
  background-color: #f5f7fa;
  padding: 10px;
}

.book-cover {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.card-body {
  padding: 10px;
  border-top: none; /* 确保顶部边框移除 */
}

.book-title {
  font-size: 14px;
  font-weight: bold;
  margin-top: 10px;
  text-align: center;
}

.card-footer {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  font-size: 12px;
}
</style>
