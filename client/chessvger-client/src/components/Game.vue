<template>
  <div>
    <h1>Chessboard Component</h1>
    <Chessboard :position="'start'" :draggable="true" />
  </div>
</template>

<script>
import Chessboard from "../components/Board.vue";

export default {
  name: "App",
  components: {
    Chessboard,
  },
};
</script>
