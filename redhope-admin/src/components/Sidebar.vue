<template>
  <div class="content__wrapper">
    <div class="sidebar__wrapper">
      <div class="sidebar">
        <div class="sidebar__header">
        </div>
        <div class="sidebar__body">
          <div class="sidebar__link">
            <router-link to="/dashboard/overview"> Dashboard</router-link>
          </div>
          <div class="sidebar__link">
            <router-link to="/dashboard/map"> Map</router-link>
          </div>
          <div class="sidebar__link">
            <router-link to="/dashboard/profile"> Profile</router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="__content">
      <slot></slot>
    </div>
  </div>

</template>
<script>
  export default {}
</script>
<style scoped>
  .content__wrapper {
    display: flex;
  }

  .sidebar {
    position: fixed;
  }

  .sidebar__wrapper {
    height: 100vh;
    padding: 1rem;
    padding-top: 4rem;
    min-width: 11rem;
  }

  .sidebar__link {
    padding: 1rem 2rem;
    text-align: left;
    font-size: 1.2rem;
    background: rgba(237, 37, 78, 1) !important;
    border-radius: 6rem;
    margin-top: 1rem;
    text-align: center;
    box-shadow: 0 2px 1px -1px rgba(0, 0, 0, .2),
    0 1px 1px 0 rgba(0, 0, 0, .14),
    0 1px 3px 0 rgba(0, 0, 0, .12);
    cursor: pointer;
    -webkit-transition: all .3s ease-in-out;
    -moz-transition: all .3s ease-in-out;
    -ms-transition: all .3s ease-in-out;
    -o-transition: all .3s ease-in-out;
    transition: all .3s ease-in-out;
  }

  .sidebar__link:hover {
    box-shadow: 3px 2px 3px -1px rgba(0, 0, 0, .2),
    0px 3px 2px 0 rgba(0, 0, 0, .14),
    0px 3px 2px 0 rgba(0, 0, 0, .12);
  }

  .sidebar__link a {
    color: white;
    text-decoration: none;
  }

  .__content {
    padding-left: 2rem;
    padding-right: 2rem;
  }
</style>
