import Vue from 'vue'
import Vuetify from 'vuetify'
import VueLocalStorage from 'vue-localstorage'
import VueResource from 'vue-resource'
import wysiwyg from "vue-wysiwyg"

import App from './App'
import store from './store'

import router from './router'

Vue.config.productionTip = false
/* eslint-disable no-new */
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
Vue.use(wysiwyg, {
  hideModules: { "code": true },
  maxHeight: "400px"
})
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
