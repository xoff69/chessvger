<template>
  GamesPlayers
</template>

<script>
export default {
 name: "GamesPlayers",
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
     console.log("GamesPlayers database reçue:"+ this.database);
 },


};
</script>
