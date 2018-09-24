const loader = document.querySelector('.loader')
const left = document.querySelector('.left')
const right = document.querySelector('.right')
const container = document.querySelector('.container_wrapper')

setTimeout(() => {
  loader.classList.add('disappear')
}, 1000)

left.addEventListener('mouseenter', () => {
  container.classList.add('left-hover')
})

left.addEventListener('mouseleave', () => {
  container.classList.remove('left-hover')
})

right.addEventListener('mouseenter', () => {
  container.classList.add('right-hover')
})

right.addEventListener('mouseleave', () => {
  container.classList.remove('right-hover')
})