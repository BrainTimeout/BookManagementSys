import Vue from 'vue'
import VueRouter from 'vue-router'
import Cookies from 'js-cookie';


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
        path:'/categoryList',
        name:'categoryList',
        component:() => import('../views/category/List.vue')
      },
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
  {
    path:"*",
    component:()=>import('@/views/404.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to,from,next)=>{
  if(to.path === '/login') next()
  const loginDTO = Cookies.get("loginDTO")
  if(!loginDTO && to.path !== '/login') return next("/login")
  next()
})

export default router
