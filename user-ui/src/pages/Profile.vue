<template>
  <div class="wrapper">
    <div class="side"></div>
    <div class="content">
      <v-stepper v-model="step">
        <v-stepper-header>
          <v-stepper-step :complete="step > 1" step="1">Personal Information</v-stepper-step>
          <v-divider></v-divider>
          <v-stepper-step :complete="step > 2" step="2">Address Information</v-stepper-step>
          <v-divider></v-divider>
          <v-stepper-step step="3">Additional Information</v-stepper-step>
        </v-stepper-header>
        <v-stepper-items>
          <v-stepper-content step="1">
            <v-card class="mb-5">
              <v-card-text>
                <v-form v-model="valid">
                  <v-text-field v-model="personal.firstName" :counter="50" label="First Name"
                                required/>
                  <v-text-field v-model="personal.lastName" :counter="50" label="Last Name"
                                required/>
                  <v-text-field :value="email" label="Email " required disabled/>
                  <v-text-field v-model="personal.phone" :counter="10" label="Phone " required/>
                </v-form>
              </v-card-text>
            </v-card>
            <v-btn color="primary" :disabled="!valid" @click.native="step = 2">Continue</v-btn>
            <v-btn>Cancel</v-btn>
          </v-stepper-content>
          <v-stepper-content step="2">
            <v-card class="mb-5">
              <v-card-text>
                <v-text-field multi-line v-model="address.address" :counter="150" label="Address "
                              required/>
              </v-card-text>
            </v-card>
            <v-btn color="primary" @click.native="step = 3">Continue</v-btn>
            <v-btn>Cancel</v-btn>
          </v-stepper-content>
          <v-stepper-content step="3">
            <v-card class="mb-5">
              <v-card-text>
                <div class="small__grid">
                  <div class="grid__left">
                    <div class="hint__text">Your Blood Type</div>
                  </div>
                  <div class="grid__right">
                    <v-radio-group v-model="general.bloodType">
                      <v-radio label="A" value="A"/>
                      <v-radio label="B" value="B"/>
                      <v-radio label="O" value="O"/>
                      <v-radio label="AB" value="AB"/>
                    </v-radio-group>
                  </div>
                  <div class="grid__left">
                    <div class="hint__text">Receive Emails about your schedules, and our
                      newsletter
                    </div>
                  </div>
                  <div class="grid__right">
                    <v-switch label="Receive Emails" v-model="general.receiveEmails"/>
                  </div>
                </div>
              </v-card-text>
            </v-card>
            <v-btn color="primary" @click.native="sendProfileData">Complete Profile</v-btn>
            <v-btn @click="navigateToOverview">Cancel</v-btn>
          </v-stepper-content>
        </v-stepper-items>
      </v-stepper>
    </div>
    <div class="side"></div>
  </div>
</template>

<script>
  import {mapState, mapMutations, mapActions, mapGetters} from 'vuex'

  export default {
    name: "Profile",
    data() {
      return {
        step: 0,
        valid: false,
        personal: {
          firstName: "",
          lastName: "",
          phone: ""
        },
        address: {
          address: ""
        },
        general: {
          bloodType: 1,
          receiveEmails: false
        },
        emailRules: [
          v => !!v || 'E-mail is required',
          v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E-mail must be valid'
        ]
      }
    },
    computed: {
      ...mapGetters(['email', 'firstName', 'lastName'])
    },
    methods: {
      ...mapActions(['updateProfile']),
      sendProfileData() {
        let object = {
          personal: this.personal,
          address: this.address,
          general: this.general
        }
        console.log('sending the new profile data ', object)
        this.updateProfile(object)
      },
      navigateToOverview() {
        this.$router.push('overview')
      }
    },
    mounted() {
      this.personal.firstName = this.$store.getters.firstName
      this.personal.lastName = this.$store.getters.lastName
    }
  }
</script>

<style scoped>
  @import "../assets/css/vuetify.min.css";

  .wrapper {
    width: 100vw;
    display: grid;
    grid-template-columns: 1fr 80vw 1fr;
  }

  .stepper {
    width: 100%;
  }

  .small__grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    grid-template-rows: 1fr 1fr;
  }

  .small__grid .grid__right {
    grid-column: 2 / 3;
  }

  .small__grid .grid__left {
    grid-column: 1 / 2;
  }

  .hint__text, .rspan1 {
    grid-row: 1 / 2;
  }
</style>
