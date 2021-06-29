<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="churchApp.publication.home.createOrEditLabel"
          data-cy="PublicationCreateUpdateHeading"
          v-text="$t('churchApp.publication.home.createOrEditLabel')"
        >
          Create or edit a Publication
        </h2>
        <div>
          <div class="form-group" v-if="publication.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="publication.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publication.type')" for="publication-type">Type</label>
            <input
              type="text"
              class="form-control"
              name="type"
              id="publication-type"
              data-cy="type"
              :class="{ valid: !$v.publication.type.$invalid, invalid: $v.publication.type.$invalid }"
              v-model="$v.publication.type.$model"
              required
            />
            <div v-if="$v.publication.type.$anyDirty && $v.publication.type.$invalid">
              <small class="form-text text-danger" v-if="!$v.publication.type.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publication.startDate')" for="publication-startDate">Start Date</label>
            <div class="d-flex">
              <input
                id="publication-startDate"
                data-cy="startDate"
                type="datetime-local"
                class="form-control"
                name="startDate"
                :class="{ valid: !$v.publication.startDate.$invalid, invalid: $v.publication.startDate.$invalid }"
                :value="convertDateTimeFromServer($v.publication.startDate.$model)"
                @change="updateZonedDateTimeField('startDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publication.endDate')" for="publication-endDate">End Date</label>
            <div class="d-flex">
              <input
                id="publication-endDate"
                data-cy="endDate"
                type="datetime-local"
                class="form-control"
                name="endDate"
                :class="{ valid: !$v.publication.endDate.$invalid, invalid: $v.publication.endDate.$invalid }"
                :value="convertDateTimeFromServer($v.publication.endDate.$model)"
                @change="updateZonedDateTimeField('endDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publication.priority')" for="publication-priority">Priority</label>
            <input
              type="number"
              class="form-control"
              name="priority"
              id="publication-priority"
              data-cy="priority"
              :class="{ valid: !$v.publication.priority.$invalid, invalid: $v.publication.priority.$invalid }"
              v-model.number="$v.publication.priority.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publication.createdBy')" for="publication-createdBy">Created By</label>
            <input
              type="text"
              class="form-control"
              name="createdBy"
              id="publication-createdBy"
              data-cy="createdBy"
              :class="{ valid: !$v.publication.createdBy.$invalid, invalid: $v.publication.createdBy.$invalid }"
              v-model="$v.publication.createdBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publication.lastUpdateBy')" for="publication-lastUpdateBy"
              >Last Update By</label
            >
            <input
              type="text"
              class="form-control"
              name="lastUpdateBy"
              id="publication-lastUpdateBy"
              data-cy="lastUpdateBy"
              :class="{ valid: !$v.publication.lastUpdateBy.$invalid, invalid: $v.publication.lastUpdateBy.$invalid }"
              v-model="$v.publication.lastUpdateBy.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.publication.orgSignature')" for="publication-orgSignature"
              >Org Signature</label
            >
            <input
              type="text"
              class="form-control"
              name="orgSignature"
              id="publication-orgSignature"
              data-cy="orgSignature"
              :class="{ valid: !$v.publication.orgSignature.$invalid, invalid: $v.publication.orgSignature.$invalid }"
              v-model="$v.publication.orgSignature.$model"
            />
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
            :disabled="$v.publication.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./publication-update.component.ts"></script>
