import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
var global = window;
const websocketService = {
    stompClient: null,

    connect(callback) {
        const socket = new SockJS("http://localhost:8080/ws");
        this.stompClient = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000, // Essayer de se reconnecter toutes les 5 secondes
            debug: (str) => console.log(str), // Debug pour vérifier la connexion
        });

        this.stompClient.onConnect = () => {
            console.log("WebSocket connected!");
            // S'abonner au topic des messages
            this.stompClient.subscribe("/topic/messages", (message) => {
                callback(JSON.parse(message.body)); // Appeler le callback avec le message reçu
            });
        };

        this.stompClient.onStompError = (error) => {
            console.error("STOMP Error:", error);
        };

        this.stompClient.activate();
    },

    sendMessage(destination, message) {
        if (this.stompClient && this.stompClient.connected) {
            this.stompClient.publish({
                destination: destination,
                body: JSON.stringify(message),
            });
        } else {
            console.error("WebSocket is not connected.");
        }
    },

    disconnect() {
        if (this.stompClient) {
            this.stompClient.deactivate();
            console.log("WebSocket disconnected!");
        }
    },
};

export default websocketService;
