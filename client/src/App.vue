<template>
  <div>
    <h1>Application de gestion des produits</h1>
    <ProductList :openProductTab="openProductTab" />

    <div v-if="openTabs.length > 0">
      <h2>Onglets de détails</h2>
      <div v-for="(tab, index) in openTabs" :key="tab.id">
        <ProductDetail :product="tab" :closeTab="() => closeProductTab(index)" />
      </div>
    </div>
  </div>
</template>

<script>
import ProductList from './components/ProductList.vue';
import ProductDetail from './components/ProductDetail.vue';

export default {
  components: { ProductList, ProductDetail },
  data() {
    return {
      openTabs: []
    };
  },
  methods: {
    openProductTab(product) {
      this.openTabs.push(product);
    },
    closeProductTab(index) {
      this.openTabs.splice(index, 1);
    }
  }
};
</script>
