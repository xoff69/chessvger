<template>
  Feature flags:
            <v-form ref="form">
              <v-text-field label="Project" v-model="flag.project" required />
              <v-text-field label="Name" v-model="flag.name" required />
              <v-select
                label="Environment"
                v-model="flag.environment"
                :items="['dev', 'prod']"
                required
              />
              <v-text-field
                label="Allowed IDs (comma-separated)"
                v-model="flag.allowedIds"
              />
              <v-text-field
                label="Forbidden IDs (comma-separated)"
                v-model="flag.forbiddenIds"
              />
              <v-switch label="Active" v-model="flag.active" />


            <v-btn color="primary" @click="save">Save</v-btn>
            <v-btn text @click="$emit('close')">Cancel</v-btn>
            </v-form>


    </template>

    <script>

    export default {
      data() {
        return {
          flag:  {
            id:0,
            name: '',
            allowedIds: '',
            forbiddenIds: '',
            environment: '',
            active: true,
          },
        };
      },
      methods: {
        async save() {
          console.log("flag ="+this.flag);
          if (this.flag.id&&this.flag.id!=0) {
            await API.updateFlag(this.flag.id, this.flag);
          } else {
            await API.createFlag(this.flag);
          }
          this.$emit('close');
          this.$emit('save');
        },
      },
    };
    </script>
