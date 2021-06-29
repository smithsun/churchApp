import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import PublicationService from '@/entities/publication/publication.service';
import { IPublication } from '@/shared/model/publication.model';

import { IVideos, Videos } from '@/shared/model/videos.model';
import VideosService from './videos.service';

const validations: any = {
  videos: {
    title: {},
    type: {},
    content: {},
    notes: {},
    keyWords: {},
    lastUpdateBy: {},
    status: {},
    eventDate: {},
  },
};

@Component({
  validations,
})
export default class VideosUpdate extends Vue {
  @Inject('videosService') private videosService: () => VideosService;
  public videos: IVideos = new Videos();

  @Inject('publicationService') private publicationService: () => PublicationService;

  public publications: IPublication[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.videosId) {
        vm.retrieveVideos(to.params.videosId);
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
    if (this.videos.id) {
      this.videosService()
        .update(this.videos)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.videos.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.videosService()
        .create(this.videos)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.videos.created', { param: param.id });
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
      this.videos[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.videos[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.videos[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.videos[field] = null;
    }
  }

  public retrieveVideos(videosId): void {
    this.videosService()
      .find(videosId)
      .then(res => {
        res.eventDate = new Date(res.eventDate);
        this.videos = res;
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
