<template>
  <v-app-bar
    app
    color="amber"
    dense
    clipped-left
  >
    <v-app-bar-nav-icon @click.stop="drawerCtrl" />
    <v-toolbar-title class="title">Notebook</v-toolbar-title>
    <v-spacer/>
    <v-menu
      transition="slide-y-transition"
      bottom
    >
      <template v-slot:activator="{ on }">
        <v-chip class="title" color="amber" v-on="on">{{username}}</v-chip>
      </template>
      <v-list>
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          :to="item.link"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

  </v-app-bar>
</template>

<script>
export default {
  name: 'Navbar',
  props: ['drawerCtrl'],
  data: () => ({
    items: [
      { title: 'Профиль', link: '/profile' },
      { title: 'Выйти', link: '/login?message=you are out' }
    ]
  }),
  computed: {
    username () {
      return this.$store.getters.getUsername
    }
  }
}
</script>

<style scoped>

</style>
