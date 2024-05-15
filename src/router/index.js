import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'layout',
      redirect: '/home',
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('../views/homePage.vue')
        },
        {
          path: '/about',
          name: 'about',
          component: () => import('../views/aboutPage.vue')
        },
        {
          path: '/bin',
          name: 'bin',
          component: () => import('../views/binPage.vue')
        }
      ],
      component: () => import('../views/layoutContainer.vue')
    }
  ]
})

export default router

