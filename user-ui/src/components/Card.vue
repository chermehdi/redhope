<template>
  <div class="card__container">
    <div class="card__title">
      <slot name="title"></slot>
    </div>
    <div class="card__content">
      <slot name="content"></slot>
    </div>
    <div class="card__actions">
      <slot name="actions"></slot>
    </div>
  </div>
</template>
<script>
  export default {
    name: "Card"
  }
</script>

<style scoped>
  .card__container {
    box-shadow: 0 2px 1px -1px rgba(0, 0, 0, .2), 0 1px 1px 0 rgba(0, 0, 0, .14), 0 1px 3px 0 rgba(0, 0, 0, .12);
    padding: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    max-width: 30rem;
    min-height: 35rem;
    padding-bottom: 0;
  }
  .card__container .card__title {
    width: 100%;
  }
</style>
