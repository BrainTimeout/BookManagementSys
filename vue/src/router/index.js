import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
    // 登录
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/login/Login.vue'),
  },
    // 主页
  {
    path: '/',
    name: 'user',
    component: () => import('../views/layout/User.vue'),
  },
  {
    path: '/admin',
    name: 'admin',
    component: () => import('../views/layout/Admin.vue'),
    children: [
      {
        path: '/userManage',
        name: 'userManage',
        component: () => import('../views/user/UserProfileManage.vue'),
      },
      {
        path: '/userJoin',
        name: 'userJoin',
        component: () => import('../views/user/UserJoin.vue'),
      },
      {
        path: '/accountManage',
        name: 'accountManage',
        component: () => import('../views/user/AccountManage'),
      },
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
