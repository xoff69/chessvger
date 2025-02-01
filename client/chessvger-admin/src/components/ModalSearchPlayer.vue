<template>
  <div v-if="isVisible" class="modal-overlay">
    <div class="modal-content">
      <!-- Bouton croix en haut à droite -->
      <button class="close-button" @click="closeModal">X</button>

      <!-- Contenu des onglets -->
      <div class="tabs">
        <button
          v-for="(tab, index) in tabs"
          :key="index"
          :class="{ active: activeTab === index }"
          @click="activeTab = index"
        >
          {{ tab.label }}
        </button>
      </div>
      <div class="tab-content">
        <component :is="tabs[activeTab].component" />
      </div>

      <!-- Boutons supplémentaires pour fermer la modale -->
      <div class="modal-actions">
        <button @click="closeModal" class="action-button">Fermer</button>
        <button @click="confirmAndClose" class="action-button confirm">Confirmer et fermer</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
  },
  emits: ["close", "confirm"],
  data() {
    return {
      activeTab: 0,
      tabs: [
        { label: "Tab 1", component: "Tab1Content" },
        { label: "Tab 2", component: "Tab2Content" },
        { label: "Tab 3", component: "Tab3Content" },
        { label: "Tab 4", component: "Tab4Content" },
      ],
    };
  },
  methods: {
    closeModal() {
      this.$emit("close");
    },
    confirmAndClose() {
      this.$emit("confirm");
      this.closeModal();
    },
  },
};
</script>

<style>
/* Style de la superposition */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

/* Style du contenu de la modale */
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 500px;
  max-width: 90%;
  position: relative;
}

/* Bouton croix */
.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  border: none;
  background: transparent;
  font-size: 20px;
  cursor: pointer;
}

/* Style des onglets */
.tabs {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}
.tabs button {
  padding: 10px 15px;
  cursor: pointer;
  border: none;
  background: #f0f0f0;
  border-radius: 4px;
  transition: background 0.3s;
}
.tabs button:hover {
  background: #ddd;
}
.tabs button.active {
  background: #007bff;
  color: white;
}

/* Contenu de l'onglet */
.tab-content {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

/* Boutons d'action */
.modal-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}
.action-button {
  padding: 10px 15px;
  border: none;
  background: #f0f0f0;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}
.action-button:hover {
  background: #ddd;
}
.action-button.confirm {
  background: #007bff;
  color: white;
}
.action-button.confirm:hover {
  background: #0056b3;
}
</style>
