/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import DailyVersesComponent from '@/entities/daily-verses/daily-verses.vue';
import DailyVersesClass from '@/entities/daily-verses/daily-verses.component';
import DailyVersesService from '@/entities/daily-verses/daily-verses.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('DailyVerses Management Component', () => {
    let wrapper: Wrapper<DailyVersesClass>;
    let comp: DailyVersesClass;
    let dailyVersesServiceStub: SinonStubbedInstance<DailyVersesService>;

    beforeEach(() => {
      dailyVersesServiceStub = sinon.createStubInstance<DailyVersesService>(DailyVersesService);
      dailyVersesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DailyVersesClass>(DailyVersesComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          dailyVersesService: () => dailyVersesServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      dailyVersesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDailyVersess();
      await comp.$nextTick();

      // THEN
      expect(dailyVersesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.dailyVerses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      dailyVersesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeDailyVerses();
      await comp.$nextTick();

      // THEN
      expect(dailyVersesServiceStub.delete.called).toBeTruthy();
      expect(dailyVersesServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
