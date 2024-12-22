<template>
  <div class="book-detail">
    <!-- 返回按钮 -->
    <el-button type="primary" @click="goBack" class="back-button">返回书籍列表</el-button>

    <!-- 加载状态 -->
    <el-loading v-if="loading" class="loading-overlay" fullscreen lock text="加载中..." />

    <!-- 展示书籍详细信息 -->
    <div v-else-if="book" class="book-info">
      <h1>{{ book.name }}</h1>
      <p><strong>作者:</strong> {{ book.author || "未知" }}</p>
      <p><strong>出版日期:</strong> {{ book.publishDate || "暂无出版日期" }}</p>
      <p><strong>出版社:</strong> {{ book.publisher || "暂无出版社" }}</p>
      <p><strong>分类:</strong> {{ book.category || "暂无分类" }}</p>
      <p><strong>积分:</strong> {{ book.score }}</p>
      <p><strong>库存数量:</strong> {{ book.nums || 0 }}</p>
      <p><strong>描述:</strong> {{ book.description || "暂无描述" }}</p>
      <p><strong>创建时间:</strong> {{ book.createtime }}</p>
      <p><strong>更新时间:</strong> {{ book.updatetime }}</p>

      <!-- 图书封面 -->
      <img :src="book.cover || defaultCover" alt="书籍封面" class="book-cover" />

      <!-- 评论部分 -->
      <div class="comments">
        <h2 class="comments-title">评论</h2> <!-- 固定标题 -->
        <div v-if="comments.length" class="comment-list">
          <div v-for="comment in comments" :key="comment.id" class="comment-item">
            <!-- 用户头像和评论内容 -->
            <div class="comment-header" @click="showUserDetail(comment.account)">
              <img
                  :src="`${FileUrl}Download/Avatar/${comment.account}.jpg`"
                  alt="用户头像"
                  class="user-avatar"
              />
              <p><strong>{{ comment.username }}:</strong></p>
            </div>
            <p>{{ comment.content }}</p>
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

    <!-- 用户详情对话框 -->
    <el-dialog
        :visible.sync="userDetailDialogVisible"
        title="用户详细信息"
        width="50%"
        @close="resetUserDetail"
    >
      <div v-if="userDetail">
        <img
            :src="`${FileUrl}Download/Avatar/${userDetail.userProfile.account}.jpg`"
            alt="用户头像"
            class="user-avatar-header"
        />
        <p><strong>用户名:</strong> {{ userDetail.userProfile.username }}</p>
        <p><strong>账户:</strong> {{ userDetail.userProfile.account }}</p>
        <p><strong>年龄:</strong> {{ userDetail.userProfile.age }}</p>
        <p><strong>性别:</strong> {{ userDetail.userProfile.sex === 'male' ? '男' : '女' }}</p>
        <p><strong>电话:</strong> {{ userDetail.userProfile.phone || '暂无电话' }}</p>
        <p><strong>地址:</strong> {{ userDetail.userProfile.address || '暂无地址' }}</p>
        <p><strong>邮箱:</strong> {{ userDetail.userProfile.email || '暂无邮箱' }}</p>
        <p><strong>简介:</strong> {{ userDetail.userProfile.introduce || '暂无简介' }}</p>
        <p><strong>创建时间:</strong> {{ userDetail.userProfile.createtime }}</p>
        <p><strong>更新时间:</strong> {{ userDetail.userProfile.updatetime }}</p>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import Cookies from 'js-cookie';

export default {
  props: ["id"], // 从父组件接收书籍 ID
  data() {
    return {
      FileUrl: "", // 动态拼接的文件URL前缀
      book: null,
      comments: [], // 存储评论列表
      newComment: "", // 新评论内容
      loading: false,
      account: "", // 用户账户
      username: "", // 用户名
      defaultCover: "https://via.placeholder.com/300x400?text=No+Image", // 默认图片占位符
      userDetailDialogVisible: false, // 控制用户详情对话框的显示
      userDetail: null, // 存储用户的详细信息
    };
  },
  methods: {
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
    async addComment() {
      if (!this.newComment.trim()) {
        this.$message.warning("评论内容不能为空");
        return;
      }
      if (!this.account) {
        this.$message.warning("请先登录");
        return;
      }
      try {
        const res = await request.post(`/User/PostComment`, {
          bookNo: this.book.bookNo,
          content: this.newComment,
          username: this.username,
          account: this.account,
        });
        if (res.code === "200") {
          this.$message.success("评论成功");
          this.comments.push({
            account: this.account,
            username: this.username,
            content: this.newComment,
          });
          this.newComment = "";
        } else {
          this.$message.error("评论失败");
        }
      } catch {
        this.$message.error("评论失败");
      }
    },
    showUserDetail(account) {
      this.loading = true;
      request.get(`/User/GetAuthInfo/${account}`).then((res) => {
        if (res.code === "200") {
          this.userDetail = res.data;
          this.userDetailDialogVisible = true; // 显示对话框
        } else {
          this.$message.error("加载用户详情失败");
        }
      }).finally(() => {
        this.loading = false;
      });
    },
    resetUserDetail() {
      this.userDetail = null;
    },
    goBack() {
      this.$router.push({ name: "bookShow" });
    },
  },
  created() {
    this.loadBook();
    this.FileUrl = `${request.defaults.baseURL}/File/`; // 动态设置 FileUrl
  },
  mounted() {
    const AuthInfo = Cookies.get('authInfo');
    if (AuthInfo) {
      const authInfo = JSON.parse(AuthInfo); // cookie中存储的是JSON字符串
      this.account = authInfo.userProfile.account || "";
      this.username = authInfo.userProfile.username || "";
    }
  }
};
</script>

<style scoped>
.user-avatar-header {
  position: absolute;
  top: 50px;
  right: 80px;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  border: 2px solid #f0f0f0;
}

.book-detail {
  padding: 20px;
}

.book-cover {
  width: 200px;
  height: 300px;
  object-fit: cover;
  border-radius: 8px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.comments {
  margin-top: 30px;
  max-height: 60vh;
  overflow-y: auto;
}

.comments-title {
  position: sticky;
  top: 0;
  background-color: #fff;
  padding: 10px;
  font-size: 20px;
  font-weight: bold;
  z-index: 10;
  border-bottom: 1px solid #f0f0f0;
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

.comment-header {
  display: flex;
  align-items: center;
}

.user-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 10px;
  cursor: pointer;
}

.add-comment {
  margin-top: 20px;
  position: sticky;
  bottom: 0;
  background-color: #fff;
  padding: 10px;
  border-top: 1px solid #f0f0f0;
  z-index: 10;
}

.add-comment textarea {
  margin-bottom: 10px;
}
</style>
