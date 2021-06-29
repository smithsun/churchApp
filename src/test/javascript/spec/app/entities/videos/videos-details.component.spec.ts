/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VideosDetailComponent from '@/entities/videos/videos-details.vue';
import VideosClass from '@/entities/videos/videos-details.component';
import VideosService from '@/entities/videos/videos.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Videos Management Detail Component', () => {
    let wrapper: Wrapper<VideosClass>;
    let comp: VideosClass;
    let videosServiceStub: SinonStubbedInstance<VideosService>;

    beforeEach(() => {
      videosServiceStub = sinon.createStubInstance<VideosService>(VideosService);

      wrapper = shallowMount<VideosClass>(VideosDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { videosService: () => videosServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVideos = { id: 123 };
        videosServiceStub.find.resolves(foundVideos);

        // WHEN
        comp.retrieveVideos(123);
        await comp.$nextTick();

        // THEN
        expect(comp.videos).toBe(foundVideos);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVideos = { id: 123 };
        videosServiceStub.find.resolves(foundVideos);

        // WHEN
        comp.beforeRouteEnter({ params: { videosId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.videos).toBe(foundVideos);
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
