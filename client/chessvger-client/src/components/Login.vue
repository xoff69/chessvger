<template>
    <div>
      <h2>Login:tu n es pas connecte </h2>
      <form @submit.prevent="handleLogin">
        <input v-model="email"  placeholder="Email" required />
        <input v-model="password" type="password" placeholder="Password" required />
        <button type="submit">Login</button>
      </form>
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    </div>
  </template>

  <script>
  import { useAuthStore } from "../stores/authStore";

  export default {
    data() {
      return {
        email: "",
        password: "",
        errorMessage: ""
      };
    },
    setup() {
      const authStore = useAuthStore();
      return { authStore };
    },
    methods: {
      async handleLogin() {
        try {
          await this.authStore.login(this.email, this.password);
          this.errorMessage = "";
          this.$router.push("/"); // Redirige après la connexion
        } catch (error) {
          this.errorMessage = "Invalid email or password";
        }
      }
    }
  };
  </script>
