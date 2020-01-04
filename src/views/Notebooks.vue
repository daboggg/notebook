<template>
    <v-container
      class="fill-height mt-12"
      fluid
    >
      <v-row
        align="center"
        justify="center"
      >
        <v-col
          cols="12"
          md="9"
          sm="10"
        >
          <v-row>
            <v-col
              align="right"
              justify="center"
              class="mr-4"
            >
              <v-btn @click="showDialogCreateNotebook = !showDialogCreateNotebook">
                <v-icon class="mr-2">mdi-notebook</v-icon>Новый блокнот
              </v-btn>
            </v-col>
          </v-row>
          <v-expansion-panels
            focusable
            popout
          >
            <v-expansion-panel
              v-for="(notebook,i) in notebooks"
              :key="i"
              @click="openNotebook(notebook.id)"
            >
              <v-expansion-panel-header
                class="title"
              >
                <div>
                  <v-icon large class="mr-3">mdi-notebook</v-icon>
                  {{notebook.notebookName}}
                </div>
                <v-spacer/>
                <div align="right" justify="right">
                  <v-icon large class="mt-2" @click.stop="edit(notebook.id, notebook.notebookName)">mdi-pen</v-icon>
                  <v-icon large class="mt-2">mdi-delete</v-icon>
                </div>
              </v-expansion-panel-header>
              <v-expansion-panel-content>
                <note :notes="notes" :notebookId="notebook.id"/>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
          <creation-notebook
            :showDialogCreateNotebook="showDialogCreateNotebook"
            v-on:dialog="showDialogCreateNotebook = !showDialogCreateNotebook"
          />
          <edition-notebook
            :editParam="editParam"
            :showDialogEditNotebook="showDialogEditNotebook"
            v-on:dialog="showDialogEditNotebook = !showDialogEditNotebook"
          />
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
import CreationNotebook from '../components/dialog/CreationNotebook'
import EditionNotebook from '../components/dialog/EditionNotebook'
import Note from '../components/Note'
export default {
  name: 'Notebooks',
  data: () => ({
    showDialogCreateNotebook: false,
    showDialogEditNotebook: false,
    editParam: {}
  }),
  methods: {
    edit (id, notebookName) {
      this.editParam = { id, notebookName }
      this.showDialogEditNotebook = !this.showDialogEditNotebook
    },
    async openNotebook (notebookId) {
      await this.$store.dispatch('getAllNotes', notebookId)
    }
  },
  mounted () {
    this.$store.dispatch('getAllNotebooks')
  },
  computed: {
    notebooks () {
      return this.$store.getters.getAllNotebooks
    },
    notes () {
      return this.$store.getters.getNotes
    }
  },
  components: {
    CreationNotebook, EditionNotebook, Note
  }
}
</script>

<style scoped>
</style>
