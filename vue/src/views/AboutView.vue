<template>
  <div class="table-container">
    <!-- 搜索表单 -->
    <div class="search-form">
      <el-input v-model="searchQuery" placeholder="请输入搜索内容" class="search-input" @input="handleSearch" />
      <el-select v-model="searchField" placeholder="选择搜索方式" class="search-select" @change="handleSearch">
        <el-option label="名称" value="name" />
        <el-option label="年龄" value="age" />
        <el-option label="住址" value="address" />
        <el-option label="电话" value="phone" />
      </el-select>
      <el-button type="primary" @click="resetPage">搜索</el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="pagedData" stripe>
      <el-table-column prop="name" label="名称"></el-table-column>
      <el-table-column prop="age" label="年龄"></el-table-column>
      <el-table-column prop="address" label="住址"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredData.length"
        @current-change="handlePageChange"
        layout="total, prev, pager, next, jumper"
        class="pagination"
    />

    <!-- 空数据提示 -->
    <div v-if="filteredData.length === 0" class="no-data">
      <p>没有找到相关数据</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AboutView',
  data() {
    return {
      searchQuery: '',  // 搜索框的绑定值
      searchField: 'name',  // 默认搜索字段为名称
      currentPage: 1,  // 当前页码
      pageSize: 8,     // 每页显示的条数
      tableData: []    // 表格数据
    }
  },
  methods: {
    load() {
      fetch("http://localhost:8089/user/list")
          .then(res => res.json())
          .then(res => this.tableData = res);
    },
    handleSearch() {
      this.currentPage = 1;  // 每次搜索时重置到第一页
    },
    resetPage() {
      this.handleSearch();
    },
    handlePageChange(page) {
      this.currentPage = page;
    }
  },
  mounted() {
    this.load();
  },
  computed: {
    filteredData() {
      if (!this.searchQuery) return this.tableData;
      return this.tableData.filter(item =>
          item[this.searchField] && item[this.searchField].toString().toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    pagedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      return this.filteredData.slice(start, start + this.pageSize);
    }
  }
}
</script>

<style scoped>
.table-container {
  padding: 10px;
}

.search-form {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.search-input {
  width: 250px;  /* 设置宽度为250px，和之前的样式一致 */
  margin-right: 10px;
}

.search-select {
  width: 150px;
  margin-right: 10px;
}

.el-table {
  margin: 10px 0;
}

.pagination {
  margin-top: 20px;
  text-align: center;
  display: flex;
  justify-content: center;  /* 将分页框居中 */
}

.no-data {
  text-align: center;
  margin-top: 20px;
  color: #888;
}
</style>
