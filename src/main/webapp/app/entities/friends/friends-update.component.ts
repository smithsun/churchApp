import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import { IFriends, Friends } from '@/shared/model/friends.model';
import FriendsService from './friends.service';

const validations: any = {
  friends: {
    name: {
      required,
    },
    lastName: {
      required,
    },
    cellPhone: {},
    lastUsedTime: {},
    intimacyOrder: {},
  },
};

@Component({
  validations,
})
export default class FriendsUpdate extends Vue {
  @Inject('friendsService') private friendsService: () => FriendsService;
  public friends: IFriends = new Friends();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.friendsId) {
        vm.retrieveFriends(to.params.friendsId);
      }
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
    if (this.friends.id) {
      this.friendsService()
        .update(this.friends)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.friends.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.friendsService()
        .create(this.friends)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('churchApp.friends.created', { param: param.id });
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
      this.friends[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.friends[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.friends[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.friends[field] = null;
    }
  }

  public retrieveFriends(friendsId): void {
    this.friendsService()
      .find(friendsId)
      .then(res => {
        res.lastUsedTime = new Date(res.lastUsedTime);
        this.friends = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
