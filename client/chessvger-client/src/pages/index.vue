<template>
  <v-app>
    <AppHeader/>
    <v-card>
      <!-- Onglets principaux -->
      <v-tabs v-model="tab" bg-color="primary">
        <v-tab v-for="(t, index) in allTabs" :key="t.name">
          {{ t.name }}

        </v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="tab">
          <v-window-item v-for="(t, index) in allTabs" :key="t.name">

            <div v-if="tab === 0">
              <DatabasesList  @row-clicked="handleRowClick"  :items="databases"  />
            </div>
            <div v-if="tab === 1">
              <DatabaseDetail :database="selectedDatabase" />
            </div>

          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>
    <AppFooter/>
  </v-app>


</template>

<script lang="ts">
import axios from "axios";
import { ref, computed } from 'vue';
import AppHeader from "../components/AppHeader.vue";
import AppFooter from "../components/AppFooter.vue";
import DatabaseDetail from "../components/DatabaseDetail.vue";

import DatabasesList from '../components/DatabasesList.vue';
import { getUser } from '../services/authService';
export default {
  name: 'ComposantOnglets',
  components: {
     AppHeader,DatabasesList,DatabaseDetail,
    AppFooter,
  },
  data() {
    return {
      databases: [],
      selectedDatabase:null,
      user: null,
       tab :ref(0),
       activeTab:0,
       allTabs : ref([{ name: 'Your databases', visible: true },]),
    }
  },
  created() {
    this.user = getUser();
  },

  mounted() {
    this.fetchDatabases();
  },
  methods: {
    handleRowClick(item,row) {
      console.log("Ligne cliquée :", item.name);
      this.allTabs.push({ name: "Database "+item.name, visible: true });
      this.selectedDatabase=item;

      this.activeTab = this.allTabs.length - 1;
    },
    async fetchDatabases() {
      try {
        const response = await axios.get("http://localhost:8080/api/databases/all");

        this.databases = response.data;
      } catch (error) {
        console.error("Erreur lors de la récupération des databases :", error);
      }},
  },

};
</script>

<style scoped>
/* Ajoutez ici des styles spécifiques à ce composant si nécessaire */
</style>
