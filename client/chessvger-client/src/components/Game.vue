<template>
  <div>
    <h1>Chessboard Component</h1>
    <Chessboard :position="'start'" :draggable="true" />
  </div>
  <p>game reçu : {{ game }}</p>



</template>

<script>
import Chessboard from "../components/Board.vue";
import { sendGetRequest } from '../api/apiService'; 
export default {
  name: "App",
  components: {
    Chessboard
  },
  props: {
     database: {
               type: Object,
               required: true
           },
           gameId: {
                type: Object,
                required: true
            },
   },
   data() {
     return {
      game:null,
     }
   },
   methods: {
    async fetchGame(id) {
      try {
        console.log("game "+id);
        const  response = await sendGetRequest("http://localhost:8080/api/games/findById?id="+id+"&databaseId="+this.database.id);

        console.log("response.date="+response.data);
        console.log(JSON.stringify(response.data));

        this.game=response.data;
        console.log(this.game.id);
      } catch (error) {
        console.error("Erreur lors de la récupération du game :", error);
      }},

  },
   mounted() {
     this.fetchGame(this.gameId);
 },


};
</script>

