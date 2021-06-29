import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPublicNotice } from '@/shared/model/public-notice.model';
import PublicNoticeService from './public-notice.service';

@Component
export default class PublicNoticeDetails extends Vue {
  @Inject('publicNoticeService') private publicNoticeService: () => PublicNoticeService;
  public publicNotice: IPublicNotice = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicNoticeId) {
        vm.retrievePublicNotice(to.params.publicNoticeId);
      }
    });
  }

  public retrievePublicNotice(publicNoticeId) {
    this.publicNoticeService()
      .find(publicNoticeId)
      .then(res => {
        this.publicNotice = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
