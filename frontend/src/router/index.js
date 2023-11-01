import { createRouter, createWebHistory } from 'vue-router'
import IndexLogin from '@/views/IndexLogin.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: IndexLogin
  },
  {
    path: '/students/:userId/:userName',
    name: 'students',
    component: () => import('../views/StudentPages.vue'),
  },
  {
    path: '/teachers/:userId/:userName',
    name: 'teachers',
    component: () => import('../views/TeacherPages.vue'),

  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: routes
})

export default router
