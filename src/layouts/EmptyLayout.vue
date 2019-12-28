<template>
  <v-app>
    <v-content class="grey darken-2">
      <v-snackbar
        v-model="snackbar"
        :bottom="y === 'bottom'"
        :color="color"
        :left="x === 'left'"
        :multi-line="mode === 'multi-line'"
        :right="x === 'right'"
        :timeout="timeout"
        :top="y === 'top'"
        :vertical="mode === 'vertical'"
      >
        {{ text }}
        <v-btn
          dark
          text
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </v-snackbar>
      <router-view/>
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: 'EmptyLayout',
  data: () => ({
    color: 'primary',
    mode: '',
    snackbar: false,
    text: 'Hello, I\'m a snackbar',
    timeout: 6000,
    x: 'right',
    y: 'top'
  }),
  computed: {
    error () {
      return this.$store.getters.error
    },
    message () {
      return this.$store.getters.message
    }
  },
  watch: {
    error (error) {
      if (error) {
        this.color = 'error'
        this.text = error
        this.snackbar = true
      }
      this.$store.commit('clearError')
    },
    message (message) {
      if (message) {
        this.color = 'success'
        this.text = message
        this.snackbar = true
      }
      this.$store.commit('clearMessage')
    }
  }
}
</script>

<style scoped>

</style>
