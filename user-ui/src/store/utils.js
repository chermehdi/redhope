import moment from 'moment'

export function formatDate(year, month, day, hour, min) {
  let dateString = `${day}-${month}-${year} ${hour}:${min}`
  return moment(dateString, 'D-M-YYYY H:m').fromNow()
}
