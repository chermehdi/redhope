import Vue from 'vue'
import Vuex from 'vuex'

import router from '../router/index'
import {formatDate} from './utils'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isConnected: false,
    isDimmed: false,
    overviewLoading: false,
    token: localStorage.getItem('_token'),
    user: {},
    events: [],
    regions: [],
    profile: {
      completed: false,
      address: "",
      email: "",
      lastName: "",
      firstName: "",
      phone: "",
      type: ""
    },
    snackbar: {
      visible: false,
      text: ''
    },
    donations: []
  },
  getters: {
    events(state) {
      return state.events.map(event => {
      let  { createdAt } = event
        return {
          "id": event.id,
          "content": event.content,
          date: formatDate(
            createdAt.year,
            createdAt.monthValue,
            createdAt.dayOfMonth,
            createdAt.hour,
            createdAt.minute
          )
        }
      })
    },
    regions(state) {
      let values = []
      for (let region of state.regions) {
        values.push({text: region})
      }
      console.log('the returned region is ', values)
      return values
    },
    snackBarVisible(state) {
      return state.snackbar.visible
    },
    snackBarText(state) {
      return state.snackbar.text
    },
    email(state) {
      return state.user.email || 'Anonymous'
    },
    firstName(state) {
      return state.user.firstName || ''
    },
    lastName(state) {
      return state.user.lastName || ''
    },
    fullName(state) {
      const {user} = state
      return `${user.firstName} ${user.lastName}`
    },
    profileCompleted(state) {
      return state.profile.completed
    },
    donations(state) {
      return state.donations || []
    },
    donationHeaders(state) {
      return [
        {
          text: '#',
          sortable: false,
          value: 'id'
        },
        {
          text: 'Date',
          sortable: true,
          value: 'time'
        },
        {
          text: 'Region',
          sortable: true,
          value: 'region'
        }
      ]
    },
    donationItems(state) {
      let items = []
      let pt = 1
      for (let donation of state.donations) {
        items.push({
          value: false,
          name: 'stuff',
          region: donation.regionName,
          time: new Date(donation.createdAt).toDateString(),
          id: pt
        })
        ++pt
      }
      return items
    },
    profile(state) {
      return state.profile
    },
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
    },
    SET_TOKEN(state, data) {
      state.isConnected = true
      state.token = data.token
      state.user = {
        firstName: data.firstName,
        lastName: data.lastName,
        email: data.email
      }
    },
    SET_DONATIONS(state, data) {
      state.donations = data
    },
    USER_INFO(state, info) {
      console.log('user data ', info)
      state.profile = info
    },
    OVERVIEW_LOADING(state) {
      state.overviewLoading = true
    },
    OVERVIEW_FINISHED(state) {
      state.overviewLoading = false
    },
    PROFILE_UPDATED(state, profile) {
      console.log('updating profile data ', profile)
      state.profile = profile
      router.push('profile')
    },
    HIDE_SNACKBAR(state) {
      state.snackbar.visible = false
    },
    UPDATE_REGIONS(state, regions) {
      state.regions = regions
    },
    SET_EVENTS(state, events) {
      state.events = events
    }
  },
  actions: {
    login(context, payload) {
      context.commit('DISPLAY_LOADER')
      this._vm.$http.post('/api/user/login', payload, {
        'Content-Type': 'Application/json'
      }).then(res => res.json())
      .then(res => {
        context.commit('SET_TOKEN', res)
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
      this._vm.$http.post('/api/user/signup', JSON.stringify(payload), {
        'Content-Type': 'Application/json'
      }).then(res => res.json())
      .then(res => {
        //context.commit('CONNECT_USER', res)
        context.commit('HIDE_LOADER')
        setTimeout(() => router.push({path: '/'}), 500)
      })
    },
    getDonations({commit, state}) {
      this._vm.$http.get('/api/donations',
        {headers: {'Authorization': state.token}})
      .then(res => res.json())
      .then(donations => {
        commit('SET_DONATIONS', donations)
        commit('OVERVIEW_FINISHED')
      })
      .catch(err => {
        console.log('the error is ', err)
        commit('OVERVIEW_FINISHED')
      })
    },
    getInfo({commit, state, dispatch}) {
      commit('OVERVIEW_LOADING')
      this._vm.$http.get('/api/users/me',
        {headers: {'Authorization': state.token}})
      .then(res => res.json())
      .then(info => {
        commit('USER_INFO', info)
        dispatch('getDonations')
      })
      .catch(err => {
        console.log('the error is ', err)
        commit('OVERVIEW_FINISHED')
      })
    },
    updateProfile({commit, state}, data) {
      commit('DISPLAY_LOADER')
      this._vm.$http.post('/api/users/update', data, {
        headers: {
          'Authorization': state.token
        }
      }).then(response => response.json())
      .then(profile => {
        commit('PROFILE_UPDATED', profile)
        setTimeout(() => {
          commit('HIDE_LOADER')
        }, 1000)
      })
    },
    fetchRegions({commit, state}) {
      this._vm.$http.get('/api/regions', {
        headers: {
          'Authorization': state.token
        }
      }).then(response => response.json())
      .then(regions => {
        commit('UPDATE_REGIONS', regions)
      })
    },
    bookSchedule({commit, state}, data) {
      data = {...data, userEmail: state.user.email}
      this._vm.$http.post('/api/schedules', data, {
        headers: {
          'Authorization': state.token
        }
      }).then(res => res.json())
      .then(success => {
        console.log('created successfully !')
      })
      .catch(err => {
        console.log('an error occured ', err)
      })
    },
    fetchEvents({commit, state}, page) {
      commit('DISPLAY_LOADER')
      this._vm.$http.get(`/api/events/page/${page}`, {
        headers: {
          'Authorization': state.token
        }
      }).then(res => res.json())
      .then(events => {
        commit('SET_EVENTS', events)
        commit('HIDE_LOADER')
      })
    }
  }
})
