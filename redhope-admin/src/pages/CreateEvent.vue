<template>
  <div class="wrapping__container">
    <section class="head__section">
      <h1 class="display_1"> Create a New Event </h1>
    </section>
    
    <section class="editor__section">
      <wysiwyg v-model="content" />
    </section>
    <section class="action__section">
      <v-btn color="primary"> Cancel </v-btn>
      <v-btn color="success" @click="publish"> Save and Publish </v-btn>
    </section>
    <v-snackbar
      v-model="snackbar"
      :bottom="true"
      :timeout="6000"
    >
      {{ message }}
      <v-btn
        color="pink"
        flat
        @click="snackbar = false"
      >
        Close
      </v-btn>
    </v-snackbar>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  data() {
    return {
      content: '',
      snackbar: false,
      message: ''
    }
  },
  methods: {
    ...mapActions(['publishEvent']),
    publish() {
      this.publishEvent({content: this.content})
        .then(val => {
          this.snackbar = true
          this.message = 'Event Created'
        }).catch(err => {
          this.snackbar = true
          this.message = 'Please try later, could not create Event'
        })
    }
  },
  
}
</script>
<style>
@import "vue-wysiwyg/dist/vueWysiwyg.css";
.head__section, 
.editor__section,
.action__section {
  padding: 2rem 1rem;
}
.action__section {
  text-align: right;
}
button.btn.primary {
  margin: 0;
}
.display_1 {
  font-size: 2rem;
}
</style>

