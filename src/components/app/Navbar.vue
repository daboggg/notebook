<template>
  <v-app-bar
    app
    color="amber"
    dense
    clipped-left
  >
    <v-app-bar-nav-icon @click.stop="drawerCtrl" />
    <v-toolbar-title class="title">Notebook</v-toolbar-title>
  </v-app-bar>
</template>

<script>
export default {
  name: 'Navbar',
  props: ['drawerCtrl']
}
</script>

<style scoped>

</style>
