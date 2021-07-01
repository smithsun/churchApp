<template>
  <div>
    <h2 id="page-heading" data-cy="DigestsHeading">
      <span v-text="$t('churchApp.digests.home.title')" id="digests-heading">每日靈糧</span>
      <div class="d-flex justify-content-end">
        <!-- <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('churchApp.digests.home.refreshListLabel')">Refresh List</span>
        </button> -->
        <router-link :to="{ name: 'DigestsCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-digests"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('churchApp.digests.home.createLabel')"> Create a new Digests </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && digests && digests.length === 0">
      <span v-text="$t('churchApp.digests.home.notFound')">No digests found</span>
    </div>

    <div class="row" v-if="digests && digests.length > 0">
      <div v-for="digests in digests" :key="digests.id" data-cy="entityTable" class="col-lg-4 col-md-6 col-sm-12 mb-4">
        <!-- <td>
              <router-link :to="{ name: 'DigestsView', params: { digestsId: digests.id } }">{{ digests.id }}</router-link>
            </td> -->
        <div v-text="$t('churchApp.DigestType.' + digests.type)">{{ digests.type }}</div>
        <div>{{ digests.eventDate ? $d(Date.parse(digests.eventDate), 'short') : '' }}</div>
        <div>{{ digests.topic }}</div>
        <div>{{ digests.title }}</div> 
        <div class="position-relative">
          <b-img src="./content/images/digestImg/web_bg_1.svg" fluid alt="Responsive image"></b-img>
          <!-- {{ digests.img }} -->
          <div class="img-verse">{{ digests.imgVerse }}</div>
        </div>

        <div>{{ digests.prayReadVerse }}</div>
        <div>{{ digests.content }}</div>
        <!-- <td>{{ digests.lastUpdateBy }}</td>
            <td>{{ digests.status }}</td> -->
      </div>
    </div>

    <div class="table-responsive" v-if="digests && digests.length > 0">
      <table class="table table-striped" aria-describedby="digests">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span v-text="$t('churchApp.digests.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('title')">
              <span v-text="$t('churchApp.digests.title')">Title</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'title'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('img')">
              <span v-text="$t('churchApp.digests.img')">Img</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'img'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('imgVerse')">
              <span v-text="$t('churchApp.digests.imgVerse')">Img Verse</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'imgVerse'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('prayReadVerse')">
              <span v-text="$t('churchApp.digests.prayReadVerse')">Pray Read Verse</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'prayReadVerse'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('content')">
              <span v-text="$t('churchApp.digests.content')">Content</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdateBy')">
              <span v-text="$t('churchApp.digests.lastUpdateBy')">Last Update By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdateBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('status')">
              <span v-text="$t('churchApp.digests.status')">Status</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'status'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('eventDate')">
              <span v-text="$t('churchApp.digests.eventDate')">Event Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'eventDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('publication.id')">
              <span v-text="$t('churchApp.digests.publication')">Publication</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'publication.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="digests in digests" :key="digests.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DigestsView', params: { digestsId: digests.id } }">{{ digests.id }}</router-link>
            </td>
            <td v-text="$t('churchApp.DigestType.' + digests.type)">{{ digests.type }}</td>
            <td>{{ digests.title }}</td>
            <td>{{ digests.img }}</td>
            <td>{{ digests.imgVerse }}</td>
            <td>{{ digests.prayReadVerse }}</td>
            <td>{{ digests.content }}</td>
            <td>{{ digests.lastUpdateBy }}</td>
            <td>{{ digests.status }}</td>
            <td>{{ digests.eventDate ? $d(Date.parse(digests.eventDate), 'short') : '' }}</td>
            <td>
              <div v-if="digests.publication">
                <router-link :to="{ name: 'PublicationView', params: { publicationId: digests.publication.id } }">{{
                  digests.publication.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DigestsView', params: { digestsId: digests.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DigestsEdit', params: { digestsId: digests.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(digests)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
        <infinite-loading
          ref="infiniteLoading"
          v-if="totalItems > itemsPerPage"
          :identifier="infiniteId"
          slot="append"
          @infinite="loadMore"
          force-use-infinite-wrapper=".el-table__body-wrapper"
          :distance="20"
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="churchApp.digests.delete.question" data-cy="digestsDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-digests-heading" v-text="$t('churchApp.digests.delete.question', { id: removeId })">
          Are you sure you want to delete this Digests?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-digests"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDigests()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./digests.component.ts"></script>

<style scoped>
.img-verse {
  position: absolute;
  right: 6px;
  top: 6px;
  writing-mode: vertical-rl;
}
</style>
