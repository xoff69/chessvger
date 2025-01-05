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
  bbb
  <div>
    <h1>Feature Flags</h1>
    <div v-for="event in events" :key="event.timestamp">
      {{ event.message }} (Reçu à {{ event.timestamp }})
    </div>
  </div>
</template>

<script>
import websocketService from "@/services/websocketService";
import { io } from "https://cdn.socket.io/4.8.1/socket.io.esm.min.js";
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
    this.socket = io("http://localhost:3000/");
    this.socket.on("featureUpdate", (data) => {
      this.events.push({
        message: data.message,
        timestamp: new Date().toLocaleTimeString(),
      });
  },

  beforeDestroy() {
    // Déconnecte du WebSocket avant de détruire le composant
    websocketService.disconnect();
    if (this.socket) {
      this.socket.disconnect();
    }
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
