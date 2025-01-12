<template>

<v-card>
      <v-tabs v-model="tab" bg-color="yellow">
        <v-tab v-for="(t, index) in allTabs" :key="t.name"   @click="activeTab = index"
        >
          {{ t.name }}
          <v-icon
          small
          class="ml-2"
          @click.stop="closeTab(index)"
        >
          mdi-close
        </v-icon>

        </v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="tab">
          <v-window-item v-for="(t, index) in allTabs" :key="t.name">

            <div v-if="tab === 0"> <GamesList :database="database" /></div>
            <div v-if="tab === 1"> <GameBrowse :database="database"/></div>
            <div v-if="tab === 2"> <GamePlayers :database="database"/></div>
            <div v-if="tab ===3"> <Game :database="database"/></div>
          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>


</template>

<script>
import GamesList from "../components/GamesList.vue";
import GameBrowse from "../components/GameBrowse.vue";
import GamePlayers from "../components/GamePlayers.vue";
import Game from "../components/Game.vue";

export default {
  name: "DatabaseDetail",
  components: {
    GamesList,GameBrowse,GamePlayers,Game
  },
  props: {
      database: {
                type: Object,
                required: true
            }
    },
    data() {
      return {
        tab :ref(0),
       activeTab:0,
       allTabs : ref([
        { name: 'Games', visible: true },
        { name: 'Browse', visible: true },
        { name: 'Players', visible: true },
        { name: 'Game', visible: true },
      ]),
      };
    },
    methods: {
    closeTab(index) {
      console.log("close "+index);
      this.allTabs.splice(index, 1); // Supprimer l'onglet
      if (this.activeTab >= this.allTabs.length) {
        this.activeTab = this.allTabs.length - 1; // Ajuster l'onglet actif
      }
    },
  },
    mounted() {
      console.log("database reçue:"+ this.database);
  },

};
</script>
<style scoped>
/* Optionnel : Ajouter du style à l'icône */
.v-icon {
  cursor: pointer;
  color: red;
}
</style>
