<template>
    <div>
      <h3>Détail du produit : {{ product.name }}</h3>
      <p>ID : {{ product.id }}</p>
      <button @click="closeTab">Fermer</button>
    </div>
  </template>
  
  <script>
  export default {
    props: ['product', 'closeTab'],
  };
  </script>
  