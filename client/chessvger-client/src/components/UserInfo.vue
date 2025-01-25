<template>
  <div v-if="authStore.isAuthenticated">
    <p>Salut, tu es connecté, {{ authStore.user.name }}!</p>
    <button @click="handleLogout">Logout</button>
  </div>
</template>

<script>
import { useAuthStore } from "../stores/authStore";
import { useRouter } from "vue-router";

export default {
  setup() {
    const authStore = useAuthStore(); // Pinia store
    const router = useRouter(); // Vue Router instance
    authStore.restoreState();
    const handleLogout = () => {
      authStore.logout(router); // Passe le router au logout
    };

    return {
      authStore,
      handleLogout,
    };
  },
};
</script>