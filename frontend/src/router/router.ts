import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/login",
      component: () => import('@/views/LoginPage.vue'),
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
