<template>
    <div>
      <h1>Socket.IO Vue.js Demo</h1>
      <ul>
        <li v-for="(msg, index) in messages" :key="index">{{ msg }}</li>
      </ul>
      <input v-model="message" placeholder="Type your message" />
      <button @click="sendMessage">Send</button>
    </div>
  </template>
  
  <script>
  import { io } from 'socket.io-client';
  
  export default {
    data() {
      return {
        socket: null,
        messages: [],
        message: '',
      };
    },
    methods: {
      setupSocket() {
        // Se connecter au serveur Socket.IO
        this.socket = io('http://localhost:3000');
  
        // Écouter les messages du serveur
        this.socket.on('message', (data) => {
          this.messages.push(`Server: ${data}`);
        });
  
        this.socket.on('broadcast-message', (data) => {
          this.messages.push(`Broadcast: ${data}`);
        });
      },
      sendMessage() {
        if (this.message.trim() !== '') {
          // Envoyer un message au serveur
          this.socket.emit('send-message', this.message);
          this.message = '';
        }
      },
    },
    mounted() {
      this.setupSocket();
    },
    beforeDestroy() {
      if (this.socket) {
        this.socket.disconnect();
      }
    },
  };
  </script>