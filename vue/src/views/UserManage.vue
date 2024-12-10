<template>
  <div class="table-container">
    <!-- 搜索表单 -->
    <div class="search-form">
      <div class="search-row">
        <el-input v-model="searchParams.name" placeholder="请输入名称" class="search-input" @input="handleInput" />
        <el-input v-model="searchParams.age" placeholder="请输入年龄" class="search-input" @input="handleInput" />
        <el-input v-model="searchParams.address" placeholder="请输入住址" class="search-input" @input="handleInput" />
        <el-input v-model="searchParams.phone" placeholder="请输入电话" class="search-input" @input="handleInput" />

        <div class="search-actions">
          <el-checkbox v-model="autoSearch" class="auto-search-checkbox">自动搜索</el-checkbox>
          <el-button type="primary" @click="handleSearch" class="search-btn">搜索</el-button>
          <el-button type="primary" @click="clearSearch" class="clear-btn">清空搜索</el-button>
        </div>
      </div>
    </div>

    <!-- 表格 -->
    <el-table :data="tableData" stripe>
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column prop="address" label="住址" />
      <el-table-column prop="phone" label="电话" />
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

    <!-- 空数据提示 -->
    <div v-if="tableData.length === 0" class="no-data">
      <p>没有找到相关数据</p>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";  // 假设你使用的是 axios

export default {
  name: 'UserManage',
  data() {
    return {
      searchParams: {
        name: '',
        age: '',
        address: '',
        phone: '',
      },
      params: {
        pageNum: 1,
        pageSize: 12,
      },
      tableData: [],
      total: 0,
      autoSearch: true,
    };
  },
  methods: {
    load() {
      const searchParams = { ...this.searchParams, ...this.params };
      request.get('/user/page', { params: searchParams })
          .then(res => {
            if (res.code === '200') {
              this.tableData = res.data.list;
              this.total = res.data.total;
            } else {
              this.$message.error('加载数据失败');
            }
          })
          .catch(() => this.$message.error('加载数据失败'));
    },

    handleSearch() {
      this.params.pageNum = 1;  // 每次搜索时重置为第一页
      this.load();
    },

    handlePageChange(page) {
      this.params.pageNum = page;
      this.load();
    },

    handleInput() {
      if (this.autoSearch) {
        this.handleSearch();
      }
    },

    clearSearch() {
      this.searchParams = {
        name: '',
        age: '',
        address: '',
        phone: '',
      };
      this.load();
    },
  },
  created() {
    this.load();  // 初始加载数据
  }
};
</script>

<style scoped>
.table-container {
  padding: 10px;
}

.search-form {
  margin-bottom: 15px;
}

.search-row {
  display: flex;
  justify-content: space-between;  /* 搜索框和按钮占一行 */
  gap: 10px;
  align-items: center;  /* 垂直居中 */
}

.search-input {
  width: 250px;
}

.search-btn, .clear-btn {
  width: 100px;
}

.auto-search-checkbox {
  top: 12px; /* 调整为正数以向下移动 */
  margin-right: 7px;
}


.pagination {
  margin-top: 20px;
  text-align: center;
  display: flex;
  justify-content: center;
}

.no-data {
  text-align: center;
  margin-top: 20px;
  color: #888;
}

.el-button[type="primary"] {
  background-color: #409EFF; /* 确保按钮背景色一致 */
  border-color: #409EFF;
}

.el-button[loading] {
  background-color: #f0f0f0;
}
</style>
