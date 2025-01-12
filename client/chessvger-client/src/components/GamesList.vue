<!-- src/components/GamesList.vue -->
<template>
  <v-container>
    <h1>Liste des games</h1> <button @click="importGames">Importer games</button>
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
import { useAuthStore } from "../stores/authStore";
import { sendPostRequest } from '../api/apiService'; // Importez votre méthode


export default {
  name: "GamesList",
  props: {
     database: {
               type: Object,
               required: true
           }
   },
  data() {
    return {
       authStore : useAuthStore(),
      games: [],
      count:0,
      loading:false,
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
      async importGames() {
        this.loading = true;



      try {
        this.response = await sendPostRequest("http://localhost:8080/api/games/import", this.database.id, this.authStore.user.id);

      } catch (error) {
        console.error("Erreur lors de la récupération des games :", error);
      }
      finally {
        this.loading = false;
      }
    },
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


    console.log("games list database reçue:"+ this.database);
  },
};
</script>
<style>
button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
