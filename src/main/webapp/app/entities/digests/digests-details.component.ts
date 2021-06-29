import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IDigests } from '@/shared/model/digests.model';
import DigestsService from './digests.service';

@Component
export default class DigestsDetails extends mixins(JhiDataUtils) {
  @Inject('digestsService') private digestsService: () => DigestsService;
  public digests: IDigests = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.digestsId) {
        vm.retrieveDigests(to.params.digestsId);
      }
    });
  }

  public retrieveDigests(digestsId) {
    this.digestsService()
      .find(digestsId)
      .then(res => {
        this.digests = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
