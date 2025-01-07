<template>
  <v-app-bar app color="primary" dark>
    <v-toolbar-title>Mon Application</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-btn text>Accueil</v-btn>
    <v-btn text>À propos</v-btn>
    <v-btn text>Contact</v-btn>
    <v-btn
    href="http://localhost:16686"
    target="_blank"
    icon
>
    <v-icon>window</v-icon> Jaeger
</v-btn>

  </v-app-bar>
</template>

<script>
export default {
  name: "AppHeader",
};
</script>
