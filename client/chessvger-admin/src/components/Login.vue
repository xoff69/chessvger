<template>
  <div>
    <div v-if="!isAuthenticated">
      <form @submit.prevent="login">
        <input v-model="username" placeholder="Username" required />
        <input v-model="password" type="password" placeholder="Password" required />
        <button type="submit">Login</button>
      </form>
    </div>
    <div v-else>
      <p>Welcome, {{ user.name }} ({{ user.role }})</p>
      <button @click="logout">Logout</button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      username: '',
      password: '',
      user: null,
    };
  },
  computed: {
    isAuthenticated() {
      return !!localStorage.getItem('authToken');
    },
  },
  methods: {
    async login() {
      try {
        const response = await fetch('http://localhost:8080/api/login', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ username: this.username, password: this.password }),
        });

        if (!response.ok) throw new Error('Authentication failed');

        const data = await response.json();
        localStorage.setItem('authToken', data.token);
        localStorage.setItem('user', JSON.stringify({ id: data.id, name: data.name, role: data.role }));

        this.user = { id: data.id, name: data.name, role: data.role };
      } catch (error) {
        alert('Login failed: ' + error.message);
      }
    },
    logout() {
      console.log("remove user");
      localStorage.removeItem('authToken');
      localStorage.removeItem('user');
      this.user = null;
    },
  },
  created() {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      this.user = JSON.parse(storedUser);
    }
  },
};
</script>
