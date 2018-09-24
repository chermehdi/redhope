<template>
  <div class="container-fluid">
      <transition name="fade" :key="router.path">
        <router-view></router-view>
      </transition>
  </div>
</template>
<script>
  import {mapState, mapActions, mapMutations} from 'vuex'
  import Sidebar from '@/components/Sidebar'

  export default {
    components: {
      Sidebar
    },
    methods: {
      ...mapActions(['getUsersMatchingRegion'])
    },
    computed: {
      ...mapState(['selectedRegion']),
      router() {
        return this.$router
      }
    },
    mounted() {
      this.getUsersMatchingRegion('Fes-Meknes')
    }
  }
</script>
<style>
  @import "../assets/css/vuetify.min.css";

  .table.datatable td, .table.datatable th {
    vertical-align: middle;
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
