<template>
  <div class="wrapper">
    <div class="side"></div>
    <div class="content">
      <v-card>
        <h2>Book a new Schedule</h2>
        <div class="flex">
          <v-flex xs6 lg6>
            <v-menu
              :close-on-content-click="false"
              v-model="showDate"
              :nudge-right="40"
              lazy
              transition="scale-transition"
              offset-y
              full-width
              max-width="290px"
              min-width="290px"
            >
              <v-text-field
                slot="activator"
                v-model="date"
                label="Date (read only text field)"
                hint="MM/DD/YYYY format"
                persistent-hint
                prepend-icon="event"
                readonly
              ></v-text-field>
              <v-date-picker v-model="date" no-title @input="showDate = false"></v-date-picker>
            </v-menu>
          </v-flex>
          <v-flex xs6>
            <v-select
              :items="regions"
              v-model="region"
              label="Select"
              single-line
            ></v-select>
          </v-flex>
        </div>
        <v-btn color="primary" @click="schedule">Book a Schedule</v-btn>
      </v-card>
    </div>
    <div class="side"></div>
  </div>
</template>

<script>
  import {mapActions, mapGetters} from 'vuex'

  export default {
    name: "Scheduling",
    data() {
      return {
        date: null,
        showDate: false,
        region: {}
      }
    },
    computed: {
      ...mapGetters(['regions'])
    },
    methods: {
      ...mapActions(['fetchRegions', 'bookSchedule']),
      schedule() {
        let data = {
          region: this.region.text,
          date: this.date
        }
        this.bookSchedule(data)
      }
    },
    mounted() {
      this.fetchRegions()
    }
  }
</script>

<style scoped>
  @import "../assets/css/vuetify.min.css";

  .flex {
    display: flex;
  }

  .wrapper {
    width: 100vw;
    display: grid;
    grid-template-columns: 1fr 80vw 1fr;
  }
</style>
