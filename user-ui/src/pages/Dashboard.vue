<template>
  <v-layout wrap style="height: 100vh">
    <div id="app">
      <v-container>
        <navigation v-on:sidebarshow="showSidebar"></navigation>
        <transition name="fade" :key="router.path">
          <router-view/>
        </transition>
      </v-container>
    </div>
    <v-navigation-drawer
      v-model="drawer"
      temporary
      absolute
      class="white__bg"
    >
      <v-list class="pa-1">
        <v-list-tile avatar>
          <v-list-tile-avatar>
            <img src="https://randomuser.me/api/portraits/men/85.jpg">
          </v-list-tile-avatar>
          <v-list-tile-content>
            <v-list-tile-title>{{ email }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
      <v-list class="pt-0" dense>
        <v-divider></v-divider>
        <v-list-tile v-for="item in items" :key="item.title" @click="navigate(item.path)">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
  </v-layout>
</template>
<script>
  import {mapGetters} from 'vuex'
  import Navigation from '@/components/Header'

  export default {
    components: {
      Navigation
    },
    data() {
      return {
        drawer: false,
        items: [
          {title: 'Home', icon: 'dashboard', path: '/dashboard/overview'},
          {title: 'Profile', icon: 'person', path: '/dashboard/profile'},
          {title: 'Schedules', icon: 'watch', path: '/dashboard/scheduling'}
        ],
        right: true
      }
    },
    methods: {
      showSidebar() {
        this.drawer = true
      },
      navigate(to) {
        this.$router.push({path: to})
      }
    },
    computed: {
      ...mapGetters(['email']),
      router() {
        return this.$router
      }
    },
    mounted() {
    }
  }
</script>
<style>
  @import "../assets/css/vuetify.min.css";

  .table.datatable td, .table.datatable th {
    vertical-align: middle;
  }

  .container-fluid {
    padding-top: 4rem;
  }

  .fade-enter-active, .fade-leave-active {
    transition-property: opacity;
    transition-duration: .25s;
  }

  .fade-enter-active {
    transition-delay: .25s;
  }

  .fade-enter, .fade-leave-active {
    opacity: 0;
  }
</style>
