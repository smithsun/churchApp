<template>
  <div>
    <h2 id="page-heading" data-cy="VideosHeading">
      <span v-text="$t('churchApp.videos.home.title')" id="videos-heading">Videos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('churchApp.videos.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'VideosCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-videos"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('churchApp.videos.home.createLabel')"> Create a new Videos </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && videos && videos.length === 0">
      <span v-text="$t('churchApp.videos.home.notFound')">No videos found</span>
    </div>
    <div class="table-responsive" v-if="videos && videos.length > 0">
      <table class="table table-striped" aria-describedby="videos">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.title')">Title</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.type')">Type</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.content')">Content</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.notes')">Notes</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.keyWords')">Key Words</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.lastUpdateBy')">Last Update By</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.status')">Status</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.eventDate')">Event Date</span></th>
            <th scope="row"><span v-text="$t('churchApp.videos.publication')">Publication</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="videos in videos" :key="videos.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'VideosView', params: { videosId: videos.id } }">{{ videos.id }}</router-link>
            </td>
            <td>{{ videos.title }}</td>
            <td>{{ videos.type }}</td>
            <td>{{ videos.content }}</td>
            <td>{{ videos.notes }}</td>
            <td>{{ videos.keyWords }}</td>
            <td>{{ videos.lastUpdateBy }}</td>
            <td>{{ videos.status }}</td>
            <td>{{ videos.eventDate ? $d(Date.parse(videos.eventDate), 'short') : '' }}</td>
            <td>
              <div v-if="videos.publication">
                <router-link :to="{ name: 'PublicationView', params: { publicationId: videos.publication.id } }">{{
                  videos.publication.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'VideosView', params: { videosId: videos.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'VideosEdit', params: { videosId: videos.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(videos)"
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
        ><span id="churchApp.videos.delete.question" data-cy="videosDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-videos-heading" v-text="$t('churchApp.videos.delete.question', { id: removeId })">
          Are you sure you want to delete this Videos?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-videos"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeVideos()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./videos.component.ts"></script>
