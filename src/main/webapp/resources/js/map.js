var paths = document.querySelectorAll('path')
var cityLabel = document.querySelector('.city')
const regionName = document.querySelector('.region__name')
const peopleTable = document.querySelector('.people__table')
let people = []

let city = ''


function doAjaxRequest(url, data, method) {
  return new Promise((resolve, reject) => {
    $.ajax({
      url,
      data,
      type: method,
      success: function (data) {
        resolve(data)
      },
      error: function (e) {
        reject(e)
      }
    })
  })
}

function addPeopleToTable() {
  for(let p of people){
    let row = peopleTable.insertRow(-1)
    row.insertCell(-1).innerHTML = p['id']
    row.insertCell(-1).innerHTML = p['name']
    row.insertCell(-1).innerHTML = p['email']
    row.insertCell(-1).innerHTML = p['address']
    row.insertCell(-1).innerHTML = p['phone']
    row.insertCell(-1).innerHTML = p['type']
  }
}

function fadeOut(el, currentCity) {
  const url = 'api/test'
  const data = {
    region: currentCity
  }
  doAjaxRequest(url, data, 'get')
  .then(e => {
    regionName.textContent = currentCity
    people = e
    addPeopleToTable()
    console.log('success ', e)
  })
  .catch(e => console.log('error', e))
  el.classList.add('fadeOut')
  setTimeout(() => {
    document.body.removeChild(el)
  }, 1000)
}

for (let path of paths) {
  // hover start part
  path.addEventListener('mouseover', (e) => {
    city = path.dataset.city
    if (city) {
      cityLabel.textContent = city
      cityLabel.classList.add('show')
    }
    path.classList.add('show')
  })

  // click part
  path.addEventListener('click', (e) => {
    const container = document.querySelector('.__container')
    const currentCity = e.target.dataset.city
    fadeOut(container, currentCity)
  })

  // hover end part
  path.addEventListener('mouseleave', (e) => {
    cityLabel.classList.remove('show')
    path.classList.remove('show')
  })
}