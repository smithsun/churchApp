import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IVideos } from '@/shared/model/videos.model';

import VideosService from './videos.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Videos extends Vue {
  @Inject('videosService') private videosService: () => VideosService;
  private removeId: number = null;

  public videos: IVideos[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllVideoss();
  }

  public clear(): void {
    this.retrieveAllVideoss();
  }

  public retrieveAllVideoss(): void {
    this.isFetching = true;
    this.videosService()
      .retrieve()
      .then(
        res => {
          this.videos = res.data;
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

  public prepareRemove(instance: IVideos): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeVideos(): void {
    this.videosService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('churchApp.videos.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllVideoss();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
