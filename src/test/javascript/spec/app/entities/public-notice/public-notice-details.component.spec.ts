/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PublicNoticeDetailComponent from '@/entities/public-notice/public-notice-details.vue';
import PublicNoticeClass from '@/entities/public-notice/public-notice-details.component';
import PublicNoticeService from '@/entities/public-notice/public-notice.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PublicNotice Management Detail Component', () => {
    let wrapper: Wrapper<PublicNoticeClass>;
    let comp: PublicNoticeClass;
    let publicNoticeServiceStub: SinonStubbedInstance<PublicNoticeService>;

    beforeEach(() => {
      publicNoticeServiceStub = sinon.createStubInstance<PublicNoticeService>(PublicNoticeService);

      wrapper = shallowMount<PublicNoticeClass>(PublicNoticeDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { publicNoticeService: () => publicNoticeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPublicNotice = { id: 123 };
        publicNoticeServiceStub.find.resolves(foundPublicNotice);

        // WHEN
        comp.retrievePublicNotice(123);
        await comp.$nextTick();

        // THEN
        expect(comp.publicNotice).toBe(foundPublicNotice);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublicNotice = { id: 123 };
        publicNoticeServiceStub.find.resolves(foundPublicNotice);

        // WHEN
        comp.beforeRouteEnter({ params: { publicNoticeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.publicNotice).toBe(foundPublicNotice);
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
