<template>
  <div>
    <v-row>
      <v-col class="mt-3" align="right" justify="right">
        <v-btn small @click="showDialogCreateNote = !showDialogCreateNote">
          <v-icon class="mr-2">mdi-note-plus</v-icon>Новая запись
        </v-btn>
      </v-col>
    </v-row>
    <v-row
      class="d-flex flex-wrap justify-center"
    >
      <v-card
        class="ma-4"
        width="250"
        v-for="(note, idx) in notes"
        :key="idx"
        elevation="5"
      >
        <v-img
          src="https://cdn.vuetifyjs.com/images/cards/sunshine.jpg"
          height="150px"
        ></v-img>

        <v-card-title>
          {{note.title}}
        </v-card-title>

        <v-card-subtitle>
          {{note.text}}
        </v-card-subtitle>

        <v-card-actions>
          <v-btn text @click="edit(note.id, note.title, note.text)">
            <v-icon>mdi-pen</v-icon>
          </v-btn>

          <v-btn text>
            <v-icon>mdi-delete</v-icon>
          </v-btn>

          <v-spacer></v-spacer>

          <v-btn text @click="showDialogOpenNote = !showDialogOpenNote">
            открыть
          </v-btn>

        </v-card-actions>
      </v-card>
    </v-row>
    <creation-note
      :showDialogCreateNote="showDialogCreateNote"
      v-on:dialog="showDialogCreateNote = !showDialogCreateNote"
      :notebookId="notebookId"
    />
    <edition-note
      :showDialogEditNote="showDialogEditNote"
      v-on:dialog="showDialogEditNote = !showDialogEditNote"
      :editParam="editParam"
    />
    <open-note
      :showDialogOpenNote="showDialogOpenNote"
      v-on:dialog="showDialogOpenNote = !showDialogOpenNote"
    />
  </div>
</template>

<script>
import CreationNote from './dialog/CreationNote'
import EditionNote from './dialog/EditionNote'
import OpenNote from './dialog/OpenNote'
export default {
  name: 'Note',
  props: ['notebookId', 'notes'],
  data: () => ({
    showDialogCreateNote: false,
    showDialogEditNote: false,
    showDialogOpenNote: false,
    editParam: {}
  }),
  methods: {
    edit (noteId, title, text) {
      this.editParam = { noteId, title, text }
      this.showDialogEditNote = !this.showDialogEditNote
    }
  },
  components: {
    CreationNote, EditionNote, OpenNote
  }
}
</script>

<style scoped>

</style>
