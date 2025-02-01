<template>
Use:
          <v-form ref="form">
            <v-text-field label="login" v-model="user.login"  />
            <v-text-field label="password" v-model="user.password"  />
            <v-text-field label="tenant" v-model="user.tenant"  />
            <v-text-field label="profile" v-model="user.profile"  />

          <v-btn color="primary" @click="save">Save</v-btn>
          <v-btn text @click="$emit('close')">Cancel</v-btn>
          </v-form>



  </template>

  <script>
  export default {
    props: {
      user: {
                type: Object,
                required: true
            }
    },
    data() {
      return {
      };
    },
    mounted() {
    console.log("Item reçu:"+ this.user.login );
  },
    methods: {
      async save() {
        console.log("save " +this.user);

        this.user.dateUpdated=new Date();
        if (this.user.id&&this.user.id!=0) {
          //await API.updateUesr(this.user.id, this.user);
        } else {
          //await API.createUser(this.user);
        }
        this.$emit('close');
        this.$emit('save');
      },
    },
  };
  </script>
