import router from '../router'

export default {
  CHANGE_SELECTED_REGION(state, newRegion) {
    state.selectedRegion = newRegion
  },
  CONNECTED_ADMIN(state, payload) {
    state.isConnected = true
    state.email = payload.email
    if (payload.token) {
      state.token = payload.token
    }
    //this._vm.$localStorage.set('_token', state.token)
    localStorage.setItem('_token', state.token)
    router.push({path: '/dashboard/overview'})
  },
  DISCONNECT_ADMIN(state) {
    state.isConnected = false
    state.email = null
    state.token = undefined
    localStorage.removeItem('_token')
    router.push({path: '/'})
  },
  DISPLAY_LOADER(state) {
    state.isDimmed = true
  },
  HIDE_LOADER(state) {
    state.isDimmed = false
  },
  SET_USER_LIST(state, list) {
    state.users = list
  },
  UPDATE_SCHEDULES(state, schedules) {
    state.schedules = schedules
  },
  UPDATE_SCHEDULE_ID(state, targetSchedule) {
    let newSch = []
    for(let i = 0; i < state.schedules.length; i++) {
      if(state.schedules[i].id === targetSchedule.id) {
       newSch.push(targetSchedule)
      }else {
        newSch.push(state.schedules[i])
      }
    }
    state.schedules = newSch
  },
  UPDATE_STOCK_INFO(state, stockInfo) {
    state.stock = stockInfo
  },
  SET_DONATION_IDS(state, ids) {
    state.ids = ids
  },
  ADD_NEW_DONATION(state, donation) {
    if(!donation.plasma.removed)
      state.stock[donation.type].plasma += donation.plasma.quantity
    if(!donation.platelet.removed)
      state.stock[donation.type].platelet += donation.platelet.quantity
    if(!donatoin.redCells.removed)
      state.stock[donation.type].redCells += donation.redCell.quantity
  }
}
