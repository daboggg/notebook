<template>
    <v-container
      class="fill-height mt-12"
      fluid
    >
      <v-row
        align="center"
        justify="center"
      >
        <v-col
          cols="12"
          md="9"
          sm="10"
        >
          <v-row>
            <v-col
              align="right"
              justify="center"
              class="mr-4"
            >
              <v-btn @click="showDialogCreateNotebook = !showDialogCreateNotebook">
                <v-icon class="mr-2">mdi-notebook</v-icon>Новый блокнот
              </v-btn>
            </v-col>
          </v-row>
          <v-expansion-panels
            focusable
            popout
          >
            <v-expansion-panel
              v-for="(item,i) in 20"
              :key="i"
            >
              <v-expansion-panel-header
                class="title"
              >
                <div>
                  <v-icon large class="mr-3">mdi-notebook</v-icon>
                  Lorem ipsum dolor sit amet, consectetur adipisicing elit. Magnam, nostrum.
                </div>
                <div>
                  <v-icon large class="">mdi-pen</v-icon>
                  <v-icon large class="mt-2">mdi-delete</v-icon>
                </div>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
          <creation-notebook
            :showDialogCreateNotebook="showDialogCreateNotebook"
            v-on:dialog="showDialogCreateNotebook = !showDialogCreateNotebook"
          />
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
import CreationNotebook from '../components/dialog/CreationNotebook'
export default {
  name: 'Notebooks',
  data: () => ({
    showDialogCreateNotebook: false
  }),
  methods: {
  },
  components: {
    CreationNotebook
  }
}
</script>

<style scoped>
</style>
