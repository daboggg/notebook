<template>
<v-app>
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
  <navbar :drawerCtrl="drawerCtrl"/>
  <sidebar :drawer="drawer" v-on:drawerCtrl="drawerCtrl"/>
  <footerbar/>
  <router-view/>
</v-app>
</template>

<script>
import Navbar from '../components/app/Navbar'
import Sidebar from '../components/app/Sidebar'
import Footerbar from '../components/app/Footer'
export default {
  name: 'MainLayout',
  data: () => ({
    drawer: null,
    color: 'primary',
    mode: '',
    snackbar: false,
    text: 'Hello, I\'m a snackbar',
    timeout: 6000,
    x: 'right',
    y: 'top'
  }),
  methods: {
    drawerCtrl () {
      this.drawer = !this.drawer
    }
  },
  components: {
    Navbar, Sidebar, Footerbar
  },
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
