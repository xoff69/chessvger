<template>
  <div>
    <h2>Browse</h2>

    <div>
      <label for="movesInput">Previous Moves (format: e4#c5#c3):</label>
      <input
        id="movesInput"
        v-model="previousMoves"
        placeholder="Entrez les coups"
      />
      <button @click="fetchMoves">Rechercher</button>
    </div>

    <div v-if="loading">Chargement...</div>

    <div v-else>
      <div v-if="moves.length === 0">Aucun coup trouvé.</div>
<ul v-else-if="moves.length > 0">
      <li v-for="(move, index) in moves" :key="index">
        <strong>Coup :</strong> {{ move.coup }}<br />
        <strong>Stats :</strong> Blancs: {{ move.blanc }}, Noirs: {{ move.noir }}, Nuls: {{ move.nul }}<br />
        <strong>Séquence :</strong> {{ move.movesStart }}<br />
        <hr />
      </li>
    </ul>

    </div>
  </div>
</template>

<script>

import { sendGetRequest } from '../api/apiService';
export default {
  name: "GameBrowse",
  props: {
    database: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      previousMoves: "e4#c5#c3", // valeur par défaut
      moves: [],
      loading: false
    };
  },
  methods: {
    async fetchMoves() {
  if (!this.previousMoves || !this.database?.id) return;

  this.loading = true;

  const url = `http://localhost:8080/api/browse/all?databaseId=${this.database.id}&previousMoves=${encodeURIComponent(this.previousMoves)}`;
      console.log("fetchMoves", url);
  try {
    const data = await sendGetRequest(url);
    this.moves = data.data;


  } catch (error) {
    console.error("Erreur:", error);
    this.moves = [];
  } finally {
    this.loading = false;
  }
}

  },
  mounted() {
    this.fetchMoves(); // appel initial
  }
};
</script>
