<template>
  <div class="wrapper">
    <div class="side">
    </div>
    <div class="content" v-if="overviewLoading">
      <progress-bar/>
    </div>
    <div class="content" v-if="!overviewLoading">
      <h1 class="header">Overview</h1>
      <div class="col">
        <card>
          <div slot="title"><h3>Last Donation</h3></div>
          <div class="gen_info" slot="content">
            <div class="left">
              <h5>1 month Ago</h5>
            </div>
            <div class="right">
              <img src="../assets/img/blood-drop.png" class="blood_drop" alt="redhope"/>
            </div>
          </div>
        </card>
      </div>
      <div class="col">
        <card>
          <div slot="title"><h3>Profile status</h3></div>
          <div class="gen_info" slot="content" v-if="profileCompleted">
            <div class="left">
              <h5>Profile Completed</h5>
            </div>
            <div class="right">
              <img src="../assets/img/profile-completed.png" class="blood_drop" alt="redhope"/>
            </div>
          </div>
          <div class="gen_info" slot="content" v-if="!profileCompleted">
            <div class="left">
              <h5>Profile Info missing </h5>
            </div>
            <div class="right">
              <img src="../assets/img/profile.png" class="blood_drop" alt="redhope"/>
            </div>
          </div>
          <div slot="actions" class="__action">
            <v-btn :color="profileCompleted ? 'success': 'primary'" @click="completeProfile"> {{
              profileCompleted ? 'See Profile':
              'Complete Profile'}}
            </v-btn>
          </div>
        </card>
      </div>
      <div class="col">
        <card>
          <div slot="title"><h3>Donation History</h3></div>
          <div class="gen_info" slot="content">
            <div class="left">
              <h5>Donation count {{ donations.length }}</h5>
            </div>
            <div class="right">
              <img src="../assets/img/history.png" class="blood_drop" alt="redhope"/>
            </div>
          </div>
          <div slot="actions" class="__action">
            <v-btn color="success">See Complete History</v-btn>
          </div>
        </card>
      </div>
      <div class="donations__container">
        <h4>Donation History</h4>
        <v-data-table
          :headers="donationHeaders"
          :items="donationItems"
          hide-actions
          class="elevation-1"
        >
          <template slot="items" slot-scope="props">
            <td class="text-xs-left">{{ props.item.id }}</td>
            <td class="text-xs-left">{{ props.item.time }}</td>
            <td class="text-xs-left">{{ props.item.region }}</td>
          </template>
        </v-data-table>
      </div>
    </div>
    <div class="side">
    </div>
  </div>
</template>

<script>
  import {mapActions, mapState, mapGetters} from 'vuex'
  import Card from '@/components/Card'
  import ProgressBar from '@/components/Progress'

  export default {
    name: "Overview",
    components: {
      Card,
      ProgressBar
    },
    computed: {
      ...mapState(['overviewLoading']),
      ...mapGetters(['profileCompleted', 'donations', 'donationHeaders', 'donationItems'])
    },
    methods: {
      ...mapActions(['getDonations', 'getInfo']),
      completeProfile() {
        this.$router.push({path: '/dashboard/profile'})
      }
    },
    mounted() {
      this.getInfo()
      this.getDonations()
    }
  }
</script>

<style scoped>
  @import "../assets/css/vuetify.min.css";

  .wrapper {
    width: 100vw;
    display: grid;
    grid-template-columns: 1fr 80vw 1fr;
  }

  .gen_info {
    width: 100%;
    display: flex;
  }

  .left, right {
    width: 50%;
  }

  .left {
    display: flex;
    justify-contentt: flex-start;

  }

  .left h5 {
    color: #adadad;
    font-size: 1.6rem;
    font-weight: 300;
    word-break: keep-all;
    word-wrap: unset;
  }

  .right {
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
  }

  .blood_drop {
    max-width: 60%;
  }

  .content {
    display: grid;
    grid-template-columns: 1fr 1fr 1fr;
    grid-gap: 1rem;
  }

  .header {
    grid-column: 1 / 4;
    text-align: center;
  }

  .__action {
    padding-top: 1rem;
    width: 100%;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
  }

  .donations__container {
    grid-column: 1 / 4;
  }

  .pp {
    width: 100%;
  }
</style>
