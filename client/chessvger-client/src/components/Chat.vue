<template>
  <div>
    <h1>WebSocket Client</h1>
    <p>Message reçu : {{ receivedMessage }}</p>
    <input
      type="text"
      v-model="messageToSend"
      placeholder="Entrez un message"
    />
    <button @click="sendMessage">Envoyer</button>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

export default {
  name: "WebSocketClient",
  data() {
    return {
      stompClient: null, // Instance STOMP
      receivedMessage: "", // Message reçu
      messageToSend: "", // Message à envoyer
    };
  },
  methods: {
    connect() {
      // Connectez-vous au serveur WebSocket via SockJS
      const socket = new SockJS("http://localhost:8080/ws");
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect({}, () => {
        console.log("Connecté au WebSocket");

        // S'abonner à la destination "/topic/notifications"
        this.stompClient.subscribe("/topic/notifications", (message) => {
          this.receivedMessage = message.body;
          console.log("Message reçu :", message.body);
        });
      }, (error) => {
        console.error("Erreur de connexion WebSocket :", error);
      });
    },
    sendMessage() {
      if (this.stompClient && this.messageToSend.trim()) {
        // Envoyer un message à "/app/hello" (géré côté serveur)
        this.stompClient.send("/app/hello", {}, this.messageToSend);
        this.messageToSend = "";
      } else {
        console.warn("Le client WebSocket n'est pas connecté ou le message est vide.");
      }
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect(() => {
          console.log("Déconnecté du WebSocket");
        });
      }
    },
  },
  mounted() {
    // Se connecter lorsque le composant est monté
    this.connect();
  },
  beforeUnmount() {
    // Se déconnecter lorsque le composant est détruit
    this.disconnect();
  },
};
</script>

<style scoped>
h1 {
  color: #333;
}
input {
  margin-right: 10px;
}
button {
  padding: 5px 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
</style>
