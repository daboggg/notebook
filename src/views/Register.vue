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
            <v-icon class="mr-4">mdi-pencil</v-icon>
            <v-toolbar-title class="title">Регистрация</v-toolbar-title>
          </v-app-bar>
          <v-form
            ref="form"
            v-model="valid"
            lazy-validation
            @submit.prevent
          >
            <v-text-field
              v-model="username"
              :rules="usernameRules"
              type="text"
              label="Имя"
              prepend-icon="person"
              outlined
              elevation="12"
              class="ma-5 mb-0 mt-10"
              color="amber"
              required
            ></v-text-field>
            <v-text-field
              v-model="email"
              :rules="emailRules"
              type="text"
              label="Email"
              prepend-icon="mdi-mail-ru"
              outlined
              elevation="12"
              class="ma-5 mb-0 mt-0"
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
            <span class="ml-5">Уже есть аккаунт?</span>
            <v-btn
              class="my-0"
              text
              color="amber"
              to="/login"
            >
               Войти
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
              <v-icon left>mdi-pencil</v-icon> Зарегистрироваться
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
  name: 'Register',
  data: () => ({
    valid: true,
    email: '',
    emailRules: [
      v => !!v || 'поле E-mail не может быть пустым',
      v => /.+@.+\..+/.test(v) || 'E-mail должен быть правильным'
    ],
    username: '',
    usernameRules: [
      v => !!v || 'поле Имя не может быть пустым',
      v => (v && v.length > 2) || 'Имя не может быть короче 3 символов'
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
        const register = {
          username: this.username,
          email: this.email,
          password: tmpPassword
        }
        try {
          console.log(register)
          await this.$store.dispatch('register', register)
        } catch (e) {
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
