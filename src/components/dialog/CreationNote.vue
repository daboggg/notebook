<template>
  <v-dialog
    width="500"
    v-model="dialog"
    persistent
  >

    <template v-slot:activator="{ on }">
      <v-btn
        small
        v-on="on"
      >
        <v-icon class="mr-2">mdi-note-plus</v-icon>Новая запись
      </v-btn>
    </template>

    <v-card elevation="12">
      <v-app-bar
        dense
      >
        <v-toolbar-title class="title">Добавить запись</v-toolbar-title>
        <v-spacer/>
        <v-icon @click="dialog = false">close</v-icon>
      </v-app-bar>
      <v-form
        ref="form"
        v-model="valid"
        lazy-validation
        @submit.prevent
      >
        <v-text-field
          v-model="title"
          :rules="titleRules"
          type="text"
          label="Введите название записи"
          prepend-icon="mdi-note"
          outlined
          elevation="12"
          class="ma-5 mb-0 mt-10"
          color="amber"
          required
        ></v-text-field>

        <v-textarea
          v-model="text"
          :rules="textRules"
          type="text"
          label="Введите текст записи"
          prepend-icon="mdi-text-subject"
          outlined
          elevation="12"
          class="ma-5 mb-0 mt-10"
          color="amber"
          required
        ></v-textarea>

      </v-form>
      <v-card-actions>
        <v-spacer></v-spacer>
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
  name: 'CreationNote',
  props: ['notebookId'],
  data: () => ({
    dialog: false,
    valid: true,
    title: '',
    text: '',
    titleRules: [
      v => !!v || 'поле не может быть пустым'
    ],
    textRules: [
      v => !!v || 'поле не может быть пустым'
    ]
  }),
  methods: {
    async validate () {
      if (this.$refs.form.validate()) {
        try {
          await this.$store.dispatch('createNote', { notebookId: this.notebookId, title: this.title, text: this.text })
          this.$refs.form.reset()
          this.$refs.form.resetValidation()
          this.dialog = false
        } catch (e) {
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
