<template>
  <div id="app">

    <main>
      <router-view /> <!-- Affiche le composant correspondant à la route -->
    </main>
    <footer>
      <p>© 2025 Mon Application Vue.js</p>
    </footer>
  </div>
</template>

<script lang="ts">
export default {
  name: 'App',
};
</script>

<style scoped>
#app {
  font-family: Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
}

header {
  background-color: #42b983;
  color: white;
  padding: 1em;
}

nav ul {
  list-style-type: none;
  padding: 0;
}

nav ul li {
  display: inline;
  margin: 0 10px;
}

nav ul li a {
  color: white;
  text-decoration: none;
}

footer {
  margin-top: 20px;
  font-size: 0.8em;
}
</style>
