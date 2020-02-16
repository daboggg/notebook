import Vue from 'vue'
import router from '../router'
const ipEndPort = process.env.VUE_APP_SERVERIPENDPORT
export default {
  state: {
    files: []
  },
  mutations: {
    addFiles (state, files) {
      files.forEach(file => state.files.push(file))
    },
    setFiles (state, files) {
      state.files = files
    },
    changeFileName (state, file) {
      let index = state.files.findIndex(f => f.id === file.id)
      console.log('index: ' + index)
      state.files.splice(index, 1, file)
    },
    deleteFile (state, file) {
      let index = state.files.findIndex(f => f.id === file.id)
      state.files.splice(index, 1)
    }
  },
  actions: {
    async addFiles ({ commit, getters }, { files, noteId }) {
      try {
        let formData = new FormData()
        for (let file of files) {
          formData.append('files', file)
        }
        let res = await Vue.http.post(`${ipEndPort}api/file`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
            'Token': getters.getToken
          },
          params: {
            noteId: noteId
          },
          uploadProgress: function (event) {
            commit('showProgress', true)
            commit('setLoaded', event.loaded / (event.total / 100))
          }
        })
        commit('showProgress', false)
        let data = await res.json()
        return data
      } catch (e) {
        if (e.body.message === 'invalid token') {
          await router.push('/login')
          commit('logout')
          commit('setError', 'sign in again')
          throw e
        } else {
          commit('setError', e.body.message)
          throw e
        }
      }
    },
    async getAllFiles ({ commit, getters }, noteId) {
      try {
        let res = await Vue.http.get(`${ipEndPort}api/file`, {
          headers: {
            'Content-Type': 'application/json',
            'Token': getters.getToken
          },
          params: {
            noteId: noteId
          }
        })
        let data = await res.json()
        commit('setFiles', data)
        console.log(data)
      } catch (e) {
        if (e.body.message === 'invalid token') {
          await router.push('/login')
          commit('logout')
          commit('setError', 'sign in again')
          throw e
        } else {
          commit('setError', e.body.message)
          throw e
        }
      }
    },
    async downloadFile ({ commit, getters }, { fileId, fileName }) {
      try {
        let res = await Vue.http.get(`${ipEndPort}api/file/${fileId}`, {
          headers: {
            'Content-Type': 'application/json',
            'Token': getters.getToken
          },
          responseType: 'blob',
          downloadProgress: function (event) {
            commit('showProgress', true)
            commit('setLoaded', event.loaded / (event.total / 100))
          }
        })
        commit('showProgress', false)

        let url = window.URL.createObjectURL(new Blob([res.data]))
        let link = document.createElement('a')
        link.href = url
        link.setAttribute('download', fileName)
        document.body.appendChild(link)
        link.click()
      } catch (e) {
        if (e.body.message === 'invalid token') {
          await router.push('/login')
          commit('logout')
          commit('setError', 'sign in again')
          throw e
        } else {
          commit('setError', e.body.message)
          throw e
        }
      }
    },
    async changeFileName ({ commit, getters }, editedItem) {
      try {
        let res = await Vue.http.put(`${ipEndPort}api/file`,
          JSON.stringify(editedItem),
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        commit('setMessage', 'changed file name')
        let data = await res.json()
        console.log(data)
        commit('changeFileName', data)
        return data
      } catch (e) {
        if (e.body.message === 'invalid token') {
          await router.push('/login')
          commit('logout')
          commit('setError', 'sign in again')
          throw e
        } else {
          commit('setError', e.body.message)
          throw e
        }
      }
    },
    async deleteFiles ({ commit, getters }, filesId) {
      try {
        for (let id of filesId) {
          let res = await Vue.http.delete(`${ipEndPort}api/file/${id}`,
            {
              headers: {
                'Content-Type': 'application/json',
                'Token': getters.getToken
              }
            })
          let data = await res.json()
          commit('deleteFile', data)
        }
        commit('setMessage', 'file or files deleted')
        // let data = await res.json()
        // console.log(data)
        // return data
      } catch (e) {
        if (e.body.message === 'invalid token') {
          await router.push('/login')
          commit('logout')
          commit('setError', 'sign in again')
          throw e
        } else {
          commit('setError', e.body.message)
          throw e
        }
      }
    },
    async deleteAllFilesByNoteId ({ commit, getters }, noteId) {
      try {
        let res = await Vue.http.delete(`${ipEndPort}api/file/all/${noteId}`,
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        let data = await res.json()
        // let data = await res.json()
        // console.log(data)
        return data
      } catch (e) {
        if (e.body.message === 'invalid token') {
          await router.push('/login')
          commit('logout')
          commit('setError', 'sign in again')
          throw e
        } else {
          commit('setError', e.body.message)
          throw e
        }
      }
    }
  },
  getters: {
    getFiles: state => state.files
  }
}
