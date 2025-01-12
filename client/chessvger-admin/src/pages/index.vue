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

            <div v-if="tab === 0">
              <UsersList/>
            </div>

            <div v-if="tab === 1">
              <PlayersList />
            </div>
            <div v-if="tab === 2">
              <FeatureFlags />
            </div>
            <div v-if="tab === 3">
              <Stats />
            </div>
          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>

    <v-btn @click="selectionnerPremierOnglet">Select first tab</v-btn>
    <AppFooter />
  </v-app>


</template>

<script>
import { ref, computed } from 'vue';
import PlayersList from '../components/PlayersList.vue';
import Stats from '../components/Stats.vue';
import UsersList from '../components/UsersList.vue';
import FeatureFlags from '../components/FeatureFlags.vue';
import { getUser } from '../services/authService';
import AppHeader from "../components/AppHeader.vue";
import AppFooter from "../components/AppFooter.vue";

export default {
  name: 'ComposantOnglets',
  components: {
    PlayersList, UsersList, AppHeader,Stats,
    AppFooter,FeatureFlags
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
    const tab = ref(0);
    const allTabs = ref([
      { name: 'Users', visible: true },
      { name: 'Players', visible: true },
      { name: 'FeatureFlags', visible: true },
      { name: 'Stats', visible: true },
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
      visibleTabs,
      cacherOnglet,
      selectionnerPremierOnglet,
    };
  },
};
</script>

