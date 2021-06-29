import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import DigestsService from '@/entities/digests/digests.service';
import { IDigests } from '@/shared/model/digests.model';

import PublicNoticeService from '@/entities/public-notice/public-notice.service';
import { IPublicNotice } from '@/shared/model/public-notice.model';

import VideosService from '@/entities/videos/videos.service';
import { IVideos } from '@/shared/model/videos.model';

import { IPublication, Publication } from '@/shared/model/publication.model';
import PublicationService from './publication.service';

const validations: any = {
  publication: {
    type: {
      required,
    },
    startDate: {},
    endDate: {},
    priority: {},
    createdBy: {},
    lastUpdateBy: {},
    orgSignature: {},
  },
};

@Component({
  validations,
})
export default class PublicationUpdate extends Vue {
  @Inject('publicationService') private publicationService: () => PublicationService;
  public publication: IPublication = new Publication();

  @Inject('digestsService') private digestsService: () => DigestsService;

  public digests: IDigests[] = [];

  @Inject('publicNoticeService') private publicNoticeService: () => PublicNoticeService;

  public publicNotices: IPublicNotice[] = [];

  @Inject('videosService') private videosService: () => VideosService;

  public videos: IVideos[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicationId) {
        vm.retrievePublication(to.params.publicationId);
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
    if (this.publication.id) {
      this.publicationService()
        .update(this.publication)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.publication.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.publicationService()
        .create(this.publication)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.publication.created', { param: param.id });
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
      this.publication[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.publication[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.publication[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.publication[field] = null;
    }
  }

  public retrievePublication(publicationId): void {
    this.publicationService()
      .find(publicationId)
      .then(res => {
        res.startDate = new Date(res.startDate);
        res.endDate = new Date(res.endDate);
        this.publication = res;
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
    this.publicNoticeService()
      .retrieve()
      .then(res => {
        this.publicNotices = res.data;
      });
    this.videosService()
      .retrieve()
      .then(res => {
        this.videos = res.data;
      });
  }
}
