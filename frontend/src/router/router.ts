import { createRouter, createWebHistory } from "vue-router";
import { getToken } from '@/helpers/authorization';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/login",
      component: () => import('@/views/LoginPage.vue')
    },
    {
      path: "/",
      component: () => import('@/views/LandingPage.vue'),
      meta: { requiresAuth: true }
    },
    { 
      path: '/:pathMatch(.*)', 
      component: () => import('@/views/FallbackPage.vue'),
      meta: { requiresAuth: true }
    }
  ]
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !getToken()) {
    next('/login');
  } else {
    next();
  }
});

export default router;
