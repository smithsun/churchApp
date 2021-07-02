<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="churchApp.digests.home.createOrEditLabel"
          data-cy="DigestsCreateUpdateHeading"
          v-text="$t('churchApp.digests.home.createOrEditLabel')"
        >
          Create or edit a Digests
        </h2>
        <div>
          <div class="form-group" v-if="digests.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="digests.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.type')" for="digests-type">Type</label>
            <select
              class="form-control"
              name="type"
              :class="{ valid: !$v.digests.type.$invalid, invalid: $v.digests.type.$invalid }"
              v-model="$v.digests.type.$model"
              id="digests-type"
              data-cy="type"
              required
            >
              <option value="NEWBELIVER" v-bind:label="$t('churchApp.DigestType.NEWBELIVER')">初信</option>
              <option value="SERVICEONE" v-bind:label="$t('churchApp.DigestType.SERVICEONE')">事奉</option>
              <option value="GENERAL" v-bind:label="$t('churchApp.DigestType.GENERAL')">一般</option>
              <option value="CUSTOMIZED" v-bind:label="$t('churchApp.DigestType.CUSTOMIZED')">自訂</option>
            </select>
            <div v-if="$v.digests.type.$anyDirty && $v.digests.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.digests.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.topic')" for="digests-topic">Topic</label>
            <input
              type="text"
              class="form-control"
              name="topic"
              id="digests-topic"
              data-cy="topic"
              :class="{ valid: !$v.digests.topic.$invalid, invalid: $v.digests.topic.$invalid }"
              v-model="$v.digests.topic.$model"
              required
            />
            <div v-if="$v.digests.topic.$anyDirty && $v.digests.topic.$invalid">
              <small class="form-text text-danger" v-if="!$v.digests.topic.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.title')" for="digests-title">Title</label>
            <input
              type="text"
              class="form-control"
              name="title"
              id="digests-title"
              data-cy="title"
              :class="{ valid: !$v.digests.title.$invalid, invalid: $v.digests.title.$invalid }"
              v-model="$v.digests.title.$model"
              required
            />
            <div v-if="$v.digests.title.$anyDirty && $v.digests.title.$invalid">
              <small class="form-text text-danger" v-if="!$v.digests.title.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.img')" for="digests-img">Img</label>
            <input
              type="text"
              class="form-control"
              name="img"
              id="digests-img"
              data-cy="img"
              :class="{ valid: !$v.digests.img.$invalid, invalid: $v.digests.img.$invalid }"
              v-model="$v.digests.img.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.imgVerse')" for="digests-imgVerse">Img Verse</label>
            <input
              type="text"
              class="form-control"
              name="imgVerse"
              id="digests-imgVerse"
              data-cy="imgVerse"
              :class="{ valid: !$v.digests.imgVerse.$invalid, invalid: $v.digests.imgVerse.$invalid }"
              v-model="$v.digests.imgVerse.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.prayReadVerse')" for="digests-prayReadVerse"
              >Pray Read Verse</label
            >
            <input
              type="text"
              class="form-control"
              name="prayReadVerse"
              id="digests-prayReadVerse"
              data-cy="prayReadVerse"
              :class="{ valid: !$v.digests.prayReadVerse.$invalid, invalid: $v.digests.prayReadVerse.$invalid }"
              v-model="$v.digests.prayReadVerse.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.content')" for="digests-content">Content</label>
            <textarea
              class="form-control"
              name="content"
              id="digests-content"
              data-cy="content"
              :class="{ valid: !$v.digests.content.$invalid, invalid: $v.digests.content.$invalid }"
              v-model="$v.digests.content.$model"
              required
            ></textarea>
            <div v-if="$v.digests.content.$anyDirty && $v.digests.content.$invalid">
              <small class="form-text text-danger" v-if="!$v.digests.content.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.lastUpdateBy')" for="digests-lastUpdateBy"
              >Last Update By</label
            >
            <input
              type="text"
              class="form-control"
              name="lastUpdateBy"
              id="digests-lastUpdateBy"
              data-cy="lastUpdateBy"
              :class="{ valid: !$v.digests.lastUpdateBy.$invalid, invalid: $v.digests.lastUpdateBy.$invalid }"
              v-model="$v.digests.lastUpdateBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.status')" for="digests-status">Status</label>
            <input
              type="text"
              class="form-control"
              name="status"
              id="digests-status"
              data-cy="status"
              :class="{ valid: !$v.digests.status.$invalid, invalid: $v.digests.status.$invalid }"
              v-model="$v.digests.status.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.eventDate')" for="digests-eventDate">Event Date</label>
            <div class="d-flex">
              <input
                id="digests-eventDate"
                data-cy="eventDate"
                type="datetime-local"
                class="form-control"
                name="eventDate"
                :class="{ valid: !$v.digests.eventDate.$invalid, invalid: $v.digests.eventDate.$invalid }"
                :value="convertDateTimeFromServer($v.digests.eventDate.$model)"
                @change="updateZonedDateTimeField('eventDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.digests.publication')" for="digests-publication">Publication</label>
            <select class="form-control" id="digests-publication" data-cy="publication" name="publication" v-model="digests.publication">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  digests.publication && publicationOption.id === digests.publication.id ? digests.publication : publicationOption
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
            :disabled="$v.digests.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./digests-update.component.ts"></script>
