<template>
  <div>
    <h1>Chessboard Component</h1>
    <Chessboard :position="'start'" :draggable="true" />
  </div>


  <Chat/>

</template>

<script>
import Chessboard from "../components/Board.vue";
import Chat from "./Chat.vue";
export default {
  name: "App",
  components: {
    Chessboard,Chat
  },
  props: {
     database: {
               type: Object,
               required: true
           }
   },
   data() {
     return {}
   },
   mounted() {
     console.log("Game:"+ this.database);
 },


};
</script>

