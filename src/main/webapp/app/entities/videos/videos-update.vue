<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="churchApp.videos.home.createOrEditLabel"
          data-cy="VideosCreateUpdateHeading"
          v-text="$t('churchApp.videos.home.createOrEditLabel')"
        >
          Create or edit a Videos
        </h2>
        <div>
          <div class="form-group" v-if="videos.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="videos.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.title')" for="videos-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="videos-title"
              data-cy="title"
              :class="{ valid: !$v.videos.title.$invalid, invalid: $v.videos.title.$invalid }"
              v-model="$v.videos.title.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.type')" for="videos-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="videos-type"
              data-cy="type"
              :class="{ valid: !$v.videos.type.$invalid, invalid: $v.videos.type.$invalid }"
              v-model="$v.videos.type.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.content')" for="videos-content">Content</label>
            <input
              type="text"
              class="form-control"
              name="content"
              id="videos-content"
              data-cy="content"
              :class="{ valid: !$v.videos.content.$invalid, invalid: $v.videos.content.$invalid }"
              v-model="$v.videos.content.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.notes')" for="videos-notes">Notes</label>
            <input
              type="text"
              class="form-control"
              name="notes"
              id="videos-notes"
              data-cy="notes"
              :class="{ valid: !$v.videos.notes.$invalid, invalid: $v.videos.notes.$invalid }"
              v-model="$v.videos.notes.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.keyWords')" for="videos-keyWords">Key Words</label>
            <input
              type="text"
              class="form-control"
              name="keyWords"
              id="videos-keyWords"
              data-cy="keyWords"
              :class="{ valid: !$v.videos.keyWords.$invalid, invalid: $v.videos.keyWords.$invalid }"
              v-model="$v.videos.keyWords.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.lastUpdateBy')" for="videos-lastUpdateBy">Last Update By</label>
            <input
              type="text"
              class="form-control"
              name="lastUpdateBy"
              id="videos-lastUpdateBy"
              data-cy="lastUpdateBy"
              :class="{ valid: !$v.videos.lastUpdateBy.$invalid, invalid: $v.videos.lastUpdateBy.$invalid }"
              v-model="$v.videos.lastUpdateBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.status')" for="videos-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="videos-status"
              data-cy="status"
              :class="{ valid: !$v.videos.status.$invalid, invalid: $v.videos.status.$invalid }"
              v-model="$v.videos.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.eventDate')" for="videos-eventDate">Event Date</label>
            <div class="d-flex">
              <input
                id="videos-eventDate"
                data-cy="eventDate"
                type="datetime-local"
                class="form-control"
                name="eventDate"
                :class="{ valid: !$v.videos.eventDate.$invalid, invalid: $v.videos.eventDate.$invalid }"
                :value="convertDateTimeFromServer($v.videos.eventDate.$model)"
                @change="updateZonedDateTimeField('eventDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.videos.publication')" for="videos-publication">Publication</label>
            <select class="form-control" id="videos-publication" data-cy="publication" name="publication" v-model="videos.publication">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="videos.publication && publicationOption.id === videos.publication.id ? videos.publication : publicationOption"
                v-for="publicationOption in publications"
                :key="publicationOption.id"
              >
                {{ publicationOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.videos.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./videos-update.component.ts"></script>
