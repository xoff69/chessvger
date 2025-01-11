<template>
  <v-app><!--
    <header>

      <h1>My Application</h1>
      <nav>
        <router-link to="/">Home</router-link>
        <router-link to="/login">Login</router-link>
      </nav>
    </header>-->
    <router-view />
  </v-app>
</template>

<script>
import router from "./router";
import { useAuthStore } from "./stores/authStore.js";
import LoginForm from "./components/Login.vue";
import UserInfo from "./components/UserInfo.vue";
export default {
  setup() {
    const authStore = useAuthStore();
    authStore.fetchUser(); // Récupérer les données utilisateur au chargement

    return { authStore };
  },
  components: {
    LoginForm,
    UserInfo
  }
};
</script>
