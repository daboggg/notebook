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
            <v-toolbar-title class="title">Login</v-toolbar-title>
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
              label="Password"
              prepend-icon="lock"
              outlined
              elevation="12"
              class="mx-5 pb-0"
              color="amber"
              required
            ></v-text-field>
          </v-form>
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
              <v-icon left>mdi-pencil</v-icon> Login
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
      v => !!v || 'E-mail is required',
      v => /.+@.+\..+/.test(v) || 'E-mail must be valid'
    ],
    password: '',
    passwordRules: [
      v => !!v || 'Password is required',
      v => (v && v.length > 5) || 'the password must be at least 6 characters'
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
          console.log(login)
          // await this.$store.dispatch('login', login)
          // this.$router.push('/')
        } catch (e) {
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
