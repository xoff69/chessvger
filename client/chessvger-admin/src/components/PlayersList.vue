<!-- src/components/PlayersList.vue -->
<template>


  <v-container>
    <v-btn
    href="http://localhost:16686"
    target="_blank"
    icon
>
    <v-icon>window</v-icon> Import players
</v-btn>


    <h1>Players</h1>
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
        const response = await axios.get("http://localhost:8082/api/admin/players/all");
        this.players = response.data;
      } catch (error) {
        console.error("Error all players :", error);
      }
    },
    async countPlayers() {
      try {
        const response = await axios.get("http://localhost:8082/api/admin/players/count");
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

