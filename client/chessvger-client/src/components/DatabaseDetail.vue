<template>

<v-card>
      <v-tabs v-model="tab" bg-color="primary">
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


          </v-window-item>
        </v-window>
      </v-card-text>
    </v-card>


</template>

<script>
import GamesList from "../components/GamesList.vue";

export default {
  name: "DatabaseDetail",
  components: {
    GamesList,
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
       allTabs : ref([{ name: 'Games', visible: true },]),
      };
    },
    mounted() {
      console.log("database reçu:"+ this.database);
        console.log("database reçu:"+ this.database.name );
  },

};
</script>
