<template>
  <div class="book-detail">
    <!-- 返回按钮 -->
    <el-button type="primary" @click="goBack" class="back-button">返回书籍列表</el-button>

    <!-- 加载状态 -->
    <el-loading v-if="loading" class="loading-overlay" fullscreen lock text="加载中..." />

    <!-- 展示书籍详细信息 -->
    <div v-else-if="book" class="book-info">
      <h1>{{ book.name }}</h1>
      <p><strong>描述:</strong> {{ book.description || "暂无描述" }}</p>
      <p><strong>作者:</strong> {{ book.author || "未知" }}</p>
      <!-- 其他书籍详细信息省略 -->
      <img :src="book.cover || defaultCover" alt="书籍封面" class="book-cover" />

      <!-- 评论部分 -->
      <div class="comments">
        <h2>评论</h2>
        <!-- 评论列表 -->
        <div v-if="comments.length" class="comment-list">
          <div v-for="comment in comments" :key="comment.id" class="comment-item">
            <p><strong>用户 {{ comment.account }}:</strong> {{ comment.content }}</p>
          </div>
        </div>
        <div v-else class="no-comments">暂无评论</div>

        <!-- 添加评论 -->
        <div class="add-comment">
          <el-input
              type="textarea"
              v-model="newComment"
              placeholder="输入你的评论"
              rows="3"
          ></el-input>
          <el-button
              type="primary"
              @click="addComment"
              :disabled="!newComment.trim()"
          >
            提交评论
          </el-button>
        </div>
      </div>
    </div>

    <!-- 错误提示 -->
    <div v-else class="error-message">
      <p>书籍信息加载失败或书籍不存在。</p>
      <el-button type="warning" @click="goBack">返回</el-button>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  props: ["id"], // 从父组件接收书籍 ID
  data() {
    return {
      book: null,
      comments: [], // 存储评论列表
      newComment: "", // 新评论内容
      loading: false,
      defaultCover: "https://via.placeholder.com/300x400?text=No+Image", // 默认图片占位符
    };
  },
  methods: {
    // 加载书籍详细信息
    async loadBook() {
      if (!this.id) {
        this.$message.error("无效的书籍 ID");
        this.goBack();
        return;
      }
      this.loading = true;
      try {
        const res = await request.get(`/User/${this.id}`);
        if (res.code === "200") {
          this.book = res.data;
        } else {
          this.$message.error("加载书籍信息失败");
        }
      } catch {
        this.$message.error("加载书籍信息失败");
      } finally {
        this.loading = false;
      }
      try {
        const res = await request.get(`/User/Comment/${this.book.bookNo}`);
        if (res.code === "200") {
          this.comments = res.data || [];
        } else {
          this.$message.error("加载评论失败");
        }
      } catch {
        this.$message.error("加载评论失败");
      }
    },
    // 添加评论
    async addComment() {
      if (!this.newComment.trim()) {
        this.$message.warning("评论内容不能为空");
        return;
      }
      try {
        const res = await request.post(`/User/Comment`, {
          bookNo: this.book.bookNo,
          content: this.newComment,
          account: "1", // 假设当前登录用户的 account 是 1，需替换为动态用户
        });
        if (res.code === "200") {
          this.$message.success("评论成功");
          this.newComment = "";
          this.loadComments(); // 重新加载评论
        } else {
          this.$message.error("评论失败");
        }
      } catch {
        this.$message.error("评论失败");
      }
    },
    // 返回书籍列表
    goBack() {
      this.$router.push({ name: "bookShow" });
    },
  },
  created() {
    this.loadBook();
  },
};
</script>

<style scoped>
.book-detail {
  padding: 20px;
}
.comments {
  margin-top: 30px;
}
.comment-list {
  margin-top: 10px;
  padding-left: 10px;
}
.comment-item {
  margin-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
  padding-bottom: 5px;
}
.add-comment {
  margin-top: 20px;
}
.add-comment textarea {
  margin-bottom: 10px;
}
</style>
