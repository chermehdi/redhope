import Vue from 'vue'
import Vuex from 'vuex'

import router from './router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isConnected: false,
    isDimmed: false,
    token: localStorage.getItem('_token'),
    user: {}
  },
  getters: {
    email(state) {
      return state.user.email || 'Anonymous'
    }
  },
  mutations: {
    DISPLAY_LOADER(state) {
      state.isDimmed = true
    },
    HIDE_LOADER(state) {
      state.isDimmed = false
    },
    CONNECT_USER(state, data) {
      state.user = data
      state.isConnected = true
    }
  },
  actions: {
    login(context, payload) {
      context.commit('DISPLAY_LOADER')
      this._vm.$http.post('/api/user/login', payload, {
        'Content-Type': 'Application/json'
      }).then(res => res.json())
      .then(res => {
        context.commit('CONNECT_USER', res)
        setTimeout(() => {
          context.commit('HIDE_LOADER')
          router.push({path: "/dashboard"})
        }, 1000)
      }).catch(e => {
        // todo: display error messages here
        context.commit('HIDE_LOADER')
      })
    },
    signup(context, payload) {
      context.commit('DISPLAY_LOADER')
      this._vm.$http.post('/api/user/signup', payload, {
        'Content-Type': 'Application/json'
      }).then(res => res.json())
      .then(res => {
        //context.commit('CONNECT_USER', res)
        context.commit('HIDE_LOADER')
      })
    }
  }
})
