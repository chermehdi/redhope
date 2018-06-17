<template>
  <div class="container mgt">
    <div class="row">
      <div class="col-md-8 offset-md-2 card">
        <h1 class="header__light">Enter Your Credentials</h1>
        <form action="#" @submit.prevent="connect">
          <div class="form-group">
            <input type="text" v-model="email" placeholder="Your Email" class="form-control">
          </div>
          <div class="form-group">
            <input type="password" v-model="password" placeholder="Your Password"
                   class="form-control">
          </div>
          <div class="form-group">
            <input type="submit" class="btn btn-primary btn-round" value="Login">
            <router-link to="/signup" class="btn btn-success btn-round">Sign up</router-link>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
  import {mapActions, mapMutations} from 'vuex'

  export default {
    data() {
      return {
        email: '',
        password: ''
      }
    },
    methods: {
      ...mapActions(['login', 'showLoader']),
      ...mapMutations(['DISPLAY_LOADER']),
      connect() {
        this.login({email: this.email, password: this.password})
      }
    }
  }
</script>
<style scoped>
  h1 {
    text-align: center;
  }

  .row {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .card {
    padding: 2rem;
    border-radius: 1rem;
  }

  .header__light {
    font-weight: 300;
  }

  .btn-primary {
    background: rgba(237, 37, 78, 1) !important;
  }

  .form-control:focus {
    border: 1px solid rgba(237, 37, 78, 1);
  }

  .mgt {
    padding-top: 3rem;
  }
</style>
