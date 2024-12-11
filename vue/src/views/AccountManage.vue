<template>
  <div class="table-container">
    <!-- 筛选条件 -->
    <el-row class="filter-row" type="flex" align="middle">
      <el-input
          v-model="filters.account"
          placeholder="账户"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.type"
          placeholder="用户类型"
          class="filter-input"
          @input="handleInput"
      />
    </el-row>

    <!-- 按钮组 -->
    <el-row class="button-row">
      <el-switch v-model="autoSearch" @change="handleSearch" />
      <span class="auto-search-text">自动搜索</span>
      <div class="search-buttons">
        <el-button type="primary" @click="handleSearch" class="btn-spacing">搜索</el-button>
        <el-button type="warning" @click="handleReset" class="btn-spacing">重置</el-button>
      </div>
    </el-row>

    <!-- 表格 -->
    <el-table :data="tableData" stripe @row-click="handleRowClick">
      <el-table-column prop="account" label="账户" />
      <el-table-column prop="password" label="密码" />
      <el-table-column prop="usertype" label="用户类型" />
      <el-table-column label="更多">
        <template v-slot="scope">
          <el-dropdown trigger="click">
            <span class="el-dropdown-link" @click.stop>更多</span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <strong>用户类型：</strong>{{ scope.row.type }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <!-- 编辑用户信息对话框 -->
    <el-dialog title="编辑用户信息" :visible.sync="editDialogVisible" width="50%" class="edit-dialog">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="账户">
          <el-input v-model="editForm.account" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="editForm.password" type="password" />
        </el-form-item>
        <el-form-item label="用户类型">
          <el-select v-model="editForm.type">
            <el-option label="管理员" value="管理员" />
            <el-option label="普通用户" value="普通用户"/>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "UserManage",
  data() {
    return {
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 12,
      },
      filters: {
        account: "",
        type: "",
      },
      autoSearch: false,
      editDialogVisible: false,
      editForm: {
        account: "",
        password: "",
        type: "",
      },
    };
  },
  watch: {
    "params.pageNum"(newPage) {
      this.load();
    },
    "params.pageSize"(newSize) {
      this.load();
    },
  },
  methods: {
    async load() {
      try {
        const searchParams = { ...this.params, ...this.filters };
        const res = await request.get("/login/Page", { params: searchParams });
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
    async update() {
      try {
        const res = await request.post("/api/users", this.editForm);
        if (res.code === "200") {
          this.$message.success("用户信息已更新！");
          await this.load();
        } else {
          this.$message.error(res.msg);
        }
      } catch (error) {
        this.$message.error("用户信息更新失败");
      }
    },
    handleSearch() {
      this.params.pageNum = 1;
      this.load();
    },
    handleReset() {
      this.filters = {
        account: "",
        type: "",
      };
      this.params.pageNum = 1;
      this.load();
    },
    handleInput() {
      if (this.autoSearch) {
        this.params.pageNum = 1;
        this.load();
      }
    },
    handlePageChange(page) {
      this.params.pageNum = page;
    },
    handleRowClick(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },
    saveEdit() {
      this.update();
      this.editDialogVisible = false;
      this.load();
    },
  },
  created() {
    this.load();
  },
};
</script>

<style scoped>
.table-container {
  padding: 10px;
}

.filter-row {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.filter-input {
  width: 150px;
}

.button-row {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.auto-search-text {
  font-size: 14px;
  color: #333;
  margin-right: 10px;
}

.search-buttons {
  display: flex;
  gap: 3px;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}

.no-data {
  text-align: center;
  margin-top: 20px;
  color: #888;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
  font-weight: bold;
}

.edit-dialog .el-form-item {
  margin-bottom: 20px;
}

.edit-dialog .el-input,
.edit-dialog .el-select {
  border-radius: 5px;
}

.edit-dialog .el-dialog {
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
}

.dialog-footer {
  text-align: right;
}

.el-button--primary {
  background-color: #409eff;
  border-color: #409eff;
}

.el-button--warning {
  background-color: #f56c6c;
  border-color: #f56c6c;
}
</style>
