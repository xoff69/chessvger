<!-- src/components/PlayersList.vue -->
<template>


  <v-container>
    <v-btn
    href="http://localhost:8082/apiadmin/players/import"

    icon
>
    <v-icon>window</v-icon> Import players
</v-btn>

<button @click="showModal = true">Ouvrir la modale</button>
<ModalSearchPlayer v-if="showModal" :is-visible="showModal" @close="showModal = false" />

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
import ModalSearchPlayer from "./ModalSearchPlayer.vue";

export default {
  name: "PlayersList",
  components: { ModalSearchPlayer },

  data() {
    return {
      players: [],
      count: 0,
      showDialog: false,
      searchCriteria: {},
      filteredPlayers: [],
      showModal: false,
      headers: [
        { title: "Nom", value: "name" },
        { title: "fideId", value: "fideId" },
        { title: "country", value: "country" },
      ],
    };
  },
  methods: {
    async fetchPlayers() {
      try {
        const response = await axios.get("http://localhost:8082/apiadmin/players/all");
        this.players = response.data.list;
        this.count = response.data.count;
      } catch (error) {
        console.error("Error all players :", error);
      }
    },
    handleSearch(searchData) {
      console.log("Recherche lancée avec :", searchData);
      // Ici, tu peux appeler une API ou filtrer une liste de joueurs
    },
    handleReset() {
      console.log("Formulaire réinitialisé !");
    },
    performSearch(criteria) {
      this.searchCriteria = criteria;
      const { name, country, fide_id, title } = criteria;

      this.filteredPlayers = this.players.filter((player) => {
        return (
          (!name || player.name.toLowerCase().includes(name.toLowerCase())) &&
          (!country || player.country.toLowerCase().includes(country.toLowerCase())) &&
          (!fide_id || player.fide_id.includes(fide_id)) &&
          (!title || player.title === title)
        );
      });
    },
    resetSearch() {
      this.searchCriteria = {};
      this.filteredPlayers = [...this.players];
    },

  },
  mounted() {
    this.fetchPlayers();
    this.filteredPlayers = [...this.players];

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
