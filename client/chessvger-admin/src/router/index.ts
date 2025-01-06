import { createRouter, createWebHistory } from 'vue-router';
import Index from '../pages/index.vue'; // Assurez-vous que le chemin est correct

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Index, // Page `index.vue` affichée par défaut
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
