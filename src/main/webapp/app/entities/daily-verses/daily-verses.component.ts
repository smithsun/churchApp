import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDailyVerses } from '@/shared/model/daily-verses.model';

import DailyVersesService from './daily-verses.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DailyVerses extends Vue {
  @Inject('dailyVersesService') private dailyVersesService: () => DailyVersesService;
  private removeId: number = null;

  public dailyVerses: IDailyVerses[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDailyVersess();
  }

  public clear(): void {
    this.retrieveAllDailyVersess();
  }

  public retrieveAllDailyVersess(): void {
    this.isFetching = true;
    this.dailyVersesService()
      .retrieve()
      .then(
        res => {
          this.dailyVerses = res.data;
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

  public prepareRemove(instance: IDailyVerses): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDailyVerses(): void {
    this.dailyVersesService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('churchApp.dailyVerses.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDailyVersess();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
