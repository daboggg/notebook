import Vue from 'vue'
import Vuex from 'vuex'
import auth from './auth'
import notebook from './notebook'
import note from './note'
import file from './file'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    error: null,
    message: null,
    loaded: 0,
    showProgress: false
  },
  mutations: {
    setError (state, error) {
      state.error = error
    },
    clearError (state) {
      state.error = null
    },
    setMessage (state, message) {
      state.message = message
    },
    clearMessage (state) {
      state.message = null
    },
    setLoaded (state, loaded) {
      state.loaded = loaded
    },
    showProgress (state, show) {
      state.showProgress = show
    }
  },
  actions: {
  },
  getters: {
    error: state => state.error,
    message: state => state.message,
    loaded: state => state.loaded,
    showProgress: state => state.showProgress
  },
  modules: {
    auth, notebook, note, file
  }
})
