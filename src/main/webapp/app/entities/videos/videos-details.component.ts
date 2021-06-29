import { Component, Vue, Inject } from 'vue-property-decorator';

import { IVideos } from '@/shared/model/videos.model';
import VideosService from './videos.service';

@Component
export default class VideosDetails extends Vue {
  @Inject('videosService') private videosService: () => VideosService;
  public videos: IVideos = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.videosId) {
        vm.retrieveVideos(to.params.videosId);
      }
    });
  }

  public retrieveVideos(videosId) {
    this.videosService()
      .find(videosId)
      .then(res => {
        this.videos = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
