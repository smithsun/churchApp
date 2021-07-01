// import { entities } from '@/router/entities';

import Component from 'vue-class-component';
import { IDigests } from '@/shared/model/digests.model';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';
import DigestsService from '@/entities/digests/digests.service';
import Digest from '@/entities/digests/digests.component';

@Component([])
export default class DailyDigest extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('DigestsService')
  private digestsService: () => DigestsService;
  @Inject('Digest')
  private digest: () => Digest;

  public digestList: IDigests[] = [];

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }
}
