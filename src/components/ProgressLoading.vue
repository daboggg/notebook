<template>
    <v-dialog
      v-model="showProgress"
      persistent
      class="ma-12"
    >
<!--      <template v-slot:activator="{ on }">-->
<!--        <v-btn-->
<!--          text-->
<!--          v-on="on"-->
<!--        >-->
<!--          <span class="grey&#45;&#45;text text&#45;&#45;darken-2">открыть</span>-->
<!--        </v-btn>-->
<!--      </template>-->

      <v-row
        align="center"
        justify="center"
      >
        <v-col
          cols="12"
          sm="10"
          md="8"
        >
          <v-progress-linear
            color="blue-grey"
            height="25"
            reactive
            :value="loaded"
            class="mx-4"
          >
            <template v-slot="{ value }">
              <strong>{{ Math.ceil(value) }}%</strong>
            </template>
          </v-progress-linear>
        </v-col>
      </v-row>
    </v-dialog>
</template>

<script>
export default {
  name: 'ProgressLoading',
  computed: {
    showProgress () {
      return this.$store.getters.showProgress
    },
    loaded () {
      return this.$store.getters.loaded
    }
  }
}
</script>

<style scoped>

</style>
