<template>
  <div class="login">
    <div class="login__outer">
      <div class="login__img"/>
      <div class="login__form">
        <h3 class="header">Registration Form</h3>
        <form @submit.prevent="register">
          <div class="field">
            <v-text-field
              v-model="firstName"
              label="Your first name"
              required
            ></v-text-field>
          </div>
          <div class="field">
            <v-text-field
              v-model="lastName"
              label="Your last name"
              required
            ></v-text-field>
          </div>
          <div class="field">
            <v-text-field
              v-model="email"
              label="Your email"
              required
            ></v-text-field>
          </div>
          <div class="field">
            <v-text-field
              v-model="password"
              label="Your password"
              type="password"
              required
            ></v-text-field>
          </div>
          <div class="field">
            <v-btn color="blue" class="white__text" @click.native="register">Register</v-btn>
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
        firstName: '',
        lastName: '',
        email: '',
        password: ''
      }
    },
    methods: {
      ...mapActions(['signup', 'showLoader']),
      ...mapMutations(['DISPLAY_LOADER']),
      register() {
        this.signup({
          email: this.email,
          password: this.password,
          firstName: this.firstName,
          lastName: this.lastName
        })
      }
    }
  }
</script>
<style scoped>
  @import "../assets/css/vuetify.min.css";

  .login {
    min-height: 100%;
    margin-top: -7rem;
  }

  .login__outer {
    display: grid;
    grid-template-columns: 1fr 1fr;
    min-height: 100vh;
  }

  .login__img {
    background: url(../assets/img/register-blood.jpg) center center no-repeat;
    background-size: cover;
  }

  .login__form {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
    flex-direction: column;
    padding: 1rem;
  }

  .login__form form {
    width: 80%;
  }

  .field {
    width: 100%;
  }

  h3.header {
    font-family: 'Roboto Mono', monospace;
    font-weight: bold;
    font-size: 3rem;
    padding: 1rem 1rem;
  }

  .white__text {
    color: white !important;
    font-weight: bold !important;
  }
</style>
