<template>
  <v-container
    class="fill-height"
    fluid
  >
    <v-row
      align="center"
      justify="center"
    >
      <v-col
        cols="12"
        sm="8"
        md="4"
      >
        <v-card elevation="12">
          <v-app-bar
            color="amber"
            dense
          >
            <v-icon class="mr-4">mdi-arrow-right-bold-circle-outline</v-icon>
            <v-toolbar-title class="title">Вход</v-toolbar-title>
          </v-app-bar>
          <v-form
            ref="form"
            v-model="valid"
            lazy-validation
            @submit.prevent
          >
            <v-text-field
              v-model="email"
              :rules="emailRules"
              type="text"
              label="Email"
              prepend-icon="mdi-mail-ru"
              outlined
              elevation="12"
              class="ma-5 mb-0 mt-10"
              color="amber"
              required
            ></v-text-field>
            <v-text-field
              v-model="password"
              :rules="passwordRules"
              type="password"
              label="Пароль"
              prepend-icon="lock"
              outlined
              elevation="12"
              class="mx-5 pb-0"
              color="amber"
              required
            ></v-text-field>
          </v-form>
          <v-card-actions>
            <span class="ml-5">Нет аккаунта?</span>
            <v-btn
              class="my-0"
              text
              color="amber"
              to="/register"
            >
              Зарегистрироваться
            </v-btn>
          </v-card-actions>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
              class="ma-2"
              tile
              color="amber"
              elevation="3"
              type="submit"
              @click="validate"
            >
              <v-icon left>mdi-arrow-right-bold-circle-outline</v-icon> Войти
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { SHA256 } from '../utils/sha256'
export default {
  name: 'Login',
  data: () => ({
    valid: true,
    email: '',
    emailRules: [
      v => !!v || 'поле E-mail не может быть пустым',
      v => /.+@.+\..+/.test(v) || 'E-mail должен быть правильным'
    ],
    password: '',
    passwordRules: [
      v => !!v || 'поле Password не может быть пустым',
      v => (v && v.length > 5) || 'пароль не может быть короче 6 символов'
    ]
  }),
  methods: {
    async validate () {
      if (this.$refs.form.validate()) {
        const tmpPassword = SHA256(this.password)
        const login = {
          email: this.email,
          password: tmpPassword
        }
        try {
          await this.$store.dispatch('login', login)
        } catch (e) {
        }
      }
    }
  },
  mounted () {
    if (this.$route.query.message) {
      if (this.$route.query.message === 'you are out') {
        this.$store.commit('logout')
        this.$store.commit('setMessage', 'you are out')
      }
    }
  }
}
</script>

<style scoped>

</style>
