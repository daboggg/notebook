<template>
  <v-dialog
    width="500"
    v-model="dialog"
  >
    <v-card elevation="12">
      <v-app-bar
        dense
      >
<!--        <v-icon class="mr-4">mdi-notebook</v-icon>-->
        <v-toolbar-title class="title">Создать блокнот</v-toolbar-title>
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
          <!--<v-icon left>mdi-notebook</v-icon>--> Создать
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'CreationNotebook',
  props: ['showDialogCreateNotebook'],
  data: () => ({
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
          console.log(this.notebookName)
          await this.$store.dispatch('createNotebook', this.notebookName)
          // this.notebookName = ''
          this.$refs.form.reset()
          this.$refs.form.resetValidation()
          this.$emit('dialog')
        } catch (e) {
        }
      }
    }
  },
  computed: {
    dialog () {
      return this.showDialogCreateNotebook
    }
  }
}
</script>

<style scoped>

</style>
