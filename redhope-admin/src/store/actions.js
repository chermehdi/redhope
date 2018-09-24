export default {
  login({ commit }, payload) {
    commit('DISPLAY_LOADER')
    this._vm.$http.post('/api/admin/login', payload, {
      'Content-Type': 'Application/json'
    }).then(res => res.json())
      .then(res => {
        commit('CONNECTED_ADMIN', res)
        commit('HIDE_LOADER')
      })
  },
  getUsersMatchingRegion({ commit, state }, region) {
    commit('DISPLAY_LOADER')
    const { token } = state
    this._vm.$http.post('/api/admin/by-region', { regionName: region }, { headers: { 'Authorization': token } })
      .then(res => res.json())
      .then(res => {
        console.log('the received res ', res)
        commit('HIDE_LOADER')
        commit('SET_USER_LIST', res)
        commit('CHANGE_SELECTED_REGION', region)
      })
      .catch(e => {
        console.log('the error is ', e)
        commit('HIDE_LOADER')
      })
  },
  getUserInfo({ commit }, token) {
    commit('DISPLAY_LOADER')
    this._vm.$http.get('/api/admin/info', { headers: { 'Authorization': token } })
      .then(res => res.json())
      .then(res => {
        console.log("the admin info ", res)
        commit('HIDE_LOADER')
        commit('CONNECTED_ADMIN', res)
      })
      .catch(e => {
        commit('HIDE_LOADER')
        commit('DISCONNECT_ADMIN')
      })
  },
  getScheduleList({ commit, state }) {
    commit('DISPLAY_LOADER')
    this._vm.$http.get('/api/schedules/all', { headers: { 'Authorization': state.token } })
      .then(res => res.json())
      .then(schedules => {
        commit('HIDE_LOADER')
        commit('UPDATE_SCHEDULES', schedules)
      })
  },
  getScheduleForDay({ commit, state }, dateString) {
    commit('DISPLAY_LOADER')
    this._vm.$http.get(`/api/schedules/${dateString}`, { headers: { 'Authorization': state.token } })
      .then(res => res.json())
      .then(schedules => {
        commit('HIDE_LOADER')
        commit('UPDATE_SCHEDULES', schedules)
      })
  },
  markDoneSchedule({ commit, state }, payload) {
    this._vm.$http.post(`/api/schedules/mark/${payload.id}`, { donationId: payload.donationId, bloodType: payload.type },
     { headers: { 'Authorization': state.token } })
      .then(res => res.json())
      .then(schedule => commit('UPDATE_SCHEDULE_ID', schedule))
      .catch(err => console.log('error', err))
  },
  publishEvent({ commit, state }, event) {
    return new Promise((resolve, reject) => {
      this._vm.$http.post('/api/events/create', event, { headers: { 'Authorization': state.token } })
      .then(res => res.json())
      .then(res => resolve(res))
      .catch(err => reject(err))
    })
  },
  getStockInfo({ commit, state }) {
    commit('DISPLAY_LOADER')
    this._vm.$http.get('/api/stock', { headers: { 'Authorization': state.token } })
      .then(res => res.json())
      .then(stockInfo => {
        commit('UPDATE_STOCK_INFO', stockInfo)
        commit('HIDE_LOADER')
      })
      .catch(err => commit('HIDE_LOADER'))
  },
  getIds({ commit, state }) {
    this._vm.$http.get('/api/donations/ids', { headers: { 'Authorization': state.token } })
      .then(res => res.json())
      .then(ids => commit('SET_DONATION_IDS', ids))
  },
  updateDonation({ commit, state }, donation) {
    this._vm.$http.post('/api/stock', donation, { headers: { 'Authorization': state.token } })
      .then(res => res.json())
      .then(donation => {
        commit('ADD_NEW_DONATION', donation)
      })
  }
}
