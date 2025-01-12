<template>
  <div>
    <h1>WebSocket Chat</h1>

    <!-- Formulaire pour envoyer un message -->
    <div>
      <input
        v-model="newMessage"
        placeholder="Type a message..."
        @keyup.enter="sendMessage"
      />
      <button @click="sendMessage">Send</button>
    </div>

    <!-- Liste des messages reçus -->
    <ul>
      <li v-for="(message, index) in messages" :key="index">
        {{ message }}
      </li>
    </ul>

  </div>
</template>

<script>
import websocketService from "@/services/websocketService";

export default {
  data() {
    return {
      newMessage: "", // Message que l'utilisateur tape
      messages: [],   // Liste des messages reçus
    };
  },
  methods: {
    sendMessage() {
      if (this.newMessage.trim()) {
        // Envoie le message au serveur via WebSocket
        websocketService.sendMessage("/app/send", this.newMessage);
        this.newMessage = ""; // Vide le champ de saisie
      }
    },
  },
  mounted() {
    // Connecte au WebSocket dès que le composant est monté
    websocketService.connect((message) => {
      this.messages.push(message); // Ajoute les messages reçus à la liste
    });
  },
  beforeDestroy() {
    // Déconnecte du WebSocket avant de détruire le composant
    websocketService.disconnect();
  },
};
</script>

<style scoped>
/* Style basique pour l'interface */
h1 {
  text-align: center;
}

input {
  padding: 8px;
  margin-right: 5px;
}

button {
  padding: 8px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  padding: 5px 0;
  border-bottom: 1px solid #ddd;
}
</style>
