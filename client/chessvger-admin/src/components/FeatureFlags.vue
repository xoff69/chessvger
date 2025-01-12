<template>
  <v-app>
  <v-container class="mx-auto my-5" style="max-width: 1200px;">
      <v-data-table
        :headers="headersS"
        :items="featureFlags"
      >
      <template v-slot:top>
          <v-toolbar flat>
            <v-toolbar-title>Feature Flags</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="openForm">Add Flag</v-btn>
          </v-toolbar>
        </template>


      </v-data-table>
    </v-container>
      <v-container v-if="showForm">
        <v-row no-gutters>
          <v-col> </v-col>
          <v-col>
      <FeatureFlagForm

        @close="showForm = false"
        @save="fetchFeatureFlags"
      />
      </v-col>
      <v-col> </v-col>
      </v-row>
  </v-container>

</v-app>
<div>
    <h1>Feature Flags</h1>

  </div>
  </template>

  <script>
  import FeatureFlagForm from './FeatureFlagsForm.vue';
  export default {
    components: { FeatureFlagForm },
    data() {
      return {
        headersS: [
        { title: 'Project', key: 'project' },
          { title: 'Name', key: 'name' },
          { title: 'Start Date', key: 'startDate' },
          { title: 'End Date', key: 'endDate' },
          { title: 'Active', key: 'active' },
          { title: 'Allowed', key: 'allowedIds' },
          { title: 'Forbidden', key: 'forbiddenIds' },
          { title: 'environment', key: 'environment' },
        ],
        events: [],
        featureFlags: [],
        showForm: false,
      };
    },
    methods: {
     async openForm() {
    this.showForm = true;
  },
      async fetchFeatureFlags() {
        const { data } = await API.getFlags();
        this.featureFlags = data;
      },
    },
    mounted() {
      this.fetchFeatureFlags();
  },
  };
  </script>
