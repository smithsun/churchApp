/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import DigestsUpdateComponent from '@/entities/digests/digests-update.vue';
import DigestsClass from '@/entities/digests/digests-update.component';
import DigestsService from '@/entities/digests/digests.service';

import PublicationService from '@/entities/publication/publication.service';

import DailyVersesService from '@/entities/daily-verses/daily-verses.service';

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
  describe('Digests Management Update Component', () => {
    let wrapper: Wrapper<DigestsClass>;
    let comp: DigestsClass;
    let digestsServiceStub: SinonStubbedInstance<DigestsService>;

    beforeEach(() => {
      digestsServiceStub = sinon.createStubInstance<DigestsService>(DigestsService);

      wrapper = shallowMount<DigestsClass>(DigestsUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          digestsService: () => digestsServiceStub,

          publicationService: () => new PublicationService(),

          dailyVersesService: () => new DailyVersesService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('load', () => {
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.digests = entity;
        digestsServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(digestsServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.digests = entity;
        digestsServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(digestsServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDigests = { id: 123 };
        digestsServiceStub.find.resolves(foundDigests);
        digestsServiceStub.retrieve.resolves([foundDigests]);

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
