/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FriendsDetailComponent from '@/entities/friends/friends-details.vue';
import FriendsClass from '@/entities/friends/friends-details.component';
import FriendsService from '@/entities/friends/friends.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Friends Management Detail Component', () => {
    let wrapper: Wrapper<FriendsClass>;
    let comp: FriendsClass;
    let friendsServiceStub: SinonStubbedInstance<FriendsService>;

    beforeEach(() => {
      friendsServiceStub = sinon.createStubInstance<FriendsService>(FriendsService);

      wrapper = shallowMount<FriendsClass>(FriendsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { friendsService: () => friendsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFriends = { id: 123 };
        friendsServiceStub.find.resolves(foundFriends);

        // WHEN
        comp.retrieveFriends(123);
        await comp.$nextTick();

        // THEN
        expect(comp.friends).toBe(foundFriends);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFriends = { id: 123 };
        friendsServiceStub.find.resolves(foundFriends);

        // WHEN
        comp.beforeRouteEnter({ params: { friendsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.friends).toBe(foundFriends);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
