// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Vuetify from 'vuetify'
import VueLocalStorage from 'vue-localstorage'
import VueResource from 'vue-resource'

import moment from 'moment'

import store from './store/index'
import App from './App'
import router from './router'
import 'vuetify/dist/vuetify.min.css'

Vue.use(VueLocalStorage)
Vue.use(VueResource)

Vue.use(Vuetify, {
  theme: {
    primary: '#e5004f',
    secondary: '#b0bec5',
    accent: '#8c9eff',
    error: '#b71c1c'
  }
})

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: {App},
  template: '<App/>'
})
