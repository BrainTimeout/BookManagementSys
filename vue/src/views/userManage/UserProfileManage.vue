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
          v-model="filters.username"
          placeholder="用户名"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.age"
          placeholder="年龄"
          class="filter-input"
          @input="handleInput"
      />
      <el-select
          v-model="filters.sex"
          placeholder="性别"
          class="filter-input"
          @change="handleInput"
      >
        <el-option label="男" value="male" />
        <el-option label="女" value="female" />
      </el-select>
      <el-input
          v-model="filters.address"
          placeholder="地址"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.phone"
          placeholder="电话"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.detail"
          placeholder="描述信息"
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
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="age" label="年龄" />
      <el-table-column label="性别">
        <template slot-scope="scope">
          <span>{{ getUserSex(scope.row.sex) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="address" label="地址" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column label="更多">
        <template v-slot="scope">
          <el-dropdown trigger="click">
            <span class="el-dropdown-link" @click.stop>更多</span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <strong>邮箱：</strong>{{ scope.row.email || '暂无邮箱' }}
              </el-dropdown-item>
              <el-dropdown-item>
                <strong>简介：</strong>{{ scope.row.introduce || '暂无简介' }}
              </el-dropdown-item>
              <el-dropdown-item>
                <strong>创建时间：</strong>{{ scope.row.createtime || '未知' }}
              </el-dropdown-item>
              <el-dropdown-item>
                <strong>更新时间：</strong>{{ scope.row.updatetime || '未知' }}
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
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" :style="{ width: '30%' }" />
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="editForm.age" :style="{ width: '20%' }" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="editForm.sex" :style="{ width: '20%' }">
            <el-option label="男" value="male" />
            <el-option label="女" value="female" />
          </el-select>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="editForm.address" :style="{ width: '45%'}" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="editForm.phone" :style="{ width: '45%' }" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" :style="{ width: '45%' }" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input type="textarea" v-model="editForm.introduce" :style="{ width: '90%' }" rows = 4 />
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
  name: "UserProfileManage",
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
        username: "",
        age: "",
        sex: null,
        address: "",
        phone: "",
        detail: "",
      },
      autoSearch: false,
      editDialogVisible: false,
      editForm: {
        account: "",
        username: "",
        age: "",
        sex: "",
        address: "",
        phone: "",
        introduce: "",
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
        const res = await request.get("/UserProfile/Page", { params: searchParams });
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
        const res = await request.put("/UserProfile/Update", this.editForm);
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
        username: "",
        age: "",
        sex: null,
        address: "",
        phone: "",
        detail: "",
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
    getUserSex(sex){
      switch (sex) {  // 修改为 usertype
        case "male": return "男";
        case "female": return "女";
        default: return "未知";
      }
    }
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

.edit-dialog .el-dialog {
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
}

.dialog-footer {
  text-align: right;
}

</style>