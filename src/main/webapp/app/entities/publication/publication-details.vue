<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="publication">
        <h2 class="jh-entity-heading" data-cy="publicationDetailsHeading">
          <span v-text="$t('churchApp.publication.detail.title')">Publication</span> {{ publication.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('churchApp.publication.type')">Type</span>
          </dt>
          <dd>
            <span>{{ publication.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publication.startDate')">Start Date</span>
          </dt>
          <dd>
            <span v-if="publication.startDate">{{ $d(Date.parse(publication.startDate), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publication.endDate')">End Date</span>
          </dt>
          <dd>
            <span v-if="publication.endDate">{{ $d(Date.parse(publication.endDate), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publication.priority')">Priority</span>
          </dt>
          <dd>
            <span>{{ publication.priority }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publication.createdBy')">Created By</span>
          </dt>
          <dd>
            <span>{{ publication.createdBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publication.lastUpdateBy')">Last Update By</span>
          </dt>
          <dd>
            <span>{{ publication.lastUpdateBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publication.orgSignature')">Org Signature</span>
          </dt>
          <dd>
            <span>{{ publication.orgSignature }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="publication.id"
          :to="{ name: 'PublicationEdit', params: { publicationId: publication.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./publication-details.component.ts"></script>
