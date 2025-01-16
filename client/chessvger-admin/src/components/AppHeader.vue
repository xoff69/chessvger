<template>
  <v-app-bar app color="primary" dark>
    <v-toolbar-title>Mon Application</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn text>Accueil</v-btn>
    <v-btn text>À propos</v-btn>
    <v-btn text>Contact</v-btn>
    <v-btn
    href="http://localhost:9411/"
    target="_blank"
    icon
>
    <v-icon>window</v-icon> Metrics
</v-btn>
<v-btn
    href="http://localhost:5601"
    target="_blank"
    icon
>
    <v-icon>window</v-icon> kibana
</v-btn>
  </v-app-bar>
</template>

<script>
export default {
  name: "AppHeader",
};
</script>
