<template>

  <div class="modal-body"> 
    
    <div class="row justify-content-center">
      <div class="col-md-8">
        <b-alert show data-cy="loginError" variant="danger" v-if="authenticationError" v-html="$t('login.messages.error.authentication')">
          <strong>Failed to sign in!</strong> Please check your credentials and try again.
        </b-alert>
      </div>
      <div class="col-md-8">
        <b-form role="form" v-on:submit.prevent="doLogin()">
           <b-form-group v-bind:label="$t('global.form[\'cellphone.label\']')" label-for="username">
            <b-form-input
              id="cellphone"
              type="text"
              name="cellphone"
              autofocus
              v-bind:placeholder="$t('global.form[\'cellphone.placeholder\']')"
              xxx-v-model="cellphone"
              xxx-data-cy="cellphone"
            >
            </b-form-input>
          </b-form-group>
          <b-form-group v-bind:label="$t('global.form[\'username.label\']')" label-for="username">
            <b-form-input
              id="username"
              type="text"
              name="username"
              autofocus
              v-bind:placeholder="$t('global.form[\'username.placeholder\']')"
              v-model="login"
              data-cy="username"
            >
            </b-form-input>
          </b-form-group>
          <b-form-group v-bind:label="$t('login.form.password')" label-for="password">
            <b-form-input
              id="password"
              type="password"
              name="password"
              v-model.trim="name"
              v-bind:placeholder="$t('login.form[\'password.placeholder\']')"
              v-model="password"
              data-cy="password"
            >
            </b-form-input>
          </b-form-group>
          <div class="d-flex justify-content-between"> 
            <b-form-checkbox id="rememberMyCellphone" name="rememberMyCellphone" xxx-v-model="rememberMyCellphone" checked>
                <span v-text="$t('login.form.rememberMyCellphone')">Remember My Cellphone</span>
            </b-form-checkbox>
            <b-form-checkbox id="rememberMe" name="rememberMe" v-model="rememberMe" checked>
                <span v-text="$t('login.form.rememberme')">Remember me</span>
            </b-form-checkbox>
          </div>
          <div class="my-3">
            <b-button block pill  size="lg" data-cy="submit" type="submit" variant="primary" v-text="$t('login.form.passwordLogin')">密碼登入</b-button>
          </div>
          <div class="my-3">
            <b-button block pill size="lg" data-cy="submit" type="submit" variant="primary">
                <span class="icon-face-recognition px-2" ></span>
                <span class="icon-fingerprint px-2"></span>
                <span  v-text="$t('login.form.quickLogin')"></span>
            </b-button>
          </div>
        </b-form>
        <p></p>
        <div>
          <b-button show variant="light" class="d-block text-center p-3">
            <b-link
              :to="'/account/reset/request'"
              class="alert-link"
              v-text="$t('login.password.forgot')"
              data-cy="forgetYourPasswordSelector"
              >Did you forget your password?</b-link
            >
          </b-button>
        </div>
        <div>
          <b-button show variant="light" class="d-block text-center p-3"> 
            <b-link :to="'/register'" class="alert-link" >
              <span v-text="$t('global.messages.info.register.noaccount')">You don't have an account yet?</span>
              <span v-text="$t('global.messages.info.register.link')"> Register a new account</span>
              
            </b-link>
          </b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./login-form.component.ts"></script>
