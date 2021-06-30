import axios from 'axios';
import Component from 'vue-class-component';
import { Vue, Inject } from 'vue-property-decorator';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';
@Component({
  watch: {
    $route() {
      this.$root.$emit('bv::hide::modal', 'login-page');
    },
  },
})
export default class LoginForm extends Vue {
  @Inject('accountService') private accountService: () => AccountService;
  @Inject('translationService') private translationService: () => TranslationService;
  public authenticationError = null;
  public login = null;
  public password = null;
  public rememberMe: boolean = null;
  private currentLanguage = this.$store.getters.currentLanguage;
  // private currentLanguage = 'zh-tw';
  created() {
    this.translationService().refreshTranslation(this.currentLanguage);
  }

  public doLogin(): void {
    const data = { username: this.login, password: this.password, rememberMe: this.rememberMe };
    axios
      .post('api/authenticate', data)
      .then(result => {
        const bearerToken = result.headers.authorization;
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length);
          if (this.rememberMe) {
            localStorage.setItem('jhi-authenticationToken', jwt);
            sessionStorage.removeItem('jhi-authenticationToken');
          } else {
            sessionStorage.setItem('jhi-authenticationToken', jwt);
            localStorage.removeItem('jhi-authenticationToken');
          }
        }
        this.authenticationError = false;
        this.$root.$emit('bv::hide::modal', 'login-page');
        this.accountService().retrieveAccount();
        this.goToNextRoute();
      })
      .catch(() => {
        this.authenticationError = true;
      });
  }

  private goToNextRoute(): void {
    this.$store.commit('authenticated');
    this.$router.push({ name: 'Home' });
  }
}
