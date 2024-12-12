<!-- src/components/GamesList.vue -->
<template>
  <v-container>
    <h1>Liste des games</h1>
    <v-data-table
      :headers="headers"
      :items="players"
      :items-per-page="5"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>games</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "GamesList",
  data() {
    return {
      games: [],
      headers: [
        { text: "whitePlayer", value: "whitePlayer" },
        { text: "blackPlayer", value: "blackPlayer" },
        { text: "event", value: "event" },
      ],
    };
  },
  methods: {
    async fetchGames() {
      try {
        const response = await axios.get("http://localhost:8080/api/allgames");
        this.games = response.data;
      } catch (error) {
        console.error("Erreur lors de la récupération des games :", error);
      }
    },
  },
  mounted() {
    // Appel de l'API dès que le composant est monté
    this.fetchGames();
  },
};
</script>

<style scoped>
/* Ajoutez ici des styles spécifiques à ce composant si nécessaire */
</style>
