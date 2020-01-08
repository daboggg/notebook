<template>
  <v-dialog
    v-model="dialog"
    persistent
  >

    <template v-slot:activator="{ on }">
      <v-btn
        text
        v-on="on"
        @click="getFiles"
      >
        <span class="grey--text text--darken-2">открыть</span>
      </v-btn>
    </template>

    <v-card elevation="12">
      <v-app-bar
        dense
      >
        <v-toolbar-title class="title">Запись</v-toolbar-title>
        <v-spacer/>
        <v-icon @click="dialog = false">close</v-icon>
      </v-app-bar>

      <v-row align="center">
        <v-col cols="12" md="5" class="pa-10">
          <v-card elevation="5">
            <v-img
              contain
              src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
              class=""
            ></v-img>
          </v-card>
        </v-col>
        <v-col cols="12" md="7" class="pa-10">
          <v-sheet
            elevation="5"
            width="100%"
            color="gray darken-2"
            class="pa-5"
          >
            <div class="title">Имя записи:</div>
            <div class="my-4 ml-10 subtitle-1">{{ editParam.title }}</div>
            <v-divider></v-divider>
            <div class="title">Текст записи:</div>
            <div class="my-4 ml-10 subtitle-1">{{ editParam.text }}</div>
            <v-divider></v-divider>
            <div class="title">Список файлов:</div>

            <v-list
              nav
              dense
              class="mt-4"
            >
              <v-list-item-group v-model="item" color="primary">
                <v-list-item
                  v-for="(file, i) in files"
                  :key="i"
                  @click="downloadFile(file.id)"
                >
                  <v-list-item-icon>
                    <v-icon>mdi-file-document-box</v-icon>
                  </v-list-item-icon>

                  <v-list-item-content>
                    <v-list-item-title v-text="file.fileName"></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>

          </v-sheet>
        </v-col>
      </v-row>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'OpenNote',
  props: ['editParam'],
  data: () => ({
    dialog: false,
    item: 0
  }),
  methods: {
    async getFiles () {
      await this.$store.dispatch('getAllFiles', this.editParam.noteId)
    },
    async downloadFile (fileId) {
      await this.$store.dispatch('downloadFile', fileId)
    }
    // async validate () {
    //   if (this.$refs.form.validate()) {
    //     try {
    //       await this.$store.dispatch('editNotebook', {
    //         id: this.editParam.id,
    //         notebookName: this.notebookName
    //       })
    //       this.$refs.form.reset()
    //       this.$refs.form.resetValidation()
    //       this.$emit('dialog')
    //     } catch (e) {
    //     }
    //   }
    // }
  },
  computed: {
    files () {
      return this.$store.getters.getFiles
    }
  }
}
</script>

<style scoped>

</style>
