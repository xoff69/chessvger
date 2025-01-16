<template>
  <v-app>
    <AppHeader/>
    <v-card>
      <div> <!-- pour gagner de l espace -->
    <p>Premier Élément</p>
    <br />
    <p>Deuxième Élément</p>
  </div>

      <!-- Onglets principaux -->
      <v-tabs v-model="activeTab" bg-color="primary">
        <v-tab v-for="(t, index) in allTabs" :key="t.name">
          {{ t.name }}
          <v-icon
          small
          class="ml-2 red-background white--text"
          @click.stop="closeTab(index)"
        >
          mdi-close
        </v-icon>

        </v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="activeTab">
          <v-window-item v-for="(t, index) in allTabs" :key="t.name">

            <div v-if="activeTab === 0">
              <DatabasesList  @row-clicked="handleRowClick"    />
            </div>
            <div v-if="activeTab === 1">
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
import UserInfo from "./UserInfo.vue";

import DatabasesList from '../components/DatabasesList.vue';
import { getUser } from '../services/authService';
export default {
  name: 'ComposantOnglets',
  components: {
     AppHeader,DatabasesList,DatabaseDetail,
    AppFooter, UserInfo,
  },
  data() {
    return {

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
  },
  methods: {
    handleRowClick(item,row) {
      console.log("Ligne cliquée :", item.name);
      this.allTabs.push({ name: "Database "+item.name, visible: true });
      this.selectedDatabase=item;

      this.activeTab = this.allTabs.length - 1;
    },

  },

};
</script>

<style scoped>
/* Optionnel : Ajouter du style à l'icône */
.v-icon {
  cursor: pointer;
  color: red;
}
.red-background {
  background-color: red;
  border-radius: 50%; /* Fond rond */
  padding: 4px; /* Ajuster selon la taille */
}
</style>
