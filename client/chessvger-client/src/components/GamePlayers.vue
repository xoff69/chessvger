<template>
  GamesPlayers
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
     return {}
   },
   methods: {
    handleRowClick(item,row) {
      // TODO ne pas ouvrir deux fois la meme
      console.log("list "+row.item.whitePlayer);
      this.$emit("row-clicked", row.item);
    },
    async fetchGames() {
      try {
// TODO databaseId en dur
console.log("fetchGames gamesplayers this.database.id", this.database.id);
       
        const response =  await sendGetRequest("http://localhost:8080/api/gamesplayer/all?databaseId="+this.database.id);

        this.games = response.data.list;
        this.count=response.data.count;
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
