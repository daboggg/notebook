<template>
  <v-content>
    <v-file-input
      label="File input"
      v-model="file"
    ></v-file-input>
    <v-btn @click="send">Send</v-btn>
  </v-content>
</template>

<script>
import Vue from 'vue'
export default {
  name: 'Test',
  data: () => ({
    file: []
  }),
  methods: {
    send () {
      var formData = new FormData()
      formData.append('file', this.file)
      Vue.http.post('http://localhost:8080/api/file', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        uploadProgress: function (e) {
          console.log(e.lengthComputable)
          console.log(e.loaded)
        }
      }).then(resp => {
        console.log(resp)
      })
      console.log(this.file.name)
    }
  }
}
</script>

<style scoped>

</style>
