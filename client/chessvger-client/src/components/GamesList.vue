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

import { sendGetRequest } from '../api/apiService'; 
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
      { title: "event", value: "event" ,sortable:true},
      { title: "site", value: "site" ,sortable:true},
        { title: "whitePlayer", value: "whitePlayer" ,sortable:true},
        { title: "blackPlayer", value: "blackPlayer" },
        { title: "result", value: "result" },
      ],
    };
  },
  methods: {
    handleRowClick(item,row) {
      // TODO ne pas ouvrir deux fois la meme
      console.log("list game j emets "+row.item.whitePlayer);
      this.$emit("row-clicked", row.item);
    },
    async fetchGames() {
      try {
        const response =  await sendGetRequest("http://localhost:8080/api/games/all?databaseId="+this.database.id);
        console.log("fetchGames", this.database.id);

        this.games = response.data.list;
        this.count=response.data.count;
      } catch (error) {
        console.error("Erreur lors de la récupération des games :", error);
      }},
      // importGames 
      async importGames() {
        this.loading = true;
        console.log("this.authStore.user':", this.authStore.user);
        console.log("this.authStore.user.tenantId':", this.authStore.user.tenantId);
        console.log("database':", this.database.id);

      try {
        const re = await sendPostRequest("http://localhost:8080/api/games/import", this.database.id);
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
