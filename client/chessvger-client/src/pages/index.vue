<template>
  <v-app>
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
            <div v-if="tab === 2">
              <PlayersList />
            </div>

            <!-- Onglet 3 (Admin) avec sous-onglets -->
            <div v-if="tab === 3">
              <v-tabs v-model="subTab" align-tabs="start" fixed-tabs>
                <v-tab>Gérer les utilisateurs</v-tab>
                <v-tab>Paramètres</v-tab>
                <v-tab>Logs</v-tab>
              </v-tabs>

              <v-tab-item v-if="subTab === 0">
                <v-card flat>
                  <v-card-text>Gestion des utilisateurs</v-card-text>
                </v-card>
              </v-tab-item>
              <v-tab-item v-if="subTab === 1">
                <v-card flat>
                  <v-card-text>Paramètres de l'application</v-card-text>
                </v-card>
              </v-tab-item>
              <v-tab-item v-if="subTab === 2">
                <v-card flat>
                  <v-card-text>Affichage des logs</v-card-text>

    <WebSocketChat />

                </v-card>
              </v-tab-item>
            </div>
          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>

    <v-btn @click="selectionnerPremierOnglet">Select first tab</v-btn>
  </v-app>


</template>

<script>
import { ref, computed } from 'vue';
import PlayersList from '../components/PlayersList.vue';
import GamesList from '../components/GamesList.vue';
import WebSocketChat from "../components/WebSocketChat.vue";

export default {
  name: 'ComposantOnglets',
  components: {
    PlayersList,GamesList, WebSocketChat
  },
  setup() {
    const tab = ref(0);  // Onglet principal
    const subTab = ref(0); // Sous-onglet pour Admin
    const allTabs = ref([
      { name: 'Liste des bd', visible: true },
      { name: 'Games', visible: true },
      { name: 'Players', visible: true },
      { name: 'Admin', visible: true },
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
