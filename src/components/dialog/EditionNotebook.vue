<template>
  <v-dialog
    width="500"
    v-model="dialog"
    persistent
  >

    <template v-slot:activator="{ on }">
      <v-icon class="mt-2" @click.stop="" large v-on="on">mdi-pen</v-icon>
    </template>

    <v-card elevation="12">
      <v-app-bar
        dense

      >
        <v-toolbar-title class="title">Редактировать блокнот</v-toolbar-title>
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
          v-model="notebookName"
          :rules="notebookNameRules"
          type="text"
          label="Введите название блокнота"
          prepend-icon="mdi-notebook"
          outlined
          elevation="12"
          class="ma-5 mb-0 mt-10"
          color="amber"
          required
        ></v-text-field>

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
  name: 'EditionNotebook',
  props: ['editParam'],
  data: () => ({
    dialog: false,
    valid: true,
    notebookName: '',
    notebookNameRules: [
      v => !!v || 'поле не может быть пустым',
      v => (v && v.length > 3) || 'название блокнота не может быть короче 4 символов'
    ]
  }),
  methods: {
    async validate () {
      if (this.$refs.form.validate()) {
        try {
          await this.$store.dispatch('editNotebook', {
            id: this.editParam.id,
            notebookName: this.notebookName
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
    this.notebookName = this.editParam.notebookName
  }
}
</script>

<style scoped>

</style>
