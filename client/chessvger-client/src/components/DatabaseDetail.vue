<template>

<v-card>
      <v-tabs v-model="tab" bg-color="yellow">
        <v-tab v-for="(t, index) in allTabs" :key="t.name"   @click="activeTab = index"
        >
          {{ t.name }}
          <v-icon v-if="index>2"
          small
          class="ml-2 red-background white--text"
          @click.stop="closeTab(index)"
        >
          mdi-close
        </v-icon>

        </v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="tab">
          <v-window-item v-for="(t, index) in allTabs" :key="t.name">

            <div v-show="tab === 0"> <GamesList :database="database"  @row-clicked="handleRowClickGame" /></div>
            <div v-show="tab === 1"> <GameBrowse :database="database"/></div>
            <div v-show="tab === 2"> <GamePlayers :database="database"/></div>
            <div v-show="tab ===3"> <Game :database="database"/></div>
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
    handleRowClickGame(item,row) {
      console.log("Ligne cliquée :", item.whitePlayer);
      this.allTabs.push({ name: "game "+item.whitePlayer, visible: true });
      this.activeTab = this.allTabs.length - 1;
    },
    closeTab(index) {
      console.log("close "+index);
      this.allTabs.splice(index, 1);
      if (this.activeTab >= this.allTabs.length) {
        this.activeTab = this.allTabs.length - 1;
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
.red-background {
  background-color: red;
  border-radius: 50%; /* Fond rond */
  padding: 4px; /* Ajuster selon la taille */
}
</style>
