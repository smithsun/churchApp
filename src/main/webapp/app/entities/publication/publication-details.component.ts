import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPublication } from '@/shared/model/publication.model';
import PublicationService from './publication.service';

@Component
export default class PublicationDetails extends Vue {
  @Inject('publicationService') private publicationService: () => PublicationService;
  public publication: IPublication = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.publicationId) {
        vm.retrievePublication(to.params.publicationId);
      }
    });
  }

  public retrievePublication(publicationId) {
    this.publicationService()
      .find(publicationId)
      .then(res => {
        this.publication = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
