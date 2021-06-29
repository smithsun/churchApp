import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PublicationService from '@/entities/publication/publication.service';
import { IPublication } from '@/shared/model/publication.model';

import { IPublicNotice, PublicNotice } from '@/shared/model/public-notice.model';
import PublicNoticeService from './public-notice.service';

const validations: any = {
  publicNotice: {
    title: {
      required,
    },
    type: {
      required,
    },
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
export default class PublicNoticeUpdate extends Vue {
  @Inject('publicNoticeService') private publicNoticeService: () => PublicNoticeService;
  public publicNotice: IPublicNotice = new PublicNotice();

  @Inject('publicationService') private publicationService: () => PublicationService;

  public publications: IPublication[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicNoticeId) {
        vm.retrievePublicNotice(to.params.publicNoticeId);
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
    if (this.publicNotice.id) {
      this.publicNoticeService()
        .update(this.publicNotice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.publicNotice.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.publicNoticeService()
        .create(this.publicNotice)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.publicNotice.created', { param: param.id });
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
      this.publicNotice[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.publicNotice[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.publicNotice[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.publicNotice[field] = null;
    }
  }

  public retrievePublicNotice(publicNoticeId): void {
    this.publicNoticeService()
      .find(publicNoticeId)
      .then(res => {
        res.eventDate = new Date(res.eventDate);
        this.publicNotice = res;
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
  }
}
