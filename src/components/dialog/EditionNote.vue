<template>
  <v-dialog
    width="500"
    v-model="dialog"
    persistent
  >

    <template v-slot:activator="{ on }">
      <v-btn
        text
        v-on="on"
      >
        <v-icon>mdi-pen</v-icon>
      </v-btn>
    </template>

    <v-card elevation="12">
      <v-app-bar
        dense
      >
        <v-toolbar-title class="title">Редактировать запись</v-toolbar-title>
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
          prepend-icon="mdi-pen"
          outlined
          elevation="12"
          class="mx-5 mt-10"
          color="amber"
          required
        ></v-text-field>

        <v-textarea
          v-model="text"
          :rules="textRules"
          type="text"
          label="Введите текст записи"
          prepend-icon="mdi-pen"
          outlined
          elevation="12"
          class="mx-5"
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
          Редактировать
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'EditionNote',
  props: ['editParam'],
  data: () => ({
    dialog: false,
    valid: true,
    title: '',
    titleRules: [
      v => !!v || 'поле не может быть пустым'
    ],
    text: '',
    textRules: [
      v => !!v || 'поле не может быть пустым'
    ]
  }),
  methods: {
    async validate () {
      if (this.$refs.form.validate()) {
        try {
          await this.$store.dispatch('editNote', {
            noteId: this.editParam.noteId,
            title: this.title,
            text: this.text
          })
          // this.$refs.form.reset()
          this.$refs.form.resetValidation()
          this.dialog = false
        } catch (e) {
        }
      }
    }
  },
  mounted () {
    this.title = this.editParam.title
    this.text = this.editParam.text
  }
}
</script>

<style scoped>

</style>
