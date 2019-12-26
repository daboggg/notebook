import Vue from 'vue'
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
        const res = await Vue.http.post('http://localhost:8080/user/register',
          JSON.stringify({ email: email, password: password, username: username }),
          { 'Content-Type': 'application/json' })
        const data = await res.json()
        commit('login', { username: data.username, token: data.token })
        console.log(data)
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
