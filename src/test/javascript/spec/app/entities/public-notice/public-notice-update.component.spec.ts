/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import * as config from '@/shared/config/config';
import PublicNoticeUpdateComponent from '@/entities/public-notice/public-notice-update.vue';
import PublicNoticeClass from '@/entities/public-notice/public-notice-update.component';
import PublicNoticeService from '@/entities/public-notice/public-notice.service';

import PublicationService from '@/entities/publication/publication.service';

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
  describe('PublicNotice Management Update Component', () => {
    let wrapper: Wrapper<PublicNoticeClass>;
    let comp: PublicNoticeClass;
    let publicNoticeServiceStub: SinonStubbedInstance<PublicNoticeService>;

    beforeEach(() => {
      publicNoticeServiceStub = sinon.createStubInstance<PublicNoticeService>(PublicNoticeService);

      wrapper = shallowMount<PublicNoticeClass>(PublicNoticeUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          publicNoticeService: () => publicNoticeServiceStub,

          publicationService: () => new PublicationService(),
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
        comp.publicNotice = entity;
        publicNoticeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicNoticeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.publicNotice = entity;
        publicNoticeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(publicNoticeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublicNotice = { id: 123 };
        publicNoticeServiceStub.find.resolves(foundPublicNotice);
        publicNoticeServiceStub.retrieve.resolves([foundPublicNotice]);

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
