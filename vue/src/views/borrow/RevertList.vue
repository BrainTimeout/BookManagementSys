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
      <el-table-column prop="revertDate" label="实际归还时间" sortable />

      <!-- 删除列 -->
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="showDeleteDialog(scope.row.id)"
          >
            删除
          </el-button>
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

    <!-- 删除确认对话框 -->
    <el-dialog
        title="确认删除"
        :visible.sync="dialogVisible"
        @close="resetDialog"
        width="400px"
    >
      <span>你确定要删除这条数据吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetDialog">取消</el-button>
        <el-button type="primary" @click="confirmDelete">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request"; // 请根据实际请求方式调整

export default {
  name: "revertList",
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
      dialogVisible: false, // 删除确认对话框显示控制
      id: null,  // 存储待删除的 id
    };
  },
  methods: {
    // 加载数据
    async load() {
      try {
        const res = await request.get("/Borrow/RevertPage", {
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

    // 显示删除确认弹窗
    showDeleteDialog(id) {
      this.id = id;
      this.dialogVisible = true;
    },

    // 取消删除
    resetDialog() {
      this.dialogVisible = false;
      this.id = null;
    },

    // 确认删除操作
    async confirmDelete() {
      try {
        const res = await request.delete(`/Borrow/DeleteRevert/${this.id}`);
        if (res.code === "200") {
          this.$message.success("删除成功");
          this.load();  // 删除成功后重新加载数据
          this.resetDialog(); // 关闭对话框
        } else {
          this.$message.error("删除失败");
        }
      } catch (error) {
        this.$message.error("删除失败");
        this.resetDialog(); // 关闭对话框
      }
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
