<!-- src/components/PlayersList.vue -->
<template>


  <v-container>
    <v-btn
    href="http://localhost:8082/apiadmin/players/import"
    target="_blank"
    icon
>
    <v-icon>window</v-icon> Import players
</v-btn>


    <h1>Players</h1>
    <v-data-table
      :items="players"
      :items-per-page="5"  class="custom-table"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>players</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
    </v-data-table>

    players : {{ count }}
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "PlayersList",
  data() {
    return {
      players: [],
      count: 0,
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
        const response = await axios.get("http://localhost:8082/apiadmin/players/all");
        this.players = response.data;
      } catch (error) {
        console.error("Error all players :", error);
      }
    },
    async countPlayers() {
      try {
        const response = await axios.get("http://localhost:8082/apiadmin/players/count");
        this.count = response.data;
      } catch (error) {
        console.error("Error count player :", error);
      }
    },
  },
  mounted() {
    this.fetchPlayers();
    this.countPlayers();
  },
};
</script>

<style scoped>
.custom-table .v-data-table__thead {
  background-color: #4caf50; /* Couleur de fond de l'en-tête */
  color: white; /* Couleur du texte */
}
.custom-table .v-data-table__thead th {
  font-weight: bold; /* Texte en gras */
  padding: 10px; /* Espacement */
}
.custom-table .v-data-table__tbody {
  background-color: #f9f9f9; /* Couleur de fond du corps */
}
</style>
