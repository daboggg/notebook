import Vue from 'vue'
import router from '../router'
const ipEndPort = process.env.VUE_APP_SERVERIPENDPORT
export default {
  state: {
    notebooks: []
  },
  mutations: {
    setAllNotebooks (state, notebooks) {
      state.notebooks = notebooks
    },
    addNotebook (state, notebook) {
      state.notebooks.push(notebook)
    },
    editNotebook (state, notebook) {
      const idx = state.notebooks.findIndex(n => n.id === notebook.id)
      state.notebooks.splice(idx, 1, notebook)
    },
    deleteNotebook (state, notebook) {
      const idx = state.notebooks.findIndex(n => n.id === notebook.id)
      console.log('index: ' + idx)
      state.notebooks.splice(idx, 1)
    }
  },
  actions: {
    async getAllNotebooks ({ commit, getters }) {
      try {
        const res = await Vue.http.get(`${ipEndPort}api/notebook`,
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        const data = await res.json()
        commit('setAllNotebooks', data)
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
    async createNotebook ({ commit, getters }, notebookName) {
      try {
        const res = await Vue.http.post(`${ipEndPort}api/notebook`,
          JSON.stringify({ notebookName: notebookName }),
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        const data = await res.json()
        commit('addNotebook', data)
        commit('setMessage', 'notepad created')
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
    async editNotebook ({ commit, getters }, { id, notebookName }) {
      try {
        const res = await Vue.http.put(`${ipEndPort}api/notebook/${id}/${notebookName}`,
          JSON.stringify({}),
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        const data = await res.json()
        commit('editNotebook', data)
        commit('setMessage', 'notepad edited')
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
    async deleteNotebook ({ commit, getters }, notebookId) {
      try {
        const res = await Vue.http.get(`${ipEndPort}api/note?notebookId=${notebookId}`,
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        const data = await res.json()
        for (let item of data) {
          console.log(item.id)
          await Vue.http.delete(`${ipEndPort}api/file/all/${item.id}`,
            {
              headers: {
                'Content-Type': 'application/json',
                'Token': getters.getToken
              }
            })
          await Vue.http.delete(`${ipEndPort}api/note/${item.id}`,
            {
              headers: {
                'Content-Type': 'application/json',
                'Token': getters.getToken
              }
            })
        }
        let result = await Vue.http.delete(`${ipEndPort}api/notebook/${notebookId}`,
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        let dat = await result.json()
        commit('deleteNotebook', dat)
        commit('setMessage', 'notebook deleted')
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
    getAllNotebooks: state => state.notebooks
  }
}
