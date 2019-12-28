<template>
  <v-navigation-drawer
    v-model="drawer"
    app
    clipped
    temporary
  >
    <v-list dense>
      <v-list-item
        link
        v-for="(item, idx) in items"
        :key="idx"
        :to="item.to"
        @click="drawerCtrl"
      >
        <v-list-item-action>
          <v-icon>{{item.icon}}</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>{{item.text}}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
export default {
  name: 'Sidebar',
  props: ['drawer', 'drawerCtrl'],
  data: () => ({
    items: [
      { text: 'добавить записку', icon: 'mdi-check-outline', to: '/addnote' },
      { text: 'домой', icon: 'mdi-check-outline', to: '/' },
      { text: 'регистрация', icon: 'mdi-check-outline', to: '/register' }
    ]
  })
}
</script>

<style scoped>

</style>
