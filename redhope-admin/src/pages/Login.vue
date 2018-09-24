<template>
  <div class="container">
    <div class="row_50">
      <div class="card">
        <h1 class="header__light">Enter Your Credentials</h1>
        <form action="#" @submit.prevent="connect">
          <div class="row">
            <v-text-field
                  v-model="email"
                  :counter="60"
                  label="Email"
                  required
                ></v-text-field>
          </div>
          <div class="row">
           <v-text-field
          :append-icon="e2 ? 'visibility' : 'visibility_off'"
          :append-icon-cb="() => (e2 = !e2)"
          :type="e2 ? 'password' : 'text'"
          name="input-10-2"
          label="Password"
          v-model="password"
        ></v-text-field>
          </div>
          <div class="row">
            <v-btn submit @click="connect"> Connect</v-btn>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  data() {
    return {
      email: "",
      password: "",
      e2: true
    };
  },
  methods: {
    ...mapActions(["login", "showLoader"]),
    ...mapMutations(["DISPLAY_LOADER"]),
    connect() {
      this.login({ email: this.email, password: this.password });
    }
  }
};
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
 .row_50 {
  width: 75%;
  margin: 1rem auto;
} 
</style>
