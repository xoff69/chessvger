<template>
    <v-container>
      <v-card>
        <v-card-title>Liste des joueurs</v-card-title>
        <v-data-table
          :headers="headers"
          :items="players"
          class="elevation-1"
          :loading="loading"
          loading-text="Chargement des données..."
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>Joueurs</v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>
          </template>
        </v-data-table>
      </v-card>
    </v-container>
  </template>
  
  <script>
  import playerService from '@/services/playerServices';
  
  export default {
    name: 'HomeView',
    data() {
      return {
        players: [],
        headers: [
          { text: 'Nom', value: 'name' },
          { text: 'Note', value: 'rating' },
        ],
        loading: true,
      };
    },
    created() {
      this.fetchPlayers();
    },
    methods: {
      async fetchPlayers() {
        try {
          const response = await playerService.getPlayers();
          this.players = response.data;
        } catch (error) {
          console.error('Erreur lors du chargement des joueurs:', error);
        } finally {
          this.loading = false;
        }
      },
    },
  };
  </script>
  
  <style>
  /* Optionnel : style pour la page */
  </style>
  