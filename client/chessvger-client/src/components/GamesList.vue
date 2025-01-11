<!-- src/components/GamesList.vue -->
<template>
  <v-container>
    <h1>Liste des games</h1>
    <v-data-table
      :headers="headers"
      :items="games"
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
    games : {{ count }}
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "GamesList",
  data() {
    return {
      games: [],
      count:0,
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
        const response = await axios.get("http://localhost:8080/api/games/all");
        this.games = response.data;
      } catch (error) {
        console.error("Erreur lors de la récupération des games :", error);
      }},

    async countGames() {
      try {
        const response = await axios.get("http://localhost:8080/api/games/count");
        this.count = response.data;
      } catch (error) {
        console.error("Error count games :", error);
      }
    },
    },
  mounted() {
    this.fetchGames();
    this.countGames();
  },
};
</script>
