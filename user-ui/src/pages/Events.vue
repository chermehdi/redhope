<template>
  <div class="wrapper">
    <div class="side"></div>
    <div class="content">
      <v-card v-for="event in events" :key="event.id" class="card__decorator">
        <v-card-title>
          <span class="formated"> {{ event.date  }}</span>
        </v-card-title>
        <v-card-text>
          <div class="content" v-html="event.content"></div>
        </v-card-text>
      </v-card>
    </div>
    <div class="side"></div>
  </div>
</template>
<script>
  import {mapActions, mapGetters} from 'vuex'

  export default {
    name: "Events",
    data() {
      return {
        currentPage: 1
      }
    },
    computed: {
      ...mapGetters(['events'])
    },
    methods: {
      ...mapActions(['fetchEvents'])
    },
    mounted() {
      this.fetchEvents(this.currentPage)
    }
  }
</script>

<style scoped>
  @import "../assets/css/vuetify.min.css";

  .flex {
    display: flex;
    padding-top: 1rem;
    padding-bottom: 1rem;
  }

  .wrapper {
    width: 100vw;
    display: grid;
    grid-template-columns: 1fr 80vw 1fr;
  }

  .content {
    display: flex;
    flex-direction: column;
  }
  .card__decorator {
    margin-top: 1rem;
  }
</style>
