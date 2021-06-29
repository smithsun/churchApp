import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFriends } from '@/shared/model/friends.model';
import FriendsService from './friends.service';

@Component
export default class FriendsDetails extends Vue {
  @Inject('friendsService') private friendsService: () => FriendsService;
  public friends: IFriends = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.friendsId) {
        vm.retrieveFriends(to.params.friendsId);
      }
    });
  }

  public retrieveFriends(friendsId) {
    this.friendsService()
      .find(friendsId)
      .then(res => {
        this.friends = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
