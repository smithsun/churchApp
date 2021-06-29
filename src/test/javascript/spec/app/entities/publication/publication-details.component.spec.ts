/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PublicationDetailComponent from '@/entities/publication/publication-details.vue';
import PublicationClass from '@/entities/publication/publication-details.component';
import PublicationService from '@/entities/publication/publication.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Publication Management Detail Component', () => {
    let wrapper: Wrapper<PublicationClass>;
    let comp: PublicationClass;
    let publicationServiceStub: SinonStubbedInstance<PublicationService>;

    beforeEach(() => {
      publicationServiceStub = sinon.createStubInstance<PublicationService>(PublicationService);

      wrapper = shallowMount<PublicationClass>(PublicationDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { publicationService: () => publicationServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPublication = { id: 123 };
        publicationServiceStub.find.resolves(foundPublication);

        // WHEN
        comp.retrievePublication(123);
        await comp.$nextTick();

        // THEN
        expect(comp.publication).toBe(foundPublication);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPublication = { id: 123 };
        publicationServiceStub.find.resolves(foundPublication);

        // WHEN
        comp.beforeRouteEnter({ params: { publicationId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.publication).toBe(foundPublication);
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
