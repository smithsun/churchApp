<template>
  <div>
    <h2 id="page-heading" data-cy="PublicNoticeHeading">
      <span v-text="$t('churchApp.publicNotice.home.title')" id="public-notice-heading">Public Notices</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('churchApp.publicNotice.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PublicNoticeCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-public-notice"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('churchApp.publicNotice.home.createLabel')"> Create a new Public Notice </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && publicNotices && publicNotices.length === 0">
      <span v-text="$t('churchApp.publicNotice.home.notFound')">No publicNotices found</span>
    </div>
    <div class="table-responsive" v-if="publicNotices && publicNotices.length > 0">
      <table class="table table-striped" aria-describedby="publicNotices">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('churchApp.publicNotice.title')">Title</span></th>
            <th scope="row"><span v-text="$t('churchApp.publicNotice.type')">Type</span></th>
            <th scope="row"><span v-text="$t('churchApp.publicNotice.content')">Content</span></th>
            <th scope="row"><span v-text="$t('churchApp.publicNotice.lastUpdateBy')">Last Update By</span></th>
            <th scope="row"><span v-text="$t('churchApp.publicNotice.status')">Status</span></th>
            <th scope="row"><span v-text="$t('churchApp.publicNotice.eventDate')">Event Date</span></th>
            <th scope="row"><span v-text="$t('churchApp.publicNotice.publication')">Publication</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="publicNotice in publicNotices" :key="publicNotice.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PublicNoticeView', params: { publicNoticeId: publicNotice.id } }">{{
                publicNotice.id
              }}</router-link>
            </td>
            <td>{{ publicNotice.title }}</td>
            <td v-text="$t('churchApp.NoticeType.' + publicNotice.type)">{{ publicNotice.type }}</td>
            <td>{{ publicNotice.content }}</td>
            <td>{{ publicNotice.lastUpdateBy }}</td>
            <td>{{ publicNotice.status }}</td>
            <td>{{ publicNotice.eventDate ? $d(Date.parse(publicNotice.eventDate), 'short') : '' }}</td>
            <td>
              <div v-if="publicNotice.publication">
                <router-link :to="{ name: 'PublicationView', params: { publicationId: publicNotice.publication.id } }">{{
                  publicNotice.publication.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PublicNoticeView', params: { publicNoticeId: publicNotice.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PublicNoticeEdit', params: { publicNoticeId: publicNotice.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(publicNotice)"
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
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="churchApp.publicNotice.delete.question" data-cy="publicNoticeDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-publicNotice-heading" v-text="$t('churchApp.publicNotice.delete.question', { id: removeId })">
          Are you sure you want to delete this Public Notice?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-publicNotice"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePublicNotice()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./public-notice.component.ts"></script>
