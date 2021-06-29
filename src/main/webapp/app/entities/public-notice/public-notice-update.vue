<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="churchApp.publicNotice.home.createOrEditLabel"
          data-cy="PublicNoticeCreateUpdateHeading"
          v-text="$t('churchApp.publicNotice.home.createOrEditLabel')"
        >
          Create or edit a PublicNotice
        </h2>
        <div>
          <div class="form-group" v-if="publicNotice.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="publicNotice.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publicNotice.title')" for="public-notice-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="public-notice-title"
              data-cy="title"
              :class="{ valid: !$v.publicNotice.title.$invalid, invalid: $v.publicNotice.title.$invalid }"
              v-model="$v.publicNotice.title.$model"
              required
            />
            <div v-if="$v.publicNotice.title.$anyDirty && $v.publicNotice.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.publicNotice.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publicNotice.type')" for="public-notice-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.publicNotice.type.$invalid, invalid: $v.publicNotice.type.$invalid }"
              v-model="$v.publicNotice.type.$model"
              id="public-notice-type"
              data-cy="type"
              required
            >
              <option value="TRAINING" v-bind:label="$t('churchApp.NoticeType.TRAINING')">TRAINING</option>
              <option value="PRAYER" v-bind:label="$t('churchApp.NoticeType.PRAYER')">PRAYER</option>
              <option value="GOSPEL" v-bind:label="$t('churchApp.NoticeType.GOSPEL')">GOSPEL</option>
            </select>
            <div v-if="$v.publicNotice.type.$anyDirty && $v.publicNotice.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.publicNotice.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publicNotice.content')" for="public-notice-content">Content</label>
            <input
              type="text"
              class="form-control"
              name="content"
              id="public-notice-content"
              data-cy="content"
              :class="{ valid: !$v.publicNotice.content.$invalid, invalid: $v.publicNotice.content.$invalid }"
              v-model="$v.publicNotice.content.$model"
              required
            />
            <div v-if="$v.publicNotice.content.$anyDirty && $v.publicNotice.content.$invalid">
              <small class="form-text text-danger" v-if="!$v.publicNotice.content.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publicNotice.lastUpdateBy')" for="public-notice-lastUpdateBy"
              >Last Update By</label
            >
            <input
              type="text"
              class="form-control"
              name="lastUpdateBy"
              id="public-notice-lastUpdateBy"
              data-cy="lastUpdateBy"
              :class="{ valid: !$v.publicNotice.lastUpdateBy.$invalid, invalid: $v.publicNotice.lastUpdateBy.$invalid }"
              v-model="$v.publicNotice.lastUpdateBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publicNotice.status')" for="public-notice-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="public-notice-status"
              data-cy="status"
              :class="{ valid: !$v.publicNotice.status.$invalid, invalid: $v.publicNotice.status.$invalid }"
              v-model="$v.publicNotice.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publicNotice.eventDate')" for="public-notice-eventDate"
              >Event Date</label
            >
            <div class="d-flex">
              <input
                id="public-notice-eventDate"
                data-cy="eventDate"
                type="datetime-local"
                class="form-control"
                name="eventDate"
                :class="{ valid: !$v.publicNotice.eventDate.$invalid, invalid: $v.publicNotice.eventDate.$invalid }"
                :value="convertDateTimeFromServer($v.publicNotice.eventDate.$model)"
                @change="updateZonedDateTimeField('eventDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publicNotice.publication')" for="public-notice-publication"
              >Publication</label
            >
            <select
              class="form-control"
              id="public-notice-publication"
              data-cy="publication"
              name="publication"
              v-model="publicNotice.publication"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  publicNotice.publication && publicationOption.id === publicNotice.publication.id
                    ? publicNotice.publication
                    : publicationOption
                "
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
            :disabled="$v.publicNotice.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./public-notice-update.component.ts"></script>
