<template>
  <div :style="styleClasses" class="navigation__cell__container" @click="navigate">
    {{ text }}
  </div>
</template>
<script>
export default {
  props: {
    backgroundColor: {
      type: String,
      default: '#e5004f'
    },
    textColor: {
      type: String,
      default: 'white'
    },
    text: {
      type: String,
      required: true
    }, 
    link: {
      type: String,
      required: true
    }
  },
  computed: {
    styleClasses() {
      const styleObject = {
        backgroundColor: this.backgroundColor,
        color: this.textColor
      }
      return styleObject
    }
  },
  methods: {
    navigate() {
      this.$router.push({path: this.link})
    }
  }
}
</script>

<style scoped>
.navigation__cell__container {
    margin: 1rem;
    flex: 1;
    min-width: 400px;
    display: flex;
    font-size: 2em;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    box-shadow: 0 2px 1px -1px rgba(0,0,0,.2), 0 1px 1px 0 rgba(0,0,0,.14), 0 1px 3px 0 rgba(0,0,0,.12);
    transition: all .3s cubic-bezier(.76,.62,.13,.83);
  }
  .navigation__cell__container:hover {
    box-shadow: 0 6px 2px -1px rgba(0,0,0,.2), 0 10px 10px 0 rgba(0,0,0,.14), 0 4px 12px 0 rgba(0,0,0,.12);
    transform: translate3d(0, 0, -151px);
  }
</style>
