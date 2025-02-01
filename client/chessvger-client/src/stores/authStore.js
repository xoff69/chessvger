// src/stores/authStore.js


import { defineStore } from "pinia";
import { login, getUser } from "../api/user.js";
export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: localStorage.getItem("user") || null,
    token: localStorage.getItem("token") || null, // Récupérer le token au démarrage
    isAuthenticated: !!localStorage.getItem("token"), // Vérifier l'authentification
  }),

  actions: {
    async login(email, password) {
      try {
        const response = await login(email, password);
        this.user = { id: response.id, name: response.name, email: response.email ,tenantId: response.tenantId};
        this.token = response.token;
        this.isAuthenticated = true;

        // Stocker les informations dans localStorage
        localStorage.setItem("token", this.token);
        localStorage.setItem("user", JSON.stringify(this.user)); // Sauvegarder l'utilisateur
        console.log("fetch "+this.user);
      } catch (error) {
        console.error("Login failed:", error.message);
        throw error;
      }
    },

    async fetchUser() {
      try {
        const token = localStorage.getItem("token");
        if (!token) return;

        // Recharger les détails de l'utilisateur depuis l'API
        const response = await getUser(token);
        this.user = { id: response.id, name: response.name, email: response.email ,tenantId: response.tenantId};
        this.token = token;
        this.isAuthenticated = true;

        console.log("fetch "+this.user);
        localStorage.setItem("user", JSON.stringify(this.user));
      } catch (error) {
        console.error("Failed to fetch user:", error.message);
        this.logout();
      }
    },

    logout(router) {
      this.user = null;
      this.token = null;
      this.isAuthenticated = false;

      // Supprimer les données du localStorage
      localStorage.removeItem("token");
      localStorage.removeItem("user");
      console.log(router)
      const allRoutes = router.getRoutes();

    // Afficher dans la console
      console.log("show:  Liste des routes :", allRoutes);
      // Rediriger vers la LandingPage
      router.push({ name: "landingPage" });
    },
    restoreState() {
      const storedUser = localStorage.getItem("user");
      const storedToken = localStorage.getItem("token");

      if (storedUser && storedToken) {
        this.user = JSON.parse(storedUser);
        this.token = storedToken;
        this.isAuthenticated = true;
      }
    },
    loadFromStorage() {
      // Recharger les données sauvegardées depuis localStorage
      const user = localStorage.getItem("user");
      const token = localStorage.getItem("token");
      console.log("loadFromStorage "+this.user);
      if (token && user) {
        this.token = token;
        this.user = JSON.parse(user);
        this.isAuthenticated = true;
      }
    },
  },
});
