<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="friends">
        <h2 class="jh-entity-heading" data-cy="friendsDetailsHeading">
          <span v-text="$t('churchApp.friends.detail.title')">Friends</span> {{ friends.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('churchApp.friends.name')">Name</span>
          </dt>
          <dd>
            <span>{{ friends.name }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.friends.lastName')">Last Name</span>
          </dt>
          <dd>
            <span>{{ friends.lastName }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.friends.cellPhone')">Cell Phone</span>
          </dt>
          <dd>
            <span>{{ friends.cellPhone }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.friends.lastUsedTime')">Last Used Time</span>
          </dt>
          <dd>
            <span v-if="friends.lastUsedTime">{{ $d(Date.parse(friends.lastUsedTime), 'long') }}</span>
          </dd>
          <dt>
            <span v-text="$t('churchApp.friends.intimacyOrder')">Intimacy Order</span>
          </dt>
          <dd>
            <span>{{ friends.intimacyOrder }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="friends.id" :to="{ name: 'FriendsEdit', params: { friendsId: friends.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./friends-details.component.ts"></script>
