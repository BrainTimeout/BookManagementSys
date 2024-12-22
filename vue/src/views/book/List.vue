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

    <!-- 添加书籍 -->
    <div class="add-book">
      <span class="add-book-text" @click="openAddDialog">+ 添加书籍</span>
    </div>

    <!-- 书籍列表表格 -->
    <el-table
        :data="tableData"
        stripe
        :default-sort="{ prop: 'id', order: 'ascending' }"
        @sort-change="handleSortChange"
        row-key="id"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="demo-table-expand">
            <el-form-item label="描述">
              <span>{{ props.row.description || '暂无描述' }}</span>
            </el-form-item>
            <el-form-item label="出版日期">
              <span>{{ props.row.publishDate || '暂无信息' }}</span>
            </el-form-item>
            <el-form-item label="标准码">
              <span>{{ props.row.bookNo || '暂无信息' }}</span>
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
      <el-table-column prop="id" label="书籍ID" sortable />
      <el-table-column prop="name" label="书名" />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="publisher" label="出版社" />
      <el-table-column prop="category" label="分类" />
      <el-table-column prop="score" label="积分" />
      <el-table-column prop="nums" label="当前数量" />
      <el-table-column label="封面">
        <template slot-scope="scope">
          <!-- 如果 cover 字段有值，则显示图片 -->
          <img :src="scope.row.cover" v-if="scope.row.cover" alt="封面" style="width: 100px; height: 150px; object-fit: cover;"/>
          <!-- 如果 cover 字段为空，则显示默认占位图 -->
          <span v-else>无封面</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200px">
        <template slot-scope="scope">
          <el-button
              size="small"
              type="success"
              icon="el-icon-edit"
              @click="handleEdit(scope.row)"
          >编辑</el-button>
          <el-button
              size="small"
              type="danger"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加书籍对话框 -->
    <el-dialog
        title="添加新书籍"
        :visible.sync="addDialogVisible"
        width="50%"
    >
      <el-form :model="newBook" label-width="100px">
        <el-form-item label="书名">
          <el-input v-model="newBook.name" placeholder="请输入书名" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
              type="textarea"
              v-model="newBook.description"
              placeholder="请输入描述"
              rows="4"
          />
        </el-form-item>
        <el-form-item label="出版日期">
          <el-date-picker
              v-model="newBook.publishDate"
              type="datetime"
              placeholder="请输入出版日期"
              format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="newBook.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="newBook.publisher" placeholder="请输入出版社" />
        </el-form-item>
        <el-form-item label="分类">
          <el-cascader
              :props = "{value:'name',label:'name'}"
              v-model="newBook.category"
              :options="categories"
              @change="handleNewCategoriesChange"></el-cascader>
        </el-form-item>
        <el-form-item label="标准码">
          <el-input
              v-model="newBook.bookNo"
              placeholder="请输入标准码"
              @input="handleNewBookNoChange"
          />
        </el-form-item>
        <el-form-item label="借书积分">
          <el-input v-model="newBook.score" placeholder="请输入借书积分" />
        </el-form-item>
        <el-form-item label="数量">
          <el-input v-model="newBook.nums" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="封面">
          <el-input v-model="newBook.cover" placeholder="请输入封面" />
          <el-upload
              class="cover-uploader"
              :action="coverUpLoadUrl"
              :show-file-list="false"
              :before-upload="beforeCoverUpload"
              :on-change="handleCoverChange"
              :on-success="handleNewCoverSuccess"
              :on-error="handleCoverError"
          >
            <span class="upload-text">点击上传封面</span>
          </el-upload>
          <div v-if="newBook.cover">
            <img :src="editBook.cover" alt="封面预览" style="width: 150px; height: 225px; object-fit: cover; margin-top: 10px;" />
          </div>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleAddBook">保存</el-button>
      </span>
    </el-dialog>

    <!-- 编辑书籍对话框 -->
    <el-dialog
        title="编辑书籍"
        :visible.sync="editDialogVisible"
        width="50%"
    >
      <el-form :model="editBook" label-width="100px">
        <el-form-item label="书名">
          <el-input v-model="editBook.name" placeholder="请输入书名" />
        </el-form-item>
        <el-form-item label="描述">
          <el-input
              type="textarea"
              v-model="editBook.description"
              placeholder="请输入描述"
              rows="4"
          />
        </el-form-item>
        <el-form-item label="出版日期">
          <el-date-picker
              v-model="editBook.publishDate"
              type="datetime"
              placeholder="请输入出版日期"
              format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="editBook.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="editBook.publisher" placeholder="请输入出版社" />
        </el-form-item>
        <el-form-item label="分类">
          <el-cascader
              :props = "{value:'name',label:'name'}"
              v-model="editBook.category"
              :options="categories"
              @change="handleEditCategoriesChange"></el-cascader>
        </el-form-item>
        <el-form-item label="标准码">
          <el-input
              v-model="editBook.bookNo"
              placeholder="请输入标准码"
              @input="handleEditBookNoChange"
          />
        </el-form-item>

        <el-form-item label="借书积分">
          <el-input v-model="editBook.score" placeholder="请输入借书积分" />
        </el-form-item>
        <el-form-item label="数量">
          <el-input v-model="editBook.nums" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="封面">
          <el-input v-model="editBook.cover" placeholder="请输入封面" />
          <el-upload
              class="cover-uploader"
              :action="coverUpLoadUrl"
              :show-file-list="false"
              :before-upload="beforeCoverUpload"
              :on-change="handleCoverChange"
              :on-success="handleEditCoverSuccess"
              :on-error="handleCoverError"
          >
            <span class="upload-text">点击上传封面</span>
          </el-upload>
          <div v-if="editBook.cover">
            <img :src="editBook.cover" alt="封面预览" style="width: 150px; height: 225px; object-fit: cover; margin-top: 10px;" />
          </div>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleEditBook">保存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request"; // 请根据你的实际请求方式调整

