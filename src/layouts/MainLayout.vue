<template>
<v-app>
  <navbar :drawerCtrl="drawerCtrl"/>
  <sidebar :drawer="drawer" :drawerCtrl="drawerCtrl"/>
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
    drawer: null
  }),
  methods: {
    drawerCtrl () {
      this.drawer = !this.drawer
    }
  },
  components: {
    Navbar, Sidebar, Footerbar
  }
}
</script>

<style scoped>
</style>
