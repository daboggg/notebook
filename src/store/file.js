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
          uploadProgress: function (e) {
            console.log(e.lengthComputable)
            console.log(e.loaded)
          }
        })
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
    async downloadFile ({ commit, getters }, fileId) {
      try {
        await Vue.http.get(`${ipEndPort}api/file/${fileId}`, {
          headers: {
            'Content-Type': 'application/json',
            'Token': getters.getToken
          }
        })
        // let data = await res.json()
        // commit('setFiles', data)
        // console.log(data)
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
