import { createRouter, createWebHistory } from "vue-router";

import LoginPage from '@/views/LoginPage.vue';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/login",
      component: LoginPage
    },
    {
      path: "/",
      component: () => import('@/views/LandingPage.vue'),
    },
    { 
      path: '/:pathMatch(.*)', 
      component: () => import('@/views/FallbackPage.vue'),
    }
  ]
});

export default router;