<template>
  <v-footer app color="primary" dark>
    <v-row justify="center">
      <Chat/>
      <span class="white--text">&copy; 2025 App client chessvger</span>
    </v-row>
  </v-footer>

</template>

<script>
import Chat from "./Chat.vue";

export default {
  name: "AppFooter",

  components: {
    Chat
  },
};
</script>
