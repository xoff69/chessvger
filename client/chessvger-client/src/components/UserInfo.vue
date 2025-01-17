<template>
    <div v-if="authStore.isAuthenticated">
      <p>salut, tu es connecte, {{ authStore.user.name }}!  tenantId:  {{ authStore.user.tenantId }}!</p>
      <button @click="authStore.logout">Logout</button>
    </div>
  </template>

  <script>
  import { useAuthStore } from "../stores/authStore";

  export default {
    setup() {
      const authStore = useAuthStore();
      return { authStore };
    }
  };
  </script>
