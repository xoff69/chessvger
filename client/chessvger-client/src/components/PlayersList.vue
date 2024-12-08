<!-- src/components/PlayersList.vue -->
<template>
  <v-container>
    <h1>Liste des players</h1>
    <v-data-table
      :headers="headers"
      :items="players"
      :items-per-page="5"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>players</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "PlayersList",
  data() {
    return {
      players: [],
      headers: [
        { text: "Nom", value: "name" },
        { text: "fideId", value: "fideId" },
        { text: "country", value: "country" },
      ],
    };
  },
  methods: {
    async fetchPlayers() {
      try {
        const response = await axios.get("http://localhost:8080/api/allplayers");
        this.players = response.data;
      } catch (error) {
        console.error("Erreur lors de la récupération des joueurs :", error);
      }
    },
  },
  mounted() {
    // Appel de l'API dès que le composant est monté
    this.fetchPlayers();
  },
};
</script>

<style scoped>
/* Ajoutez ici des styles spécifiques à ce composant si nécessaire */
</style>
