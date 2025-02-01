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
   /* "id": 1,
            "event": "Vinkoal Tourna",
            "site": "Vinkovci CRO",
            "partieAnalysee": false,
            "date": "1995-01-01",
            "eventDate": null,
            "round": "10",
            "result": "1-0",
            "whitePlayer": "Rosandic, Denis",
            "blackPlayer": "Hodak, S",
            "whiteTitle": null,
            "blackTitle": null,
            "whiteElo": 0,
            "blackElo": 0,
            "eco": "D02",
            "opening": null,
            "whiteFideId": 0,
            "blackFideId": 0,
            "nbcoups": 0,
            "lastPosition": 0,
            "informationsFaitDeJeu": 0,
            "lastUpdate": 0,
            "firstMove": "d4",
            "moves": "1. d4 d5 2. Nf3 e6 3. c3 Nf6 4. Bg5 Be7 5. Nbd2 O-O 6. e3 b6 7. Ne5 h6  8. Bh4 Ne4 9. Bxe7 Qxe7 10. Bd3 Nxd2 11. Qxd2 Nd7 12. f4 Nxe5 13. fxe5  a5 14. Qe2 f6 15. O-O fxe5 16. Rxf8 Qxf8 17. Rf1 Qe7 18. dxe5 Bd7  19. Qf3 c5 20. Qf4 c4 21. Bb1 Be8 22. Qf2 Bf7 23. Qc2 g6 24. Rf6 Rf8  25. Qf2 Be8 26. Bxg6 Bxg6 27. Rxg6 Kh7 28. Qc2 Rf5 29. Rf6 Kg8 30. Rxf5  exf5 31. Qxf5 Qc5 32. Qg6 Kh8 33. Qxh6 Kg8 34. e6 ",
            "interet": 0,
            "theorique": false,
            "favori": false,
            "new": true,
            "deleted": false
   */
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
        const re = await sendPostRequest("http://localhost:8080/api/games/import", this.database.id, this.authStore.user.id);
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
