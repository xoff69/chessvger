<template>
  Detail database
    <GamesList/>

</template>

<script>
import GamesList from "../components/GamesList.vue";

export default {
  name: "DatabaseDetail",
  components: {
    GamesList,
  },
  props: {
      database: {
                type: Object,
                required: true
            }
    },
    data() {
      return {
      };
    },
    mounted() {
      console.log("database reçu:"+ this.database);
        console.log("database reçu:"+ this.database.name );
  },

};
</script>
