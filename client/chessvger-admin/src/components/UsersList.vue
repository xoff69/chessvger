<template>
  User List
  crud + possibilite de se connecter comme lui de facon a gerer ses
  voir le detail = les contrats ... reinit password ..

  <h1>Users</h1>
    <v-data-table
      :items="users"
      :items-per-page="5"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>users</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
    </v-data-table>

</template>

<script>
import axios from "axios";

export default {
  name: "UsersList",
  data() {
    return {
      users: [],
      count: 0,

    };
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get("http://localhost:8082/apiadmin/users/all");
        console.log("user recuperes "+response.data);
        this.users = response.data;
      } catch (error) {
        console.error("Error all users :", error);
      }
    },

  },
  mounted() {
    this.fetchUsers();
  },
};
</script>

