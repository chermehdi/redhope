export default {
  isConnected: false,
  isDimmed: false,
  token: localStorage.getItem('_token'),
  email: '',
  selectedRegion: '',
  users: [],
  schedules: [],
  ids: [],
  stock: {
    "A_PLUS": {
      redCell: 0,
      plasma: 0,
      platelets: 0
    },
    "A_MINUS": {
      redCell: 0,
      plasma: 0,
      platelets: 0
    },
    "B_PLUS": {
      redCell: 0,
      plasma: 0,
      platelets: 0
    },
    "B_MINUS": {
      redCell: 0,
      plasma: 0,
      platelets: 0
    },
    "AB_PLUS": {
      redCell: 0,
      plasma: 0,
      platelets: 0
    },
    "AB_MINUS": {
      redCell: 0,
      plasma: 0,
      platelets: 0
    },
    "O_PLUS": {
      redCell: 0,
      plasma: 0,
      platelets: 0
    },
    "O_MINUS": {
      redCell: 0,
      plasma: 0,
      platelets: 0
    }
  }
}
