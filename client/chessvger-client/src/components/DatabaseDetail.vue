<template>

<v-card>
      <v-tabs v-model="tab" bg-color="yellow">
        <v-tab v-for="(t, index) in allTabs" :key="t.name">
          {{ t.name }}
          <v-icon x-small class="ml-2" @click="cacherOnglet(t)">mdi-close</v-icon>
        </v-tab>
      </v-tabs>

      <v-card-text>
        <!-- Contenu des onglets -->
        <v-window v-model="tab">
          <v-window-item v-for="(t, index) in allTabs" :key="t.name">

            <div v-if="tab === 0"> <GamesList/></div>
            <div v-if="tab === 1"> <GameBrowse/></div>
            <div v-if="tab === 2"> <GamePlayers/></div>

          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>


</template>

<script>
import GamesList from "../components/GamesList.vue";
import GameBrowse from "../components/GameBrowse.vue";
import GamePlayers from "../components/GamePlayers.vue";

export default {
  name: "DatabaseDetail",
  components: {
    GamesList,GameBrowse,GamePlayers
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

      ]),
      };
    },
    mounted() {
      console.log("database reçu:"+ this.database);
        console.log("database reçu:"+ this.database.name );
  },

};
</script>
