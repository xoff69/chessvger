
import { defineStore } from "pinia";
import { login, getUser } from "../api/user.js";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: null,
    token: null,
    isAuthenticated: false
  }),
  actions: {
    async login(email, password) {
      try {
        const response = await login(email, password);
        this.user = { id: response.id, name: response.name, email: response.email };
        this.token = response.token;
        this.isAuthenticated = true;

        // Stocker le token dans localStorage
        localStorage.setItem("token", this.token);
      } catch (error) {
        console.error("Login failed:", error.message);
        throw error;
      }
    },
    async fetchUser() {
      try {
        const token = localStorage.getItem("token");
        if (!token) return;

        const response = await getUser(token);
        this.user = { id: response.id, name: response.name, email: response.email };
        this.token = token;
        this.isAuthenticated = true;
      } catch (error) {
        console.error("Failed to fetch user:", error.message);
        this.logout();
      }
    },
    logout() {
      this.user = null;
      this.token = null;
      this.isAuthenticated = false;

      // Supprimer le token de localStorage
      localStorage.removeItem("token");
    }
  }
});
