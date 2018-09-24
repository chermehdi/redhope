<template>
  <div class="container__outer">
  <div class="water__container">
    <div class="marker_container">
      <div class="marker">
        <span class="dig">{{ percent }}</span><span class="sig">%</span>
      </div>
    </div>
  <div class="water__outer" :style="getFill" >
    <svg class="water__wave water__wave_back" :style="getContentColor" viewBox="0 0 560 20">
      <use xlink:href="#wave">
        <svg id="wave" width="100%" height="100%">
    <path d="M420,20c21.5-0.4,38.8-2.5,51.1-4.5c13.4-2.2,26.5-5.2,27.3-5.4C514,6.5,518,4.7,528.5,2.7c7.1-1.3,17.9-2.8,31.5-2.7c0,0,0,0,0,0v20H420z"></path>
    <path d="M420,20c-21.5-0.4-38.8-2.5-51.1-4.5c-13.4-2.2-26.5-5.2-27.3-5.4C326,6.5,322,4.7,311.5,2.7C304.3,1.4,293.6-0.1,280,0c0,0,0,0,0,0v20H420z"></path>
    <path d="M140,20c21.5-0.4,38.8-2.5,51.1-4.5c13.4-2.2,26.5-5.2,27.3-5.4C234,6.5,238,4.7,248.5,2.7c7.1-1.3,17.9-2.8,31.5-2.7c0,0,0,0,0,0v20H140z"></path>
    <path d="M140,20c-21.5-0.4-38.8-2.5-51.1-4.5c-13.4-2.2-26.5-5.2-27.3-5.4C46,6.5,42,4.7,31.5,2.7C24.3,1.4,13.6-0.1,0,0c0,0,0,0,0,0l0,20H140z"></path>
  </svg>
      </use>
    </svg>
    <svg class="water__wave water__wave_front" :style="getContentColor" viewBox="0 0 560 20">
      <use xlink:href="#wave">
        <svg id="wave" width="100%" height="100%">
    <path d="M420,20c21.5-0.4,38.8-2.5,51.1-4.5c13.4-2.2,26.5-5.2,27.3-5.4C514,6.5,518,4.7,528.5,2.7c7.1-1.3,17.9-2.8,31.5-2.7c0,0,0,0,0,0v20H420z"></path>
    <path d="M420,20c-21.5-0.4-38.8-2.5-51.1-4.5c-13.4-2.2-26.5-5.2-27.3-5.4C326,6.5,322,4.7,311.5,2.7C304.3,1.4,293.6-0.1,280,0c0,0,0,0,0,0v20H420z"></path>
    <path d="M140,20c21.5-0.4,38.8-2.5,51.1-4.5c13.4-2.2,26.5-5.2,27.3-5.4C234,6.5,238,4.7,248.5,2.7c7.1-1.3,17.9-2.8,31.5-2.7c0,0,0,0,0,0v20H140z"></path>
    <path d="M140,20c-21.5-0.4-38.8-2.5-51.1-4.5c-13.4-2.2-26.5-5.2-27.3-5.4C46,6.5,42,4.7,31.5,2.7C24.3,1.4,13.6-0.1,0,0c0,0,0,0,0,0l0,20H140z"></path>
  </svg>
      </use>
    </svg>
  </div>
</div>
  <div class="container__title lighter__text">
    {{ title }}
  </div>
</div>

</template>
<script>
export default {
  props: {
    percent: {
      type: Number,
      required: true
    },
    color: {
      type: String,
      default: '#FF2551'
    },
    title: {
      type: String,
      required: true
    }
  },
  computed: {
    getFill() {
      let value = 100 - this.percent;
      let style = {
        transform: `translate(0, ${value}%)`,
        background: this.color
      }
      return style
    },
    getContentColor() {
      return { fill: this.color }
    }
  }
};
</script>
<style>
.container__outer{
  margin: 0;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
}
.water__container {
  overflow: hidden;
  height: 450px;
  width: 250px;
  max-width: 300px;
  position: relative;
  background: #2D3142;
  box-shadow: 2px 2px 8px rgba(0, 0, 0, .3);
  border-radius: 8px;
}
.water__outer {
  position: absolute;
  height: 100%;
  width: 100%;
  z-index: 5;
  bottom: 0;
  left: 0;
  background: #FF2551;
  transition: .8s all ease-in-out;
}

.water__wave {
    width: 200%;
    position: absolute;
    bottom: 100%;
}

.water__wave_back {
  right: 0;
  fill: #D4254F;
  -webkit-animation: wave-back 3s infinite linear;
  animation: wave-back 3s infinite linear;
}
.water__wave_front {
  left: 0;
  fill: #FF2551;
  margin-bottom: -1px;
  -webkit-animation: wave-front 1.5s infinite linear;
  animation: wave-front 1.5s infinite linear;
}
.marker_container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 100;
}
.marker {
  font-size: 4em;
  display: inline-block;
  font-family: Roboto Mono;
  color: white;
  width: 100%;
  text-align: center;
  margin: 4rem auto;
}
.marker .dig, .marker .sig {
  margin: 0;
  padding: 0;
}
.marker .dig {
  font-size: 4.4rem;
}

.marker .sig {
  font-size: 3rem;
}

.container__title {
  text-align: center;
  width: 100%;
  font-size: 1.4rem;
  font-weight: 500;
  font-family: 'Roboto Mono', sans-serif;
  text-transform: capitalize;
  margin-top: 1rem;
}
@keyframes wave-front {
  100% {
    transform: translate(-50%, 0);
  }
}

@keyframes wave-back {
  100% {
    transform: translate(50%, 0);
  }
}
</style>


