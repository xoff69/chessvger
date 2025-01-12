
<template>
  <v-container>
    <h1>Liste des db</h1>
    <v-data-table
      :headers="headers"
      :items="items"
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
    items: Array,
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


    async countDatabases() {
      try {
        const response = await axios.get("http://localhost:8080/api/databases/count");
        this.count = response.data;
      } catch (error) {
        console.error("Error count databases :", error);
      }
    },
    },
  mounted() {
    this.countDatabases();
  },
};
</script>

<style scoped>
/* Ajoutez ici des styles spécifiques à ce composant si nécessaire */
</style>
