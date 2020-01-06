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

          <v-btn text>
            <v-icon>mdi-delete</v-icon>
          </v-btn>

          <v-spacer></v-spacer>

          <open-note :editParam="{ noteId:note.id, title:note.title, text:note.text }"/>

        </v-card-actions>
      </v-card>
    </v-row>
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
    editParam: {}
  }),
  components: {
    CreationNote, EditionNote, OpenNote
  }
}
</script>

<style scoped>

</style>
