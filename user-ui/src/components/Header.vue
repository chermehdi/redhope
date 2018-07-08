<template>
  <nav class="navbar navbar-expand-lg fixed-top whiteBackground shadow">
    <div class="container">
      <div class="navbar-translate">
        <div class="logo__wrapper">
          <v-icon v-if="isConnected" class="hoverable" @click="showSideBar">menu</v-icon>
          <a class="navbar-brand" id="brand">
            <img class="logo" src="../assets/img/logo.png"/>
          </a>
        </div>
        <button class="navbar-toggler navbar-toggler-right navbar-burger"
                type="button" data-toggle="collapse" data-target="#navbarToggler"
                aria-controls="navbarTogglerDemo02" aria-expanded="false"
                aria-label="Toggle navigation">
          <span class="navbar-toggler-bar"></span> <span
          class="navbar-toggler-bar"></span> <span
          class="navbar-toggler-bar"></span>
        </button>
      </div>
      <div class="collapse navbar-collapse" id="navbarToggler">
        <ul class="navbar-nav ml-auto">
          <li class="nav-item"><a href="#" target="_blank"
                                  class="nav-link">About</a></li>
          <li class="nav-item"><a href="#" id="services" class="nav-link">Services</a>
          <li class="nav-item"><router-link to="/dashboard/events" class="nav-link">Events</router-link>
          </li>
          <li v-if="!isConnected" class="nav-item"><a href="signup-general.xhtml"
                                                      class="btn btn-round accent-background"
                                                      style="border: none">Sign in</a></li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
  import {mapState} from 'vuex'

  export default {
    name: "Header",
    computed: {
      ...mapState(['isConnected'])
    },
    methods: {
      showSideBar() {
        this.$emit('sidebarshow')
      }
    }
  }
</script>

<style scoped>
  /**
   * Landing section
   */
  .hoverable {
    cursor: pointer;
    margin-right: 1rem;
    font-size: 2.3rem;
  }

  a.nav-link.logo {
    font-size: 1.2rem;
  }

  .logo__wrapper {
    display: flex;
  }

  .shadow {
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.16), 0 2px 4px rgba(0, 0, 0, 0.23);
  }

  nav.navbar.navbar-expand-lg.fixed-top.shadow {
    /*background-color: rgba(237, 37, 78, 1) !important;*/
  }

  .navbar-brand .logo {
    width: 100px;
  }

  .navbar .navbar-nav .nav-item .nav-link {
    color: rgba(237, 37, 78, 1);
  }

  #brand {
    color: white;
    max-width: 5rem;
    padding: 0;
  }

  .blood-drop {
    animation-name: pulse;
    animation-iteration-count: infinite;
    animation-timing-function: ease-in-out;
    animation-duration: 4s;
    position: relative;
  }

  /**
   * Services Section
   */
  .subHeader {
    font-size: 1.5em;
    margin-top: 5%;
  }

  .subHeader.slideInLeft {
    animation-duration: .5s;
  }

  .wrappper {
    padding-bottom: 4rem;
    padding-top: 4rem;
  }

  section.services, .service-container {
    padding-bottom: 4rem;
    padding-top: 4rem;
  }

  .service-container.card {
    background: #F7F4F8;
    padding-left: .5rem;
    padding-right: .5rem;
  }

  .service-header {
    padding-bottom: 1rem;
  }

  .service-header > h3 {
    font-weight: 500;
    margin: 0;
  }

  .image-container {
    max-width: 100px;
    width: 100px;
    position: absolute;
    padding-top: 1rem;
    display: inline-block;
    padding-bottom: 2rem;
    padding-top: 2rem;
  }

  #carousel {
    min-height: 300px;
    position: relative;
  }

  .image-container img {
    width: 100%;
    border-radius: 50%;
    position: absolute;
    transition: all .4s ease-in-out;
    border: 1px solid #ddd;
  }

  .image-container img.late {
    animation: pulse-animation 2.5s;
  }

  nav.navbar.navbar-expand-lg.fixed-top.shadow {
    background-color: rgba(255, 255, 255, 1) !important;
    max-height: 7.5rem;
    z-index: 4;
  }

  /** hero animation **/
  @-webkit-keyframes pulse-animation {
    10% {
      box-shadow: 0 1px 2px rgba(199, 37, 74, 0.79), 0 1px 2px rgba(241, 37, 78, 0.5);
    }
    30% {
      transform: scale(0.7);
    }
    100% {
      transform: scale(1);
      box-shadow: 0 0 0 20px rgba(90, 153, 212, 0);
    }
  }

</style>
