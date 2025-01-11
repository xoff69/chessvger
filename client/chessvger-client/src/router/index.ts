import { createRouter, createWebHistory } from "vue-router";
import { useAuthStore } from "../stores/authStore";
import LoginForm from "../components/Login.vue";
import HomePage from "../components/HomePage.vue";
import LandingPage from "../components/LandingPage.vue";
const routes = [
  {
    path: "/",
    name: "home",
    component: HomePage,
    meta: { requiresAuth: true },
  },
  {
    path: "/landingPage",
    name: "landingPage",
    component: LandingPage,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Navigation guard pour vérifier l'authentification
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  // Vérifier si la route nécessite une authentification
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next("/landingPage");
  } else if (to.name === "landingPage" && authStore.isAuthenticated) {
    next("/"); // Si l'utilisateur est connecté, rediriger vers la page d'accueil
  } else {
    next(); // Continuer la navigation
  }
});

export default router;
