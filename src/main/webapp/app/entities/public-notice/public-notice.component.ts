import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPublicNotice } from '@/shared/model/public-notice.model';

import PublicNoticeService from './public-notice.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class PublicNotice extends Vue {
  @Inject('publicNoticeService') private publicNoticeService: () => PublicNoticeService;
  private removeId: number = null;

  public publicNotices: IPublicNotice[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPublicNotices();
  }

  public clear(): void {
    this.retrieveAllPublicNotices();
  }

  public retrieveAllPublicNotices(): void {
    this.isFetching = true;
    this.publicNoticeService()
      .retrieve()
      .then(
        res => {
          this.publicNotices = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IPublicNotice): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePublicNotice(): void {
    this.publicNoticeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('churchApp.publicNotice.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPublicNotices();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
