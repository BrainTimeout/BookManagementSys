<template>
  <div class="category-container">
    <!-- 搜索框 -->
    <el-row class="filter-row" type="flex" align="middle">
      <el-input
          v-model="filters.id"
          placeholder="分类ID"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.name"
          placeholder="分类名称"
          class="filter-input"
          @input="handleInput"
      />
      <el-input
          v-model="filters.remark"
          placeholder="备注"
          class="filter-input"
          @input="handleInput"
      />
      <!-- 自动搜索开关 -->
      <el-switch v-model="autoSearch" @change="handleSearch" />
      <span class="auto-search-text">自动搜索</span>
      <div class="search-buttons">
        <el-button type="primary" @click="handleSearch">搜索</el-button>
        <el-button type="warning" @click="handleReset">重置</el-button>
      </div>
    </el-row>

    <!-- 添加分类 -->
    <div class="add-category">
      <span class="add-category-text" @click="openAddDialog">+ 添加分类</span>
    </div>

    <!-- 表格 -->
    <el-table
        :data="tableData"
        stripe :default-sort="{ prop: 'id', order: 'ascending' }"
        @sort-change="handleSortChange"
        row-key="id"
        default-expand-all
        :tree-props="{children: 'children'}">
      <el-table-column prop="id" label="分类ID" sortable />
      <el-table-column prop="name" label="分类名称" />
      <el-table-column prop="remark" label="备注" />
      <el-table-column label="创建时间">
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.createtime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间">
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.updatetime) }}</span>
        </template>
      </el-table-column>
      <!-- 操作列 -->
      <el-table-column label="操作" width = 350px>
        <template slot-scope="scope">
          <el-button
              size="small"
              type="success"
              icon="el-icon-plus"
              v-if ="!scope.row.pid"
              @click="openAddChildrenDialog(scope.row)"
          >添加二级分类
          </el-button>
          <el-button
              size="small"
              type="primary"
              icon="el-icon-edit"
              @click="handleEdit(scope.row)"
          >修改
          </el-button>
          <el-button
              size="small"
              type="danger"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
      <!-- 添加二子级分类列 -->
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

    <!-- 添加分类的对话框 -->
    <el-dialog
        title="添加新分类"
        :visible.sync="addDialogVisible"
        width="30%"
    >
      <el-form :model="newCategory" label-width="100px">
        <el-form-item label="分类名称">
          <el-input v-model="newCategory.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="newCategory.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddCategory">保存</el-button>
      </span>
    </el-dialog>

    <!-- 修改分类的对话框 -->
    <el-dialog
        title="修改分类"
        :visible.sync="editDialogVisible"
        width="30%"
    >
      <el-form :model="editCategory" label-width="100px">
        <el-form-item label="分类名称">
          <el-input v-model="editCategory.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="editCategory.remark" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleEditCategory">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request"; // 请根据你的实际请求方式调整

export default {
  name: "CategoryList",
  data() {
    return {
      tableData: [],  // 存储分类数据
      total: 0,       // 数据总数
      params: {
        pageNum: 1,
        pageSize: 10,
      },
      filters: {
        name: "",
        remark: "",
        id: "",
        sortOrder: "ascending", // 默认升序
      },
      autoSearch: false,  // 自动搜索开关
      addDialogVisible: false,  // 控制添加分类对话框显示与否
      addChildrenDialog: false, // 控制添加二级分类对话框显示与否
      editDialogVisible: false,  // 控制修改分类对话框显示与否
      newCategory: {
        name: "",
        remark: "",
        pid: "",
      },
      editCategory: {
        id: null,
        name: "",
        remark: "",
      },
    };
  },
  methods: {
    // 格式化时间
    formatDate(date) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(date).toLocaleDateString('zh-CN', options);
    },

    // 加载数据
    async load() {
      try {
        const res = await request.get("/Category/Page", {
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

    // 处理排序变化
    handleSortChange({ prop, order }) {
      this.filters.sortOrder = order === "ascending" ? "ascending" : "descending";
      this.load();
    },

    // 搜索
    handleSearch() {
      this.params.pageNum = 1;
      this.load();
    },

    // 重置搜索条件
    handleReset() {
      this.filters = {
        name: "",
        remark: "",
        id: "",
        sortOrder: "ascending", // 默认升序
      };
      this.params.pageNum = 1;
      this.load();
    },

    // 分页改变
    handlePageChange(page) {
      this.params.pageNum = page;
      this.load();
    },

    // 输入时触发搜索（自动搜索功能）
    handleInput() {
      if (this.autoSearch) {
        this.params.pageNum = 1;
        this.load();
      }
    },

    // 编辑操作
    handleEdit(row) {
      this.editCategory = { ...row };  // 复制选中的分类数据到编辑模型
      this.editDialogVisible = true;  // 显示修改对话框
    },

    // 删除操作
    async handleDelete(row) {
      try {
        const res = await request.delete(`/Category/Delete/${row.id}`);
        if (res.code === "200") {
          this.$message.success("删除成功");
          this.load();  // 刷新数据
        } else {
          this.$message.error("删除失败");
        }
      } catch (error) {
        this.$message.error("删除失败");
      }
    },

    // 打开添加分类的对话框
    openAddDialog() {
      this.newCategory.pid = "";
      this.addDialogVisible = true;
    },

    openAddChildrenDialog(row){
      this.newCategory.pid = row.id;
      this.addDialogVisible = true;
    },

    // 处理保存新分类
    async handleAddCategory() {
      try {
        const res = await request.post("/Category/Insert", this.newCategory);
        if (res.code === "200") {
          this.$message.success("分类添加成功");
          this.addDialogVisible = false;
          this.load();  // 刷新数据
        } else {
          this.$message.error("分类添加失败");
        }
      } catch (error) {
        this.$message.error("分类添加失败");
      }
    },

    // 处理保存修改分类
    async handleEditCategory() {
      try {
        const res = await request.put(`/Category/Update/`,this.editCategory);
        if (res.code === "200") {
          this.$message.success("分类修改成功");
          this.editDialogVisible = false;
          this.load();  // 刷新数据
        } else {
          this.$message.error("分类修改失败");
        }
      } catch (error) {
        this.$message.error("分类修改失败");
      }
    },
  },

  created() {
    this.load();
  },
};
</script>

<style scoped>
.category-container {
  padding: 20px;
}

.filter-row {
  margin-bottom: 20px;
}

.filter-input {
  width: 200px;
  margin-right: 10px;
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

.el-table .el-button {
  margin-right: 5px;
}

.add-category {
  text-align: right;
  margin-bottom: 10px;
}

.add-category-text {
  font-size: 16px;
  color: #409EFF;
  cursor: pointer;
  font-weight: bold;
}

.add-category-text:hover {
  text-decoration: underline;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}
</style>
