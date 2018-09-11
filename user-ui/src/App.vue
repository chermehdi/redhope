<template>

  <div id="app">
    <v-app>
      <div class="wrapper__outer">
        <transition :key="router.path" name="fade">
          <router-view/>
        </transition>
      </div>
      <loader/>
    </v-app>
  </div>
</template>

<script>
  import Navigation from '@/components/Header'
  import Loader from '@/components/Loader'
  import {mapGetters, mapMutations, mapState} from 'vuex'

  export default {
    name: 'App',
    components: {
      Navigation,
      Loader
    },
    data() {
      return {
      }
    },
    computed: {
      router() {
        return this.$router
      }
    },
  }

</script>

<style>
  @import "assets/css/bootstrap.min.css";
  @import "assets/css/now-ui-kit.css";
  @import "assets/css/vuetify.min.css";

  body {
    background-color: white;
  }
  * {
    box-sizing: border-box;
    font-family: 'Roboto', sans-serif;
  }

  .wrapper__outer {
    margin-top: 7rem;
    background-color: white;
  }

  .fade-enter-active,
  .fade-leave-active {
    transition-property: opacity;
    transition-duration: 0.25s;
  }

  .fade-enter-active {
    transition-delay: 0.25s;
  }

  .fade-enter,
  .fade-leave-active {
    opacity: 0;
  }

  .white__bg {
    background-color: white;
  }

  .list.pt-0.list--dense div:hover {
    transition: all .4s ease;
    background-color: #ddd;
  }
  .application--wrap {
    background-color: white;
  }
</style>
