<template>
  <v-app>
    <AppHeader/>
    <v-card>
      <!-- Onglets principaux -->
      <v-tabs v-model="tab" bg-color="primary">
        <v-tab v-for="(t, index) in visibleTabs" :key="t.name">
          {{ t.name }}
          <v-icon x-small class="ml-2" @click="cacherOnglet(t)">mdi-close</v-icon>
        </v-tab>
      </v-tabs>

      <v-card-text>
        <!-- Contenu des onglets -->
        <v-window v-model="tab">
          <v-window-item v-for="(t, index) in visibleTabs" :key="t.name">
            <!-- Onglet 1 -->
            <div v-if="tab === 0">Contenu du premier onglet</div>
            <div v-if="tab === 1">
              <GamesList />
            </div>

          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>

    <v-btn @click="selectionnerPremierOnglet">Select first tab</v-btn> <AppFooter />
  </v-app>


</template>

<script>

import AppHeader from "../components/AppHeader.vue";
import AppFooter from "../components/AppFooter.vue";
import { ref, computed } from 'vue';
import GamesList from '../components/GamesList.vue';
import { getUser } from '../services/authService';
export default {
  name: 'ComposantOnglets',
  components: {
   GamesList,  AppHeader,
    AppFooter,
  },
  data() {
    return {
      user: null,
    };
  },
  created() {
    this.user = getUser();
  },
  setup() {
    const tab = ref(0);  // Onglet principal
    const subTab = ref(0); // Sous-onglet pour Admin
    const allTabs = ref([
      { name: 'Liste des bd', visible: true },
      { name: 'Games', visible: true },
    ]);

    const visibleTabs = computed(() => allTabs.value.filter((onglet) => onglet.visible));

    function cacherOnglet(onglet) {
      onglet.visible = false;
      // Si le dernier onglet est fermé, on sélectionne le premier onglet
      if (onglet.name === 'Admin') {
        tab.value = 0;
      }
    }

    function selectionnerPremierOnglet() {
      tab.value = 0;
    }

    return {
      tab,
      subTab,  // Variable pour suivre l'onglet sélectionné dans les sous-onglets Admin
      allTabs,
      visibleTabs,
      cacherOnglet,
      selectionnerPremierOnglet,
    };
  },
};
</script>

<style scoped>
/* Ajoutez ici des styles spécifiques à ce composant si nécessaire */
</style>
