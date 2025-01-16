
<template>
  <v-container>
    <h1>Liste des db</h1>
    <v-data-table
      :headers="headers"
      :items="databases"
      :items-per-page="5"
      class="elevation-1"
      @click:row="handleRowClick"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>databases</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
    </v-data-table>
    databases : {{ count }}
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "DatabasesList",
  props: {
  },

  data() {
    return {
      databases: [],
      count:0,
      headers: [
        { text: "name", value: "name" },
        { text: "description", value: "description" },
      ],

    };
  },

  methods: {
    handleRowClick(item,row) {
// TODO ne pas ouvrir deux fois la meme
      console.log("list "+row.item.name);
      this.$emit("row-clicked", row.item);
    },
    async fetchDatabases() {
      try {
        const response = await axios.get("http://localhost:8080/api/databases/all");

        this.databases = response.data;

        const customHeader = response.headers["X-Total-Count"];
        console.log("Valeur de 'X-Total-Count':", customHeader);
        this.count=customHeader;
      } catch (error) {
        console.error("Erreur lors de la récupération des databases :", error);
      }},


    },
  mounted() {
    this.fetchDatabases();
  },
};
</script>

<style scoped>
/* Ajoutez ici des styles spécifiques à ce composant si nécessaire */
</style>
