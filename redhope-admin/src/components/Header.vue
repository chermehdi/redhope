<template>
<v-toolbar>
    <v-toolbar-side-icon v-if="isConnected" @click="showSideBar"></v-toolbar-side-icon>
    <v-toolbar-title>Redhope</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items class="hidden-sm-and-down">
      <v-btn flat v-if="isConnected" to="/dashboard">Dashboard</v-btn>
      <v-btn flat @click.prevent="disconnect" v-if="isConnected">Logout</v-btn>
      <div class="wrapper__buton">
        <v-btn raised  color="primary" to="/dashboard" v-if="!isConnected">Login</v-btn>
      </div>
    </v-toolbar-items>
  </v-toolbar>
</template>
<script>
  import {mapState, mapAction, mapMutations} from 'vuex'

  export default {
    computed: {
      ...mapState(['isConnected'])
    },
    methods: {
      ...mapMutations(['DISCONNECT_ADMIN']),
      disconnect() {
        this.DISCONNECT_ADMIN()
      },
      showSideBar() {
        this.$emit('sidebarshow')
      }
    }
  }
</script>
<style scoped>
  .hoverable {
    cursor: pointer;
  }
  a.nav-link.logo {
    font-size: 1.2rem;
  }
  .logo__wrapper {
    display: flex;
  }
  .wrapper__buton {
    padding-top: .8rem;
    padding-bottom: .8rem;
    height: 100%;
  }
</style>
