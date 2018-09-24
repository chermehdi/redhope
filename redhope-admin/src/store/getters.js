export default {
  getRegion(state) {
    return state.selectedRegion.split('-').join(' ')
  },
  schedules(state) {
    let { schedules } = state
    let ret = []
    for (let schedule of schedules) {
      let { profile } = schedule.user
      ret.push({
        region: schedule.region,
        name: `${profile.firstName} ${profile.lastName}`,
        id: schedule.id,
        type: profile.type,
        date: schedule.date,
        status: schedule.status
      })
    }
    return ret
  },
  getUsers(state) {
    let res = []
    for (let user of state.users) {
      if (user.profile) {
        res.push({
          "id": user.id,
          "email": user.email,
          "firstName": user.profile.firstName,
          "lastName": user.profile.lastName,
          "type": user.profile.type,
          "address": user.profile.address,
          "phone": user.profile.phone,
          "value": false
        })
      }
    }
    return res
  },
  stockMaxSize(state) {
    let maxSize = state.stock ? state.stock.maxSize: 10000
    if(!maxSize) return 1000; // since the maxsize is in ml
    return maxSize / 1000
  },
  ids(state) {
    return state.ids
  }
}
