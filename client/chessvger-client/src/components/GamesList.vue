<!-- src/components/GamesList.vue -->
<template>
  <v-container>
    <h1>Liste des games:</h1>
    <button @click="importGames">Importer games</button>

    <button @click="showModal = true">Ouvrir la modale</button>
    <ModalSearchGame v-if="showModal" :is-visible="showModal" @close="showModal = false" />


    <v-data-table
      :headers="headers"
      :items="games"
      :items-per-page="5"
      class="elevation-1"
      @click:row="handleRowClick"
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
import { sendPostRequest } from '../api/apiService';
import ModalSearchGame from "./ModalSearchGame.vue";
export default {
  components:{
    ModalSearchGame
  },
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
      count:"",
      showModal: false,
      loading:false,
      headers: [
        { text: "whitePlayer", value: "whitePlayer" },
        { text: "blackPlayer", value: "blackPlayer" },
        { text: "event", value: "event" },
      ],
    };
  },
  methods: {
    handleRowClick(item,row) {
      // TODO ne pas ouvrir deux fois la meme
      console.log("list "+row.item.whitePlayer);
      this.$emit("row-clicked", row.item);
    },
    async fetchGames() {
      try {

        const response = await axios.get("http://localhost:8080/api/games/all");


        this.games = response.data.list;
        this.count=response.data.count;
      } catch (error) {
        console.error("Erreur lors de la récupération des games :", error);
      }},
      async importGames() {
        this.loading = true;
        console.log("this.authStore.user.tenantId':", this.authStore.user);
        console.log("this.authStore.user.tenantId':", this.authStore.user.tenantId);


      try {
        const re = await sendPostRequest("http://localhost:8080/api/games/import", this.database.id, this.authStore.user.tenantId);
        this.response=re.data;
      } catch (error) {
        console.error("Erreur lors de la récupération des games :", error);
      }
      finally {
        this.loading = false;
      }
    },

    },
  mounted() {
    this.fetchGames();


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
