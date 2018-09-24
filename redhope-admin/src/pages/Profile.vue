<template>
  <div class="profile__wrapper">
    <h1>hello {{ email }}</h1>
  </div>
</template>
<script>
  import  {mapState, mapActions, mapMutations } from 'vuex'
  export default {
    computed: {
      ...mapState(['email'])
    }
  }
</script>
<style scoped>

</style>
