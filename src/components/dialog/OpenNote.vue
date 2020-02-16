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
            <v-card elevation="5" class="mt-3">
              <v-card-title>Имя записи:</v-card-title>
              <v-card-text class="subtitle-1">{{ editParam.title }}</v-card-text>
              <v-divider class="my-3"/>
              <v-card-title>Текст записи:</v-card-title>
              <v-card-text class="subtitle-1">{{ editParam.text }}</v-card-text>
            </v-card>
<!--            <div class="title"></div>-->
<!--            <div class="my-4 ml-10 subtitle-1">{{ editParam.title }}</div>-->
<!--            <v-divider></v-divider>-->
<!--            <div class="title">Текст записи:</div>-->
<!--            <div class="my-4 ml-10 subtitle-1">{{ editParam.text }}</div>-->
            <v-divider></v-divider>

<!--            <v-list-->
<!--              nav-->
<!--              dense-->
<!--              class="mt-4"-->
<!--            >-->
<!--              <v-list-item-group v-model="item" color="primary">-->
<!--                <v-list-item-->
<!--                  v-for="(file, i) in files"-->
<!--                  :key="i"-->
<!--                  @click="downloadFile(file.id, file.fileName)"-->
<!--                >-->
<!--                  <v-list-item-icon>-->
<!--                    <v-icon>mdi-file-document-box</v-icon>-->
<!--                  </v-list-item-icon>-->

<!--                  <v-list-item-content>-->
<!--                    <v-list-item-title v-text="file.fileName"></v-list-item-title>-->
<!--                  </v-list-item-content>-->
<!--                </v-list-item>-->
<!--              </v-list-item-group>-->
<!--            </v-list>-->
            <file-table class="mt-6" :files="files"/>
          </v-sheet>

        </v-col>
      </v-row>
      <progress-loading/>
    </v-card>
  </v-dialog>
</template>

<script>
import ProgressLoading from '../ProgressLoading'
import FileTable from '../table/FileTable'
export default {
  name: 'OpenNote',
  props: ['editParam'],
  data: () => ({
    dialog: false,
    item: 0,
    skill: 20
  }),
  methods: {
    async getFiles () {
      await this.$store.dispatch('getAllFiles', this.editParam.noteId)
    }
    // async downloadFile (fileId, fileName) {
    //   await this.$store.dispatch('downloadFile', { fileId, fileName })
    // }
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
  components: { ProgressLoading, FileTable },
  computed: {
    files () {
      return this.$store.getters.getFiles
    }
  }
}
</script>

<style scoped>

</style>
