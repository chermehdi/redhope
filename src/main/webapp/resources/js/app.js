$("#services").click(function () {
  $('html, body').animate({
    scrollTop: $("#our-services").offset().top
  }, 1000);
});

/**
 * switch animation for pictures in the about section
 */
const pictures = document.querySelectorAll('.image-container img')
const containers = document.querySelectorAll('.image-container')

const animated = new Array(pictures.length)
for (let i = 0; i < pictures.length; i++) {
  animated[i] = Math.random() > .5 ? true : false
}

let current = true

setInterval(function () {
  for (let i = 0; i < pictures.length; i++) {
    if (animated[i] === current) {
      pictures[i].classList.add('late')
    } else {
      pictures[i].classList.remove('late')
    }
  }
  current = !current
}, 3000)
