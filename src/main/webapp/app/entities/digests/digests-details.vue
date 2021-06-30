<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="digests">
        <h2 class="jh-entity-heading" data-cy="digestsDetailsHeading">
          <span v-text="$t('churchApp.digests.detail.title')">Digests</span> {{ digests.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('churchApp.digests.type')">Type</span>
          </dt>
          <dd>
            <span v-text="$t('churchApp.DigestType.' + digests.type)">{{ digests.type }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.digests.title')">Title</span>
          </dt>
          <dd>
            <span>{{ digests.title }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.digests.imgVerse')">Img Verse</span>
          </dt>
          <dd>
            <span>{{ digests.imgVerse }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.digests.prayReadVerse')">Pray Read Verse</span>
          </dt>
          <dd>
            <span>{{ digests.prayReadVerse }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.digests.content')">Content</span>
          </dt>
          <dd>
            <span>{{ digests.content }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.digests.lastUpdateBy')">Last Update By</span>
          </dt>
          <dd>
            <span>{{ digests.lastUpdateBy }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.digests.status')">Status</span>
          </dt>
          <dd>
            <span>{{ digests.status }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.digests.eventDate')">Event Date</span>
          </dt>
          <dd>
            <span v-if="digests.eventDate">{{ $d(Date.parse(digests.eventDate), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.digests.publication')">Publication</span>
          </dt>
          <dd>
            <div v-if="digests.publication">
              <router-link :to="{ name: 'PublicationView', params: { publicationId: digests.publication.id } }">{{
                digests.publication.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="digests.id" :to="{ name: 'DigestsEdit', params: { digestsId: digests.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./digests-details.component.ts"></script>
