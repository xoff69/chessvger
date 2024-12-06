<template>
  <v-app>
    <v-card>
      <v-tabs v-model="tab" bg-color="primary">
        <v-tab v-for="(t, index) in visibleTabs" :key="t">
          {{ t.name }}
          <v-icon x-small class="ml-2" @click="hideTab(t)">mdi-close</v-icon>
        </v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="tab">
          <v-window-item v-for="(t, index) in visibleTabs" :key="t">
            {{ t.name }}
          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>

    <v-btn @click="selectedTabFirstTab">Select first tab</v-btn>

    <v-main>
      <v-container>
        <h1>Liste des players</h1>
        <v-data-table
          :headers="headers"
          :items="players"
          :items-per-page="5"
          class="elevation-1"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>players</v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>
          </template>
        </v-data-table>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";
import { ref, computed } from 'vue';
export default {
  name: "App",
  name: 'ComposantOnglets',
  setup() {
    const tab = ref(0);
    const allTabs = ref([
      { name: 'Onglet1', visible: true },
      { name: 'Onglet2', visible: true },
      { name: 'Onglet3', visible: true },
    ]);

    const visibleTabs = computed(() => allTabs.value.filter((onglet) => onglet.visible));

    function cacherOnglet(onglet: { name: string; visible: boolean }) {
      onglet.visible = false;
      // Si le dernier onglet est fermé, on sélectionne le premier onglet
      if (onglet.name === 'Onglet3') {
        tab.value = 0;
      }
    }

    function selectionnerPremierOnglet() {
      tab.value = 0;
    }

    return {
      tab,
      allTabs,
      visibleTabs,
      cacherOnglet,
      selectionnerPremierOnglet,
    };
  },
  data() {
    return {
      players: [],
      headers: [
        { text: "Nom", value: "name" },
        { text: "fideId", value: "fideId" },
        { text: "country", value: "country" },
      ],
    };
  },
  methods: {
    async fetchPlayers() {
      try {
        const response = await axios.get("http://localhost:8080/api/allplayers");
        this.players = response.data;
      } catch (error) {
        console.error("Erreur lors de la récupération des utilisateurs :", error);
      }
    },
  },
  mounted() {
    // Appel de l'API dès que le composant est monté
    this.fetchPlayers();
  },
};
</script>

<style>
/* Optionnel : styles pour ajuster la présentation */
</style>
