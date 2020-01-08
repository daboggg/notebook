<template>
  <v-dialog
    width="500"
    v-model="dialog"
    persistent
  >

    <template v-slot:activator="{ on }">
      <v-btn icon v-on="on">
        <v-icon>mdi-file-document-box-plus</v-icon>
      </v-btn>
    </template>

    <v-card elevation="12">
      <v-app-bar
        dense

      >
        <v-toolbar-title class="title">Добавить файл</v-toolbar-title>
        <v-spacer/>
        <v-icon @click="dialog = false">close</v-icon>
      </v-app-bar>

      <v-file-input
        color="amber"
        class="mx-5 mt-5"
        v-model="files"
        counter
        label="File input"
        multiple
        placeholder="Выберите ваши файлы"
        prepend-icon="mdi-paperclip"
        outlined
        :show-size="1000"
      >
        <template v-slot:selection="{ index, text }">
          <v-chip
            v-if="index < 2"
            color="amber"
            label
            small
          >
            {{ text }}
          </v-chip>

          <span
            v-else-if="index === 2"
            class="overline grey--text text--darken-3 mx-2"
          >
        +{{ files.length - 2 }} File(s)
      </span>
        </template>
      </v-file-input>

      <v-card-actions>
        <v-spacer/>
        <v-btn
          class="ma-2"
          tile
          elevation="3"
          type="submit"
          @click="validate"
        >
          Добавить
        </v-btn>
      </v-card-actions>

    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'AddFile',
  props: ['noteId'],
  data: () => ({
    dialog: false,
    files: null
  }),
  methods: {
    async validate () {
      if (this.files) {
        try {
          await this.$store.dispatch('addFiles', {
            files: this.files, noteId: this.noteId
          })
          this.$refs.form.reset()
          this.dialog = false
        } catch (e) {}
      }
      this.dialog = false
    //   if (this.$refs.form.validate()) {
    //     try {
    //       await this.$store.dispatch('editNotebook', {
    //         id: this.editParam.id,
    //         notebookName: this.notebookName
    //       })
    //       this.$refs.form.reset()
    //       this.$refs.form.resetValidation()
    //       this.dialog = false
    //     } catch (e) {
    //     }
    //   }
    }
  },
  mounted () {
    // this.notebookName = this.editParam.notebookName
  }
}
</script>

<style scoped>

</style>
