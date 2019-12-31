import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    meta: { layout: 'empty' },
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    meta: { layout: 'empty' },
    component: () => import('../views/Register.vue')
  },
  {
    path: '/',
    name: 'home',
    meta: { layout: 'main', auth: true },
    component: () => import('../views/Home.vue')
  },
  {
    path: '/profile',
    name: 'profile',
    meta: { layout: 'main', auth: true },
    component: () => import('../views/Profile.vue')
  },
  {
    path: '/notebooks',
    name: 'notebooks',
    meta: { layout: 'main', auth: true },
    component: () => import('../views/Notebooks.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const usernameFromSessionStorage = sessionStorage.getItem('username')
  const tokenFromSessionStorage = sessionStorage.getItem('token')

  const currentUser = store.getters.getToken

  const requireAuth = to.matched.some(record => record.meta.auth)
  if (requireAuth && !currentUser && !tokenFromSessionStorage) {
    next('/login')
  } else {
    if (tokenFromSessionStorage) {
      store.commit('login', {
        username: usernameFromSessionStorage,
        token: tokenFromSessionStorage
      })
    }
    next()
  }
})

export default router
