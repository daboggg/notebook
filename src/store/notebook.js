import Vue from 'vue'
import router from '../router'
const ipEndPort = process.env.VUE_APP_SERVERIPENDPORT
export default {
  state: {

  },
  actions: {
    async createNotebook ({ commit, getters }, notebookName) {
      try {
        console.log(getters.getToken)
        const res = await Vue.http.post(`${ipEndPort}api/notebook/create`,
          JSON.stringify({ notebookName: notebookName }),
          {
            headers: {
              'Content-Type': 'application/json',
              'Token': getters.getToken
            }
          })
        // console.log(res)
        const data = await res.json()
        // commit('login', { username: data.username, token: data.token })
        // await router.push('/')
        // commit('setMessage', 'registration successful')
        console.log(data)
      } catch (e) {
        if (e.body.message === 'invalid token') {
          await router.push('/login')
          commit('logout')
          commit('setError', 'sign in again')
          throw e
        } else {
          commit('setError', e.body.message)
          console.log(e.body.message)
          throw e
        }
      }
    }
  }
}
