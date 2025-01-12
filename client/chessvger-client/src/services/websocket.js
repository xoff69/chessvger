// services/websocket.js
export class WebSocketService {
  constructor(url) {
    this.url = url;
    this.socket = null;
    this.callbacks = {};
  }

  connect() {
    this.socket = new WebSocket(this.url);

    this.socket.onopen = () => {
      console.log('WebSocket connected');
    };

    this.socket.onmessage = (message) => {
      const data = JSON.parse(message.data);
      const callback = this.callbacks[data.type];
      if (callback) {
        callback(data.payload);
      }
    };

    this.socket.onclose = () => {
      console.log('WebSocket disconnected');
    };

    this.socket.onerror = (error) => {
      console.error('WebSocket error:', error);
    };
  }

  disconnect() {
    if (this.socket) {
      this.socket.close();
    }
  }

  send(type, payload) {
    if (this.socket && this.socket.readyState === WebSocket.OPEN) {
      this.socket.send(JSON.stringify({ type, payload }));
    } else {
      console.error('WebSocket is not open');
    }
  }

  onMessage(type, callback) {
    this.callbacks[type] = callback;
  }
}
