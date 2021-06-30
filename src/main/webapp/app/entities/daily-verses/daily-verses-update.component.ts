import { Component, Vue, Inject } from 'vue-property-decorator';

import DigestsService from '@/entities/digests/digests.service';
import { IDigests } from '@/shared/model/digests.model';

import { IDailyVerses, DailyVerses } from '@/shared/model/daily-verses.model';
import DailyVersesService from './daily-verses.service';

const validations: any = {
  dailyVerses: {
    verses: {},
    order: {},
  },
};

@Component({
  validations,
})
export default class DailyVersesUpdate extends Vue {
  @Inject('dailyVersesService') private dailyVersesService: () => DailyVersesService;
  public dailyVerses: IDailyVerses = new DailyVerses();

  @Inject('digestsService') private digestsService: () => DigestsService;

  public digests: IDigests[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dailyVersesId) {
        vm.retrieveDailyVerses(to.params.dailyVersesId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.dailyVerses.id) {
      this.dailyVersesService()
        .update(this.dailyVerses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.dailyVerses.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.dailyVersesService()
        .create(this.dailyVerses)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.dailyVerses.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveDailyVerses(dailyVersesId): void {
    this.dailyVersesService()
      .find(dailyVersesId)
      .then(res => {
        this.dailyVerses = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.digestsService()
      .retrieve()
      .then(res => {
        this.digests = res.data;
      });
  }
}
