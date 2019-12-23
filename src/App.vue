<template>
  <component :is="layout">
    <router-view/>
  </component>
</template>

<script>
import EmptyLayout from './layouts/EmptyLayout'
import MainLayout from './layouts/MainLayout'
export default {
  data: () => ({
    drawer: null
  }),
  components: {
    MainLayout, EmptyLayout
  },
  computed: {
    layout () {
      return (this.$route.meta.layout || 'empty') + '-layout'
    }
  }
}
</script>
