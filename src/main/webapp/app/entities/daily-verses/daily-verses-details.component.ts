import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDailyVerses } from '@/shared/model/daily-verses.model';
import DailyVersesService from './daily-verses.service';

@Component
export default class DailyVersesDetails extends Vue {
  @Inject('dailyVersesService') private dailyVersesService: () => DailyVersesService;
  public dailyVerses: IDailyVerses = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.dailyVersesId) {
        vm.retrieveDailyVerses(to.params.dailyVersesId);
      }
    });
  }

  public retrieveDailyVerses(dailyVersesId) {
    this.dailyVersesService()
      .find(dailyVersesId)
      .then(res => {
        this.dailyVerses = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
