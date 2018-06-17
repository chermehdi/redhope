<template>
  <div :class="showing">
    <div class="loading-content">
      <svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" id="Capa_1" x="0px" y="0px" viewBox="0 0 30.646 30.646" height="100px" style="enable-background:new 0 0 30.646 30.646;" xml:space="preserve">
    <g>
      <g>
        <path d="M16.458,0.584C16.197,0.218,15.773,0,15.323,0c-0.451,0-0.874,0.217-1.137,0.584c-2.808,3.919-9.843,14.227-9.843,19.082    c0,6.064,4.915,10.98,10.979,10.98c6.065,0,10.981-4.916,10.981-10.98C26.304,14.811,19.266,4.503,16.458,0.584z M11.467,25.881    c-0.3,0.357-0.732,0.542-1.167,0.542c-0.345,0-0.695-0.118-0.981-0.358c-4.329-3.646-2.835-9.031-2.769-9.26    c0.234-0.809,1.073-1.273,1.886-1.042c0.808,0.231,1.274,1.075,1.045,1.881c-0.047,0.175-0.982,3.743,1.804,6.089    C11.927,24.275,12.01,25.236,11.467,25.881z M14.464,28.91c-0.893,0-1.62-0.727-1.62-1.62c0-0.896,0.727-1.621,1.62-1.621    c0.896,0,1.62,0.726,1.62,1.621C16.084,28.185,15.359,28.91,14.464,28.91z" fill="#FFFFFF"/>
      </g>
    </g>
  </svg>
      <div class="loading">Loading ...</div>
    </div>
  </div>
</template>

<script>
  import {mapMutations, mapState} from "vuex";

  export default {
    computed: {
      ...mapState(["isDimmed"]),
      showing() {
        let obj = {
          show: this.isDimmed,
          loader: true
        };
        return obj;
      }
    },
    methods: {
      ...mapMutations(["DISPLAY_LOADER"]),
      display() {
        this.DISPLAY_LOADER();
      }
    }
  };
</script>
<style>
  .loader {
    position: absolute;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    text-align: center;
    background-color: rgba(237, 37, 78, 1);
    z-index: -1;
    opacity: 0;
    transition: all 1s ease-in-out;
  }

  .loader.show {
    opacity: 1;
    z-index: 1500;
  }

  .loader:not(.show) .loading-content {
    z-index: -1;
  }

  .loader svg {
    width: 60px;
    max-width: 100%;
    margin-bottom: 5rem;
    animation: drop 0.5s infinite ease-in alternate;
  }

  .loading-content {
    height: 100%;
    padding: 5rem;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .loading {
    color: white;
    font-size: 1.5rem;
  }

  @keyframes drop {
    100% {
      transform: translateY(70%);
    }
  }
</style>
