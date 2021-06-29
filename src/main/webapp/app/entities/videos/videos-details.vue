<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="videos">
        <h2 class="jh-entity-heading" data-cy="videosDetailsHeading">
          <span v-text="$t('churchApp.videos.detail.title')">Videos</span> {{ videos.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('churchApp.videos.title')">Title</span>
          </dt>
          <dd>
            <span>{{ videos.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.videos.type')">Type</span>
          </dt>
          <dd>
            <span>{{ videos.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.videos.content')">Content</span>
          </dt>
          <dd>
            <span>{{ videos.content }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.videos.notes')">Notes</span>
          </dt>
          <dd>
            <span>{{ videos.notes }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.videos.keyWords')">Key Words</span>
          </dt>
          <dd>
            <span>{{ videos.keyWords }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.videos.lastUpdateBy')">Last Update By</span>
          </dt>
          <dd>
            <span>{{ videos.lastUpdateBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.videos.status')">Status</span>
          </dt>
          <dd>
            <span>{{ videos.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.videos.eventDate')">Event Date</span>
          </dt>
          <dd>
            <span v-if="videos.eventDate">{{ $d(Date.parse(videos.eventDate), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.videos.publication')">Publication</span>
          </dt>
          <dd>
            <div v-if="videos.publication">
              <router-link :to="{ name: 'PublicationView', params: { publicationId: videos.publication.id } }">{{
                videos.publication.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="videos.id" :to="{ name: 'VideosEdit', params: { videosId: videos.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./videos-details.component.ts"></script>
