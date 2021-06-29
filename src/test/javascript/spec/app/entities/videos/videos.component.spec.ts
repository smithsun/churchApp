/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import VideosComponent from '@/entities/videos/videos.vue';
import VideosClass from '@/entities/videos/videos.component';
import VideosService from '@/entities/videos/videos.service';

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
  describe('Videos Management Component', () => {
    let wrapper: Wrapper<VideosClass>;
    let comp: VideosClass;
    let videosServiceStub: SinonStubbedInstance<VideosService>;

    beforeEach(() => {
      videosServiceStub = sinon.createStubInstance<VideosService>(VideosService);
      videosServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VideosClass>(VideosComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          videosService: () => videosServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      videosServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVideoss();
      await comp.$nextTick();

      // THEN
      expect(videosServiceStub.retrieve.called).toBeTruthy();
      expect(comp.videos[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      videosServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeVideos();
      await comp.$nextTick();

      // THEN
      expect(videosServiceStub.delete.called).toBeTruthy();
      expect(videosServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
