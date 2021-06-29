<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="publicNotice">
        <h2 class="jh-entity-heading" data-cy="publicNoticeDetailsHeading">
          <span v-text="$t('churchApp.publicNotice.detail.title')">PublicNotice</span> {{ publicNotice.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('churchApp.publicNotice.title')">Title</span>
          </dt>
          <dd>
            <span>{{ publicNotice.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publicNotice.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('churchApp.NoticeType.' + publicNotice.type)">{{ publicNotice.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publicNotice.content')">Content</span>
          </dt>
          <dd>
            <span>{{ publicNotice.content }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publicNotice.lastUpdateBy')">Last Update By</span>
          </dt>
          <dd>
            <span>{{ publicNotice.lastUpdateBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publicNotice.status')">Status</span>
          </dt>
          <dd>
            <span>{{ publicNotice.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publicNotice.eventDate')">Event Date</span>
          </dt>
          <dd>
            <span v-if="publicNotice.eventDate">{{ $d(Date.parse(publicNotice.eventDate), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.publicNotice.publication')">Publication</span>
          </dt>
          <dd>
            <div v-if="publicNotice.publication">
              <router-link :to="{ name: 'PublicationView', params: { publicationId: publicNotice.publication.id } }">{{
                publicNotice.publication.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="publicNotice.id"
          :to="{ name: 'PublicNoticeEdit', params: { publicNoticeId: publicNotice.id } }"
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

<script lang="ts" src="./public-notice-details.component.ts"></script>
