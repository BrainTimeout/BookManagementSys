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
    path: '/user',
    name: 'user',
    redirect: '/bookShow',
    component: () => import('../views/layout/User.vue'),
    children: [
      {
        path:'/bookShow',
        name:'bookShow',
        component:() => import('../views/user/Show.vue')
      },
      {
        path: "/bookDetail/:id",
        name: "bookDetail",
        component:() => import('../views/user/Detail.vue'),
        props: true,
      },
      {
        path: "/currentBorrow/:account",
        name: "currentBorrow",
        component:() => import('../views/user/CurrentBorrow.vue'),
        props: true,
      },
    ]
  },
  {
    path: '/admin',
    name: 'admin',
    redirect: '/home',
    component: () => import('../views/layout/Admin.vue'),
    children: [
      {
        path:'/home',
        name:'home',
        component:() => import('../views/Home')
      },
      {
        path:'/categoryList',
        name:'categoryList',
        component:() => import('../views/category/CategoryManage.vue')
      },
      {
        path:'/bookList',
        name:'bookList',
        component:() => import('../views/book/BookManage.vue')
      },
      {
        path:'/borrowBook',
        name:'/borrowBook',
        component:() => import('../views/borrow/BorrowBook')
      },
      {
        path:'/revertBook',
        name:'/revertBook',
        component:() => import('../views/borrow/RevertBook')
      },
      {
        path:'/borrowList',
        name:'borrowList',
        component:() => import('../views/borrow/BorrowList.vue')
      },
      {
        path:'/revertList',
        name:'revertList',
        component:() => import('../views/borrow/RevertList.vue')
      },
      {
        path: '/userManage',
        name: 'userManage',
        component: () => import('../views/userManage/UserProfileManage.vue'),
      },
      {
        path: '/userJoin',
        name: 'userJoin',
        component: () => import('../views/userManage/UserJoin.vue'),
      },
      {
        path: '/accountManage',
        name: 'accountManage',
        component: () => import('../views/userManage/AccountManage'),
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
  const loginDTO = Cookies.get("authInfo")
  if(!loginDTO && to.path !== '/login') return next("/login")
  next()
})

export default router
