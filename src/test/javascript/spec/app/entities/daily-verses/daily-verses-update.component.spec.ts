/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import DailyVersesUpdateComponent from '@/entities/daily-verses/daily-verses-update.vue';
import DailyVersesClass from '@/entities/daily-verses/daily-verses-update.component';
import DailyVersesService from '@/entities/daily-verses/daily-verses.service';

import DigestsService from '@/entities/digests/digests.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('DailyVerses Management Update Component', () => {
    let wrapper: Wrapper<DailyVersesClass>;
    let comp: DailyVersesClass;
    let dailyVersesServiceStub: SinonStubbedInstance<DailyVersesService>;

    beforeEach(() => {
      dailyVersesServiceStub = sinon.createStubInstance<DailyVersesService>(DailyVersesService);

      wrapper = shallowMount<DailyVersesClass>(DailyVersesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          dailyVersesService: () => dailyVersesServiceStub,

          digestsService: () => new DigestsService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.dailyVerses = entity;
        dailyVersesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dailyVersesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.dailyVerses = entity;
        dailyVersesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(dailyVersesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDailyVerses = { id: 123 };
        dailyVersesServiceStub.find.resolves(foundDailyVerses);
        dailyVersesServiceStub.retrieve.resolves([foundDailyVerses]);

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
