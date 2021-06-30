<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="churchApp.dailyVerses.home.createOrEditLabel"
          data-cy="DailyVersesCreateUpdateHeading"
          v-text="$t('churchApp.dailyVerses.home.createOrEditLabel')"
        >
          Create or edit a DailyVerses
        </h2>
        <div>
          <div class="form-group" v-if="dailyVerses.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="dailyVerses.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.dailyVerses.verses')" for="daily-verses-verses">Verses</label>
            <input
              type="text"
              class="form-control"
              name="verses"
              id="daily-verses-verses"
              data-cy="verses"
              :class="{ valid: !$v.dailyVerses.verses.$invalid, invalid: $v.dailyVerses.verses.$invalid }"
              v-model="$v.dailyVerses.verses.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.dailyVerses.order')" for="daily-verses-order">Order</label>
            <input
              type="number"
              class="form-control"
              name="order"
              id="daily-verses-order"
              data-cy="order"
              :class="{ valid: !$v.dailyVerses.order.$invalid, invalid: $v.dailyVerses.order.$invalid }"
              v-model.number="$v.dailyVerses.order.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('churchApp.dailyVerses.digests')" for="daily-verses-digests">Digests</label>
            <select class="form-control" id="daily-verses-digests" data-cy="digests" name="digests" v-model="dailyVerses.digests">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="dailyVerses.digests && digestsOption.id === dailyVerses.digests.id ? dailyVerses.digests : digestsOption"
                v-for="digestsOption in digests"
                :key="digestsOption.id"
              >
                {{ digestsOption.id }}
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
            :disabled="$v.dailyVerses.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./daily-verses-update.component.ts"></script>
