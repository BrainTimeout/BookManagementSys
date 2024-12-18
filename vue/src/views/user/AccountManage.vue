<template>
  <div class="table-container">
    <!-- 筛选条件 -->
    <el-row class="filter-row" type="flex" align="middle">
      <el-input v-model="filters.account" placeholder="账户" class="filter-input" @input="handleInput" />
      <el-select v-model="filters.usertype" placeholder="用户类型" class="filter-input" @input="handleInput">
        <el-option label="管理员" value="admin" />
        <el-option label="普通用户" value="user" />
      </el-select>
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
      <el-table-column label="用户类型">
        <template slot-scope="scope">
          <span>{{ getUserType(scope.row.usertype) }}</span> <!-- 修改为 usertype -->
        </template>
      </el-table-column>
      <el-table-column label="封禁状态">
        <template slot-scope="scope">
          <span v-if="new Date(scope.row.banuntil) > new Date()">
            已封禁，{{ formatDate(scope.row.banuntil) }}解禁
          </span>
          <span v-else>未封禁</span>
        </template>
      </el-table-column>
      <el-table-column label="" width="60">
        <template slot-scope="scope">
          <el-button type="danger" icon="el-icon-delete" circle @click.stop="handleDelete(scope.row)" />
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
    <el-dialog title="编辑账号信息" :visible.sync="editDialogVisible" width="50%" class="edit-dialog">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="账号"><el-input v-model="editForm.account" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="editForm.password" type="password" /></el-form-item>
        <el-form-item label="用户类型">
          <el-select v-model="editForm.usertype"> <!-- 修改为 usertype -->
            <el-option label="管理员" value="admin" />
            <el-option label="普通用户" value="user" />
          </el-select>
        </el-form-item>
        <el-form-item label="封禁截止时间">
          <el-date-picker
              v-model="editForm.banuntil"
              type="datetime"
              placeholder="请选择封禁截止时间"
              format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
          />
        </el-form-item>

        <!-- 快捷选项按钮 -->
        <div class="quick-actions">
          <el-button @click="setImmediateUnban" type="success" size="mini" plain>立刻解禁</el-button>
          <el-button @click="setBanFor10Days" type="danger" size="mini" plain>封禁10天</el-button>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </span>
    </el-dialog>

    <!-- 删除用户对话框 -->
    <el-dialog title="删除用户" :visible.sync="deleteDialogVisible" width="30%">
      <p>确定要删除该用户吗？</p>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false">取消</el-button>
        <el-button type="danger" @click="deleteUser">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AccountManage",
  data() {
    return {
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 8,
      },
      filters: {
        account: "",
        usertype: "",  // 修改为 usertype
      },
      autoSearch: false,
      editDialogVisible: false,
      editForm: {
        account: "",
        password: "",
        usertype: "",  // 修改为 usertype
        banuntil: "",
      },
      deleteDialogVisible: false,
      deleteUserAccount: null,
    };
  },
  watch: {
    "params.pageNum": "load",
    "params.pageSize": "load",
  },
  methods: {
    async load() {
      try {
        const res = await request.get("/Accounts/Page", {
          params: { ...this.params, ...this.filters },
        });
        if (res.code === "200") {
          this.tableData = res.data.list;
          this.total = res.data.total;
        } else {
          this.$message.error("加载数据失败");
        }
      } catch {
        this.$message.error("加载数据失败");
      }
    },
    async update() {
      try {
        const res = await request.put("/Accounts/Update", this.editForm);
        if (res.code === "200") {
          this.$message.success("用户信息已更新！");
          this.load();
        } else {
          this.$message.error(res.msg);
        }
      } catch {
        this.$message.error("用户信息更新失败");
      }
    },
    handleSearch() {
      this.params.pageNum = 1;
      this.load();
    },
    handleReset() {
      this.filters = { account: "", usertype: "" };  // 修改为 usertype
      this.params.pageNum = 1;
      this.load();
    },
    handleInput() {
      if (this.autoSearch) this.handleSearch();
    },
    handlePageChange(page) {
      this.params.pageNum = page;
    },
    handleRowClick(row) {
      this.editForm = { ...row };
      this.editDialogVisible = true;
    },
    handleDelete(row) {
      this.deleteUserAccount = row.account;
      this.deleteDialogVisible = true;
    },
    saveEdit() {
      this.update();
      this.editDialogVisible = false;
    },
    async deleteUser() {
      try {
        const res = await request.delete(`/Accounts/Delete/${this.deleteUserAccount}`);
        if (res.code === "200") {
          this.$message.success("用户已删除！");
          this.load();
          this.deleteDialogVisible = false;
        } else {
          this.$message.error("删除失败");
        }
      } catch {
        this.$message.error("删除失败");
        this.deleteDialogVisible = false;
      }
    },
    formatDate(date) {
      const d = new Date(date);
      return `${d.getFullYear()}-${(d.getMonth() + 1).toString().padStart(2, '0')}-${d.getDate().toString().padStart(2, '0')} ${d.getHours().toString().padStart(2, '0')}:${d.getMinutes().toString().padStart(2, '0')}:${d.getSeconds().toString().padStart(2, '0')}`;
    },
    getUserType(usertype) {
      switch (usertype) {  // 修改为 usertype
        case "admin": return "管理员";
        case "user": return "普通用户";
        default: return "未知";
      }
    },
    setImmediateUnban() {
      this.editForm.banuntil = new Date().toISOString();
    },
    setBanFor10Days() {
      const now = new Date();
      now.setDate(now.getDate() + 10);
      this.editForm.banuntil = now.toISOString();
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

.el-dropdown {
  margin-right: 10px;
}

.el-dialog .el-form-item {
  margin-bottom: 20px;
}

.quick-actions {
  margin-top: 10px;
  display: flex;
  gap: 10px;
}
</style>
