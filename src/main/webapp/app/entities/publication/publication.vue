<template>
  <div>
    <h2 id="page-heading" data-cy="PublicationHeading">
      <span v-text="$t('churchApp.publication.home.title')" id="publication-heading">Publications</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('churchApp.publication.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PublicationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-publication"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('churchApp.publication.home.createLabel')"> Create a new Publication </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && publications && publications.length === 0">
      <span v-text="$t('churchApp.publication.home.notFound')">No publications found</span>
    </div>
    <div class="table-responsive" v-if="publications && publications.length > 0">
      <table class="table table-striped" aria-describedby="publications">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('type')">
              <span v-text="$t('churchApp.publication.type')">Type</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'type'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('startDate')">
              <span v-text="$t('churchApp.publication.startDate')">Start Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'startDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('endDate')">
              <span v-text="$t('churchApp.publication.endDate')">End Date</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'endDate'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('priority')">
              <span v-text="$t('churchApp.publication.priority')">Priority</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'priority'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('createdBy')">
              <span v-text="$t('churchApp.publication.createdBy')">Created By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'createdBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('lastUpdateBy')">
              <span v-text="$t('churchApp.publication.lastUpdateBy')">Last Update By</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'lastUpdateBy'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('orgSignature')">
              <span v-text="$t('churchApp.publication.orgSignature')">Org Signature</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'orgSignature'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="publication in publications" :key="publication.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PublicationView', params: { publicationId: publication.id } }">{{ publication.id }}</router-link>
            </td>
            <td>{{ publication.type }}</td>
            <td>{{ publication.startDate ? $d(Date.parse(publication.startDate), 'short') : '' }}</td>
            <td>{{ publication.endDate ? $d(Date.parse(publication.endDate), 'short') : '' }}</td>
            <td>{{ publication.priority }}</td>
            <td>{{ publication.createdBy }}</td>
            <td>{{ publication.lastUpdateBy }}</td>
            <td>{{ publication.orgSignature }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PublicationView', params: { publicationId: publication.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PublicationEdit', params: { publicationId: publication.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(publication)"
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
        ><span id="churchApp.publication.delete.question" data-cy="publicationDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-publication-heading" v-text="$t('churchApp.publication.delete.question', { id: removeId })">
          Are you sure you want to delete this Publication?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-publication"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePublication()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./publication.component.ts"></script>
