<template>
  <div class="wrapper">
    <div class="side"></div>
    <div class="content">
      <v-card>
        <h1>this is the profile</h1>
      </v-card>
    </div>
    <div class="side"></div>
  </div>
</template>

<script>
  export default {
    name: "Profile"
  }
</script>

<style scoped>
  .wrapper {
    width: 100vw;
    display: grid;
    grid-template-columns: 1fr 80vw 1fr;
  }
</style>
