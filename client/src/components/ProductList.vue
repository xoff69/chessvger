<template>
    <div>
      <h2>Liste des produits</h2>
      <ul>
        <li v-for="product in products" :key="product.id">
          <a href="#" @click="openProductTab(product)">{{ product.name }}</a>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    props: ['openProductTab'],
    data() {
      return {
        products: [
          { id: 1, name: 'Produit A' },
          { id: 2, name: 'Produit B' },
          { id: 3, name: 'Produit C' }
        ]
      };
    }
  };
  </script>
  