<template>
  <div class="wrapper">
    <div class="side"></div>
    <div class="content">
      <h1>this is an overview</h1>
    </div>
    <div class="side"></div>
  </div>
</template>

<script>
  export default {
    name: "Overview"
  }
</script>

<style scoped>
  .wrapper {
    width: 100vw;
    display: grid;
    grid-template-columns: 1fr 80vw 1fr;
  }
</style>
