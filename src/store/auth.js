import Vue from 'vue'
import router from '../router'
const ipEndPort = process.env.VUE_APP_SERVERIPENDPORT
export default {
  state: {
    username: null,
    token: null
  },
  mutations: {
    login (state, login) {
      state.username = login.username
      state.token = login.token
      sessionStorage.setItem('username', login.username)
      sessionStorage.setItem('token', login.token)
    },
    logout (state) {
      state.username = null
      state.token = null
      sessionStorage.removeItem('username')
      sessionStorage.removeItem('token')
    }
  },
  actions: {
    async register ({ commit }, { email, password, username }) {
      try {
        const res = await Vue.http.post(`${ipEndPort}api/user/register`,
          JSON.stringify({ email: email, password: password, username: username }),
          { 'Content-Type': 'application/json' })
        const data = await res.json()
        commit('login', { username: data.username, token: data.token })
        await router.push('/')
        commit('setMessage', 'registration successful')
      } catch (e) {
        commit('setError', e.body.message)
        // console.log(e.body.message)
        throw e
      }
    },
    async login ({ commit }, { email, password }) {
      try {
        const res = await Vue.http.post(`${ipEndPort}api/user/login`,
          JSON.stringify({ email: email, password: password }),
          { 'Content-Type': 'application/json' })
        const data = await res.json()
        commit('login', { username: data.username, token: data.token })
        await router.push('/')
      } catch (e) {
        commit('setError', e.body.message)
        console.log(e.body.message)
        throw e
      }
    }
  },
  getters: {
    getUsername: state => state.username,
    getToken: state => state.token
  }
}
