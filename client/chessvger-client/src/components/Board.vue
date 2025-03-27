<script setup>
import { TheChessboard } from 'vue3-chessboard';
import 'vue3-chessboard/style.css';
</script>
<template>
<div class="chessboard-container">
  <TheChessboard class="responsive-board" />
</div>
</template>

<style scoped>
.chessboard-container {
  width: 33.33vw; /* 1/3 de l'écran */
  height: 33.33vw; /* Garde un aspect carré */
}
.responsive-board {
  width: 100%;
  height: 100%;
}
</style>