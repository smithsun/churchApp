/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DigestsDetailComponent from '@/entities/digests/digests-details.vue';
import DigestsClass from '@/entities/digests/digests-details.component';
import DigestsService from '@/entities/digests/digests.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Digests Management Detail Component', () => {
    let wrapper: Wrapper<DigestsClass>;
    let comp: DigestsClass;
    let digestsServiceStub: SinonStubbedInstance<DigestsService>;

    beforeEach(() => {
      digestsServiceStub = sinon.createStubInstance<DigestsService>(DigestsService);

      wrapper = shallowMount<DigestsClass>(DigestsDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { digestsService: () => digestsServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDigests = { id: 123 };
        digestsServiceStub.find.resolves(foundDigests);

        // WHEN
        comp.retrieveDigests(123);
        await comp.$nextTick();

        // THEN
        expect(comp.digests).toBe(foundDigests);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDigests = { id: 123 };
        digestsServiceStub.find.resolves(foundDigests);

        // WHEN
        comp.beforeRouteEnter({ params: { digestsId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.digests).toBe(foundDigests);
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
