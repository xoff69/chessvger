<template>
   <v-data-table
      :headers="headers"
      :items="games"
      :items-per-page="5"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>player gane</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
    </v-data-table>
    games : {{ count }}
</template>

<script>

import axios from "axios";
import { useAuthStore } from "../stores/authStore";
import { sendPostRequest } from '../api/apiService';
import ModalSearchGame from "./ModalSearchGame.vue";

import { sendGetRequest } from '../api/apiService'; 
export default {
 name: "GamesPlayers",
 props: {
     database: {
               type: Object,
               required: true
           }
   },
   data() {
     return {
      games: [],
      count:"",
      headers: [
      { title: "id", value: "id" ,sortable:true},
      { title: "name", value: "name" ,sortable:true},
      { title: "gameCount", value: "gameCount" ,sortable:true},
      ],

     }
   },
   methods: {
    handleRowClick(item,row) {
      // TODO ne pas ouvrir deux fois la meme
      console.log("list "+row.item.whitePlayer);
      this.$emit("row-clicked", row.item);
    },
    async fetchGames() {
      try {

        console.log("fetchGames gamesplayers this.database.id", this.database.id);
       
        const response =  await sendGetRequest("http://localhost:8080/api/gamesplayer/all?databaseId="+this.database.id);

        this.games = response.data.list;
        this.count=response.data.count;
        console.log("fetchGames gamesplayers ", this.games);
      } catch (error) {
        console.error("Erreur lors de la récupération des gamesplayers :", error);
      }},
      
    },
   mounted() {
     console.log("GamesPlayers database reçue:"+ this.database);
     console.table(this.database);
     //gamesplayer
     this.fetchGames();

 },


};
</script>
