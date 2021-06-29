<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="churchApp.friends.home.createOrEditLabel"
          data-cy="FriendsCreateUpdateHeading"
          v-text="$t('churchApp.friends.home.createOrEditLabel')"
        >
          Create or edit a Friends
        </h2>
        <div>
          <div class="form-group" v-if="friends.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="friends.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.friends.name')" for="friends-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="friends-name"
              data-cy="name"
              :class="{ valid: !$v.friends.name.$invalid, invalid: $v.friends.name.$invalid }"
              v-model="$v.friends.name.$model"
              required
            />
            <div v-if="$v.friends.name.$anyDirty && $v.friends.name.$invalid">
              <small class="form-text text-danger" v-if="!$v.friends.name.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.friends.lastName')" for="friends-lastName">Last Name</label>
            <input
              type="text"
              class="form-control"
              name="lastName"
              id="friends-lastName"
              data-cy="lastName"
              :class="{ valid: !$v.friends.lastName.$invalid, invalid: $v.friends.lastName.$invalid }"
              v-model="$v.friends.lastName.$model"
              required
            />
            <div v-if="$v.friends.lastName.$anyDirty && $v.friends.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.friends.lastName.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.friends.cellPhone')" for="friends-cellPhone">Cell Phone</label>
            <input
              type="text"
              class="form-control"
              name="cellPhone"
              id="friends-cellPhone"
              data-cy="cellPhone"
              :class="{ valid: !$v.friends.cellPhone.$invalid, invalid: $v.friends.cellPhone.$invalid }"
              v-model="$v.friends.cellPhone.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.friends.lastUsedTime')" for="friends-lastUsedTime"
              >Last Used Time</label
            >
            <div class="d-flex">
              <input
                id="friends-lastUsedTime"
                data-cy="lastUsedTime"
                type="datetime-local"
                class="form-control"
                name="lastUsedTime"
                :class="{ valid: !$v.friends.lastUsedTime.$invalid, invalid: $v.friends.lastUsedTime.$invalid }"
                :value="convertDateTimeFromServer($v.friends.lastUsedTime.$model)"
                @change="updateZonedDateTimeField('lastUsedTime', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.friends.intimacyOrder')" for="friends-intimacyOrder"
              >Intimacy Order</label
            >
            <input
              type="number"
              class="form-control"
              name="intimacyOrder"
              id="friends-intimacyOrder"
              data-cy="intimacyOrder"
              :class="{ valid: !$v.friends.intimacyOrder.$invalid, invalid: $v.friends.intimacyOrder.$invalid }"
              v-model.number="$v.friends.intimacyOrder.$model"
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
            :disabled="$v.friends.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./friends-update.component.ts"></script>
