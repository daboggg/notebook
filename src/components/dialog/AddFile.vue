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

      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
        @submit.prevent
      >
        <v-file-input
          color="amber"
          class="mx-5 mt-5"
          v-model="files"
          :rules="filesRules"
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
      </v-form>

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
const maxFileSize = +process.env.VUE_APP_MAXFILESIZE
export default {
  name: 'AddFile',
  props: ['noteId'],
  data: () => ({
    dialog: false,
    valid: true,
    files: [],
    filesRules: [
      v => v.length > 0 || 'поле не может быть пустым',
      v => {
        for (let item of v) {
          if (item.size > maxFileSize) {
            return `один или несколько фалов больше ${maxFileSize}Kb`
          }
        }
        return true
      }
    ]
  }),
  methods: {
    async validate () {
      if (this.$refs.form.validate()) {
        try {
          await this.$store.dispatch('addFiles', {
            files: this.files, noteId: this.noteId
          }).then(res => {
            this.files = []
            this.$refs.form.reset()
            this.$store.commit('setMessage', 'file or files uploaded')
          })
        } catch (e) {}
        this.dialog = false
      }
    }
  }
}
</script>

<style scoped>

</style>
