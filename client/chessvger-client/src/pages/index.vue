<template>
  <v-app>
    <v-main>
      <v-container>
        <h1>Liste des players</h1>
        <!-- Tableau Vuetify -->
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
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";

export default {
  name: "App",
  data() {
    return {
      // Données de la table
      players: [],
      // En-têtes du tableau
      headers: [
        { text: "Nom", value: "name" },
        { text: "fideId", value: "fideId" },
        { text: "country", value: "country" },
      ],
    };
  },
  methods: {
    // Fonction pour appeler l'API
    async fetchPlayers() {
      try {
        const response = await axios.get("http://localhost:8080/api/allplayers");
        this.players = response.data;
      } catch (error) {
        console.error("Erreur lors de la récupération des utilisateurs :", error);
      }
    },
  },
  mounted() {
    // Appel de l'API dès que le composant est monté
    this.fetchPlayers();
  },
};
</script>

<style>
/* Optionnel : styles pour ajuster la présentation */
</style>
