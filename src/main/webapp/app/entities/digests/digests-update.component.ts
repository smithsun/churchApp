import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PublicationService from '@/entities/publication/publication.service';
import { IPublication } from '@/shared/model/publication.model';

import DailyVersesService from '@/entities/daily-verses/daily-verses.service';
import { IDailyVerses } from '@/shared/model/daily-verses.model';

import { IDigests, Digests } from '@/shared/model/digests.model';
import DigestsService from './digests.service';

const validations: any = {
  digests: {
    type: {
      required,
    },
    title: {
      required,
    },
    imgVerse: {},
    prayReadVerse: {},
    content: {
      required,
    },
    lastUpdateBy: {},
    status: {},
    eventDate: {},
  },
};

@Component({
  validations,
})
export default class DigestsUpdate extends mixins(JhiDataUtils) {
  @Inject('digestsService') private digestsService: () => DigestsService;
  public digests: IDigests = new Digests();

  @Inject('publicationService') private publicationService: () => PublicationService;

  public publications: IPublication[] = [];

  @Inject('dailyVersesService') private dailyVersesService: () => DailyVersesService;

  public dailyVerses: IDailyVerses[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.digestsId) {
        vm.retrieveDigests(to.params.digestsId);
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
    if (this.digests.id) {
      this.digestsService()
        .update(this.digests)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.digests.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.digestsService()
        .create(this.digests)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.digests.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.digests[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.digests[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.digests[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.digests[field] = null;
    }
  }

  public retrieveDigests(digestsId): void {
    this.digestsService()
      .find(digestsId)
      .then(res => {
        res.eventDate = new Date(res.eventDate);
        this.digests = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.publicationService()
      .retrieve()
      .then(res => {
        this.publications = res.data;
      });
    this.dailyVersesService()
      .retrieve()
      .then(res => {
        this.dailyVerses = res.data;
      });
  }
}
