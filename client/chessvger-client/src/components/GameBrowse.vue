<template>
   Browse
</template>

<script>
export default {
  name: "GameBrowse",
  props: {
      database: {
                type: Object,
                required: true
            }
    },
    data() {
      return {}
    },
    mounted() {
      console.log("Browse database reçue:"+ this.database);
  },


};
</script>
