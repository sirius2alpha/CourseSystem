import { createRouter, createWebHistory } from 'vue-router'
import IndexLogin from '@/views/IndexLogin.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: IndexLogin
  },
  {
    path: '/students',
    name: 'students',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import('../views/StudentPages.vue')
  },
  {
    path: '/teachers',
    name: 'teachers',
    component: () => import('../views/TeacherPages.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes:routes
})

export default router