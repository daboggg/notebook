<template>
  <v-navigation-drawer
    v-model="openDrawer"
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
        @click="$emit('drawerCtrl')"
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
  props: ['drawer'],
  data: () => ({
    openDrawer: null,
    items: [
      { text: 'блокноты', icon: 'mdi-notebook', to: '/notebooks' },
      { text: 'домой', icon: 'mdi-check-outline', to: '/' }
    ]
  }),
  watch: {
    drawer () {
      this.openDrawer = this.drawer
    }
  }
}
</script>

<style scoped>

</style>
