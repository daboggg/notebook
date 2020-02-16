import Vue from 'vue'
import router from '../router'
const ipEndPort = process.env.VUE_APP_SERVERIPENDPORT
export default {
  state: {
    notes: []
  },
  mutations: {
    addNote (state, note) {
      state.notes.push(note)
    },
    setNotes (state, notes) {
      state.notes = notes
    },
    editNote (state, note) {
      let index = state.notes.findIndex(n => n.id === note.id)
      state.notes.splice(index, 1, note)
    },
    deleteNote (state, note) {
      let index = state.notes.findIndex(n => n.id === note.id)
      state.notes.splice(index, 1)
    }
  },
  actions: {
    async getAllNotes ({ commit, getters }, notebookId) {
      try {
        const res = await Vue.http.get(`${ipEndPort}api/note?notebookId=${notebookId}`,
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        const data = await res.json()
        commit('setNotes', data)
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
    async createNote ({ commit, getters }, { notebookId, title, text }) {
      try {
        const res = await Vue.http.post(`${ipEndPort}api/note`,
          JSON.stringify({
            notebookId, title, text
          }),
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        const data = await res.json()
        commit('addNote', data)
        commit('setMessage', 'note added')
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
    async editNote ({ commit, getters }, { noteId, title, text }) {
      try {
        const res = await Vue.http.put(`${ipEndPort}api/note`,
          JSON.stringify({
            id: noteId, title, text
          }),
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        const data = await res.json()
        commit('editNote', data)
        commit('setMessage', 'note edited')
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
    async deleteNote ({ commit, getters }, id) {
      try {
        let res = await Vue.http.delete(`${ipEndPort}api/note/${id}`,
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        let data = await res.json()
        commit('deleteNote', data)

        commit('setMessage', 'note deleted')
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
    getNotes: state => state.notes
  }
}
