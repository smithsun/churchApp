/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PublicNoticeComponent from '@/entities/public-notice/public-notice.vue';
import PublicNoticeClass from '@/entities/public-notice/public-notice.component';
import PublicNoticeService from '@/entities/public-notice/public-notice.service';

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
  describe('PublicNotice Management Component', () => {
    let wrapper: Wrapper<PublicNoticeClass>;
    let comp: PublicNoticeClass;
    let publicNoticeServiceStub: SinonStubbedInstance<PublicNoticeService>;

    beforeEach(() => {
      publicNoticeServiceStub = sinon.createStubInstance<PublicNoticeService>(PublicNoticeService);
      publicNoticeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PublicNoticeClass>(PublicNoticeComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          publicNoticeService: () => publicNoticeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      publicNoticeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPublicNotices();
      await comp.$nextTick();

      // THEN
      expect(publicNoticeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.publicNotices[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      publicNoticeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePublicNotice();
      await comp.$nextTick();

      // THEN
      expect(publicNoticeServiceStub.delete.called).toBeTruthy();
      expect(publicNoticeServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
