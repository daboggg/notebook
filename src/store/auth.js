import Vue from 'vue'
export default {
  actions: {
    async register ({ commit }, { email, password, username }) {
      try {
        const res = await Vue.http.post('http://localhost:8080/user/register',
          JSON.stringify({ email: email, password: password, username: username }),
          { 'Content-Type': 'application/json' })
        console.log(res)
      } catch (e) {
        console.log(e)
      }
    }
  }
}
