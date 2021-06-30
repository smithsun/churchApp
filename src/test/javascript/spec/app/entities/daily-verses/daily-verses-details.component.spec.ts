/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DailyVersesDetailComponent from '@/entities/daily-verses/daily-verses-details.vue';
import DailyVersesClass from '@/entities/daily-verses/daily-verses-details.component';
import DailyVersesService from '@/entities/daily-verses/daily-verses.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('DailyVerses Management Detail Component', () => {
    let wrapper: Wrapper<DailyVersesClass>;
    let comp: DailyVersesClass;
    let dailyVersesServiceStub: SinonStubbedInstance<DailyVersesService>;

    beforeEach(() => {
      dailyVersesServiceStub = sinon.createStubInstance<DailyVersesService>(DailyVersesService);

      wrapper = shallowMount<DailyVersesClass>(DailyVersesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { dailyVersesService: () => dailyVersesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDailyVerses = { id: 123 };
        dailyVersesServiceStub.find.resolves(foundDailyVerses);

        // WHEN
        comp.retrieveDailyVerses(123);
        await comp.$nextTick();

        // THEN
        expect(comp.dailyVerses).toBe(foundDailyVerses);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDailyVerses = { id: 123 };
        dailyVersesServiceStub.find.resolves(foundDailyVerses);

        // WHEN
        comp.beforeRouteEnter({ params: { dailyVersesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.dailyVerses).toBe(foundDailyVerses);
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
