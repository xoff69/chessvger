<template>
  <div>
    <h1>Chessboard Component</h1>
    <Chessboard :position="'start'" :draggable="true" />
  </div>
  <p>Message reçu : {{ game }}</p>


</template>

<script>
import Chessboard from "../components/Board.vue";
import axios from "axios";
export default {
  name: "App",
  components: {
    Chessboard
  },
  props: {
     database: {
               type: Object,
               required: true
           }
   },
   data() {
     return {
      game:null,
     }
   },
   methods: {
    async fetchGame(id) {
      try {
        const response = await axios.get("http://localhost:8080/api/games/findById?id="+id);
        console.log(response.data);
        this.game=response.data;
        console.log(this.game.id);
      } catch (error) {
        console.error("Erreur lors de la récupération du game :", error);
      }},

  },
   mounted() {
     console.log("Game:"+ this.database);
     this.fetchGame(233);
 },


};
</script>

