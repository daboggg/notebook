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
            // console.log(e.lengthComputable)
            // console.log(e.loaded)
          }
        })
        commit('showProgress', false)
        let data = await res.json()
        console.log(data)
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
            // console.log(event.lengthComputable)
            // console.log(event.loaded)
            // console.log(event.total)
            // console.log(event.loaded / (event.total / 100))
          }
        })
        commit('showProgress', false)
        // console.log(res.headers)

        let url = window.URL.createObjectURL(new Blob([res.data]))
        let link = document.createElement('a')
        link.href = url
        link.setAttribute('download', fileName)
        document.body.appendChild(link)
        link.click()

        // let blob = new Blob([res.data], { type: res.headers.get('content-type') })
        // let link = document.createElement('a')
        // link.href = window.URL.createObjectURL(blob)
        // link.download = 'ghjk'
        // link.click()

        // console.log(blob)
        // let data = await res.json()
        // commit('setFiles', data)
        // console.log(res)
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
