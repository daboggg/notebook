<template>
  <div>
    <v-row>
      <v-col class="mt-3" align="right" justify="right">
        <creation-note :notebookId="notebookId"/>
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
          {{note.title.length>13? note.title.substring(0,12) + '...' : note.title}}
        </v-card-title>

        <v-card-subtitle>
          {{note.text.length>19? note.text.substring(0,18) + '...' : note.text}}
        </v-card-subtitle>

        <v-card-actions>

          <edition-note :editParam="{ noteId:note.id, title:note.title, text:note.text }"/>

          <v-btn icon @click="deleteNote(note.id)">
            <v-icon>mdi-delete</v-icon>
          </v-btn>

<!--          <v-btn icon>-->
<!--            <v-icon>mdi-file-document-box-plus</v-icon>-->
<!--          </v-btn>-->
          <add-file :noteId="note.id"/>

          <v-spacer></v-spacer>

          <open-note :editParam="{ noteId:note.id, title:note.title, text:note.text }"/>

        </v-card-actions>
        <progress-loading/>
      </v-card>
    </v-row>
  </div>
</template>

<script>
import CreationNote from './dialog/CreationNote'
import EditionNote from './dialog/EditionNote'
import OpenNote from './dialog/OpenNote'
import AddFile from './dialog/AddFile'
import ProgressLoading from './ProgressLoading'
import Vue from 'vue'
const ipEndPort = process.env.VUE_APP_SERVERIPENDPORT
export default {
  name: 'Note',
  props: ['notebookId', 'notes'],
  data: () => ({
    editParam: {}
  }),
  components: {
    CreationNote, EditionNote, OpenNote, AddFile, ProgressLoading
  },
  methods: {
    async deleteNote (noteId) {
      let res = await Vue.http.get(`${ipEndPort}api/file/check?noteId=${noteId}`)
      let isExist = await res.json()
      if (isExist) {
        confirm('У этой записи список файлов не пуст, все равно удалить?') &&
        await this.$store.dispatch('deleteAllFilesByNoteId', noteId)
        await this.$store.dispatch('deleteNote', noteId)
      } else {
        console.log('net')
        confirm('Подтвердите удаление') &&
        await this.$store.dispatch('deleteNote', noteId)
      }
      // console.log(isExist);
    }
  }
}
</script>

<style scoped>

</style>
