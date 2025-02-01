<template>
  User List
  crud + possibilite de se connecter comme lui de facon a gerer ses
  voir le detail = les contrats ... reinit password ..

  <h1>Users</h1>
  <v-container class="mx-auto my-5" style="max-width: 1200px;">
    <v-data-table
      :items="users" item-key="id"
       :headers="headersS"
      :items-per-page="5"  show-select
      class="elevation-1"  @update:items-per-page="onItemsPerPageChange"
    >

    <template #item.actions="{ item }">
      <v-icon
        small
        color="red"
        class="mr-2"
        @click="deleteItem(item)"
      >
        mdi-delete
      </v-icon>
      <v-icon
        small
        color="red"
        class="mr-2"
        @click="openItem(item)"
      >
        mdi-table-edit
      </v-icon>
    </template>


      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>users 2</v-toolbar-title>
          <v-spacer></v-spacer>

            <v-icon
        small
        color="red"
        class="mr-2"
        @click="openItem(newItem)"
      >
        mdi-plus
      </v-icon>
        </v-toolbar>
      </template>
    </v-data-table>
  </v-container>
      <v-container v-if="showForm">
        <v-row no-gutters>
          <v-col> </v-col>
          <v-col>
      <UsersForm
        :user="selectedItem"
        @close="showForm = false"
        @save="fetchUsers"
      />
      </v-col>
      <v-col> </v-col>
      </v-row>
  </v-container>

</template>

<script>
import axios from "axios";
import UsersForm from './UsersForm.vue';
export default {
  components: { UsersForm },
  name: "UsersList",
  data() {
    return {
      users: [],
      count: 0,
      headersS: [
        { title: 'login', key: 'login', sortable: true },
          { title: 'tenantId', key: 'tenantId' , sortable: true },
          { title: 'Actions', key: 'actions', sortable: false }, // Colonne pour les boutons
        ],
      selectedItem:null,

newItem: {
  login: 'new',
  password: ''
},
showForm: false,
formData: {
  login: '',
  password: ''
}
    };
  },
  methods: {
    async openItem(item) {
           console.log("opem fpr, "+item.login);
          this.showForm = true;

          this.selectedItem=item;

      },

      async deleteItem(item) {
        console.log("delete id="+ item.id);
         this.users = this.users.filter((i) => i.id !== item.id);
        // await API.deleteAdr(item.id);
      },
    async fetchUsers() {
      try {
        const response = await axios.get("http://localhost:8080/apiadmin/users/all");
        console.log("user recuperes "+response.data);
        this.users =  response.data.list;
        this.count=response.data.count;
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

