<template>
  <v-card elevation="5">
    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="files"
      item-key="id"
      show-select
      class="elevation-1"
      :mobile-breakpoint="450"
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <v-toolbar-title>Список файлов:</v-toolbar-title>
          <v-divider
            class="mx-4"
            inset
            vertical
          ></v-divider>
          <v-spacer></v-spacer>
          <v-icon
            large
            :disabled="!(selected.length > 0)"
            class="mr-2 mt-2"
            @click="deleteFiles(...selected)"
          >
            delete
          </v-icon>
        </v-toolbar>
      </template>
      <template v-slot:item.action="{ item }">
        <v-icon
          class="mr-2 mt-2"
          @click="downloadFile(item.id, item.fileName)"
        >
          mdi-download
        </v-icon>
        <v-icon
          class="mr-2 mt-2"
          @click="dialog = true; editedItem = { ...item }"
        >
          edit
        </v-icon>
        <v-icon
          class="mr-2 mt-2"
          @click="deleteFiles(item)"
        >
          delete
        </v-icon>
      </template>
    </v-data-table>

    <v-dialog v-model="dialog" max-width="500px">
      <v-card>
        <v-card-title>
          <span class="headline">Изменить название файла</span>
        </v-card-title>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="10" md="8">
                <v-text-field v-model="editedItem.fileName" label="Название файла"></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="changeFileName">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
export default {
  name: 'FileTable',
  props: ['files'],
  data: () => ({
    editedItem: {},
    dialog: false,
    selected: [],
    headers: [
      {
        text: 'Имя файла',
        align: 'left',
        sortable: false,
        value: 'fileName'
      },
      { align: 'right', value: 'action', sortable: false }
    ]
  }),
  methods: {
    async changeFileName () {
      await this.$store.dispatch('changeFileName', this.editedItem)
      this.dialog = false
    },
    async downloadFile (fileId, fileName) {
      await this.$store.dispatch('downloadFile', { fileId, fileName })
    },
    async deleteFiles (...files) {
      console.log(files)
      confirm('Подтвердите удаление') &&
        await this.$store.dispatch('deleteFiles', files.map(file => file.id))
      this.selected = []
    }
  }
}
</script>

<style scoped>

</style>
