import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api', // Remplacez par l'URL de votre service
  headers: {
    'Content-Type': 'application/json',
  },
});

export default {
  getPlayers() {
    console.log("get players");
    return apiClient.get('/allplayers');
  },
};