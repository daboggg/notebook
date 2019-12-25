import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import 'vuetify/dist/vuetify.min.css'

Vue.config.productionTip = false
Vue.use(VueResource)
Vue.http.options.emulateJSON = true
const http = Vue.http
export default http

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