export default {
  name: "bookList",
  data() {
    return {
      tableData: [],  // 存储书籍数据
      total: 0,       // 数据总数
      params: {
        pageNum: 1,
        pageSize: 6,
      },
      filters: {
        name: "",
        author: "",
        publisher: "",
        category:"",
        detail:"",
        sortOrder: "ascending",
      },
      autoSearch: false,  // 控制是否开启自动搜索
      addDialogVisible: false,  // 控制添加书籍对话框显示与否
      editDialogVisible: false,  // 控制编辑书籍对话框显示与否
      categories:[],
      newBook: {
        name: "",
        description: "",
        publishDate: "",
        author: "",
        publisher: "",
        category: "",
        bookNo: "",
        score:"",
        nums:"",
        cover: "",
      },
      editBook: {
        id: null,
        name: "",
        description: "",
        publishDate: "",
        author: "",
        publisher: "",
        category: "",
        bookNo: "",
        score:"",
        nums:"",
        cover: "",
      },
      FileUrl:"",
      coverUpLoadUrl:"",
    };
  },
  created() {
    try {
      request.get("/Category/Tree").then(res => {
        this.categories = res.data;
      });
    } catch (error) {
      this.$message.error("分类加载失败");
    }
    this.FileUrl=`${request.defaults.baseURL}/File/`;
    this.load();  // 初始化加载数据
  },
  methods: {
    handleEditBookNoChange(){
      this.coverUpLoadUrl = this.FileUrl + `Upload/Cover/${this.editBook.bookNo}.jpg`;
    },
    handleNewBookNoChange(){
      this.coverUpLoadUrl = this.FileUrl + `Upload/Cover/${this.newBook.bookNo}.jpg`;
    },
    beforeCoverUpload(file) {
      const isImage = file.type.startsWith('image/');
      if (!isImage) {
        this.$message.error('只能上传图片文件！');
      }
      return isImage;
    },

    // 处理头像上传后的变更
    handleCoverChange(file) {

    },
    // 上传成功后的回调
    handleEditCoverSuccess(response) {
      this.editBook.cover = this.FileUrl + `Download/Cover/${this.editBook.bookNo}.jpg?time=${new Date().getTime()}`;
    },

    handleNewCoverSuccess(response) {
      this.newBook.cover = this.FileUrl + `Download/Cover/${this.newBook.bookNo}.jpg?time=${new Date().getTime()}`;
    },

    // 上传失败的回调
    handleCoverError(error, file, fileList) {
      this.$message.error('封面上传失败');
    },
    // 加载数据
    async load() {
      try {
        const res = await request.get("/Book/Page", {
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
        name: "",
        author: "",
        publisher: "",
      };
      this.params.pageNum = 1;
      this.load();
    },
    handleSortChange({ prop, order }) {
      this.filters.sortOrder = order === "ascending" ? "ascending" : "descending";
      this.load();
    },
    // 分页改变
    handlePageChange(page) {
      this.params.pageNum = page;
      this.load();
    },
    handleEditCategoriesChange(val){
      this.editBook.category = val[val.length-1];
    },
    handleNewCategoriesChange(val){
      this.newBook.category = val[val.length-1];
    },
    // 编辑操作
    handleEdit(row) {
      this.editBook = { ...row };  // 复制选中的书籍数据到编辑模型
      this.editDialogVisible = true;  // 显示修改对话框
      this.coverUpLoadUrl = this.FileUrl + `Upload/Cover/${this.editBook.bookNo}.jpg`;
    },

    // 删除操作
    async handleDelete(row) {
      try {
        const res = await request.delete(`/Book/Delete/${row.id}`);
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

    // 打开添加书籍的对话框
    openAddDialog() {
      this.newBook = {  // 清空添加表单数据
        name: "",
        description: "",
        publishDate: "",
        author: "",
        publisher: "",
        category: "",
        bookNo: "",
        cover: "",
      };
      this.coverUpLoadUrl = "";
      this.addDialogVisible = true;  // 打开添加对话框
    },

    // 添加新书籍
    async handleAddBook() {
      try {
        const res = await request.post("/Book/Insert", this.newBook);
        if (res.code === "200") {
          this.$message.success("添加成功");
          this.addDialogVisible = false;  // 关闭对话框
          this.load();  // 刷新数据
        } else {
          this.$message.error("添加失败");
        }
      } catch (error) {
        this.$message.error("添加失败");
      }
    },

    // 编辑书籍
    async handleEditBook() {
      try {
        const res = await request.put("/Book/Update", this.editBook);
        if (res.code === "200") {
          this.$message.success("编辑成功");
          this.editDialogVisible = false;  // 关闭对话框
          this.load();  // 刷新数据
        } else {
          this.$message.error("编辑失败");
        }
      } catch (error) {
        this.$message.error("编辑失败");
      }
    },
  },
};
</script>

<style scoped>
.auto-search-text {
  font-size: 14px;
  color: #333;
  margin-right: 10px;
}

.demo-table-expand {
  font-size: 14px;
}

.demo-table-expand .el-form-item {
  margin-bottom: 10px;
  width: 100%;
}

.demo-table-expand label {
  width: 90px;
  color: #0468EF;  /* 设置label的颜色 */
  font-weight: bold;  /* 加粗label */
}

.demo-table-expand .el-form-item .el-input,
.demo-table-expand .el-form-item .el-date-picker {
  width: 100%;
}

.demo-table-expand .el-form-item .el-input::placeholder,
.demo-table-expand .el-form-item .el-date-picker__input::placeholder {
  color: #999;  /* 设置输入框的placeholder颜色 */
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

.add-book {
  margin-bottom: 20px;
}

.add-book {
  text-align: right;
  margin-bottom: 10px;
}

.add-book-text {
  cursor: pointer;
  color: #409eff;
}

.pagination {
  margin-top: 20px;
}
</style>