import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Friends = () => import('@/entities/friends/friends.vue');
// prettier-ignore
const FriendsUpdate = () => import('@/entities/friends/friends-update.vue');
// prettier-ignore
const FriendsDetails = () => import('@/entities/friends/friends-details.vue');
// prettier-ignore
const Publication = () => import('@/entities/publication/publication.vue');
// prettier-ignore
const PublicationUpdate = () => import('@/entities/publication/publication-update.vue');
// prettier-ignore
const PublicationDetails = () => import('@/entities/publication/publication-details.vue');
// prettier-ignore
const DailyDigests = () => import('@/entities/digests/dailyDigest.vue');
// prettier-ignore
const Digests = () => import('@/entities/digests/digests.vue');
// prettier-ignore
const DigestsUpdate = () => import('@/entities/digests/digests-update.vue');
// prettier-ignore
const DigestsDetails = () => import('@/entities/digests/digests-details.vue');
// prettier-ignore
const PublicNotice = () => import('@/entities/public-notice/public-notice.vue');
// prettier-ignore
const PublicNoticeUpdate = () => import('@/entities/public-notice/public-notice-update.vue');
// prettier-ignore
const PublicNoticeDetails = () => import('@/entities/public-notice/public-notice-details.vue');
// prettier-ignore
const Videos = () => import('@/entities/videos/videos.vue'); 
// prettier-ignore
const VideosUpdate = () => import('@/entities/videos/videos-update.vue');
// prettier-ignore
const VideosDetails = () => import('@/entities/videos/videos-details.vue');
// prettier-ignore
const DailyVerses = () => import('@/entities/daily-verses/daily-verses.vue');
// prettier-ignore
const DailyVersesUpdate = () => import('@/entities/daily-verses/daily-verses-update.vue');
// prettier-ignore
const DailyVersesDetails = () => import('@/entities/daily-verses/daily-verses-details.vue');
// prettier-ignore
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
  path: '/friends',
    name: 'Friends',
    component: Friends,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/friends/new',
    name: 'FriendsCreate',
    component: FriendsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/friends/:friendsId/edit',
    name: 'FriendsEdit',
    component: FriendsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/friends/:friendsId/view',
    name: 'FriendsView',
    component: FriendsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/publication',
    name: 'Publication',
    component: Publication,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/publication/new',
    name: 'PublicationCreate',
    component: PublicationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/publication/:publicationId/edit',
    name: 'PublicationEdit',
    component: PublicationUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/publication/:publicationId/view',
    name: 'PublicationView',
    component: PublicationDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/digests',
    name: 'Digests',
    component: Digests,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/digests/new',
    name: 'DigestsCreate',
    component: DigestsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/digests/:digestsId/edit',
    name: 'DigestsEdit',
    component: DigestsUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/digests/:digestsId/view',
    name: 'DigestsView',
    component: DigestsDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-notice',
    name: 'PublicNotice',
    component: PublicNotice,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-notice/new',
    name: 'PublicNoticeCreate',
    component: PublicNoticeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-notice/:publicNoticeId/edit',
    name: 'PublicNoticeEdit',
    component: PublicNoticeUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/public-notice/:publicNoticeId/view',
    name: 'PublicNoticeView',
    component: PublicNoticeDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/videos',
    name: 'Videos',
    component: Videos,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/videos/new',
    name: 'VideosCreate',
    component: VideosUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/videos/:videosId/edit',
    name: 'VideosEdit',
    component: VideosUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/videos/:videosId/view',
    name: 'VideosView',
    component: VideosDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/daily-verses',
    name: 'DailyVerses',
    component: DailyVerses,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/daily-verses/new',
    name: 'DailyVersesCreate',
    component: DailyVersesUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/daily-verses/:dailyVersesId/edit',
    name: 'DailyVersesEdit',
    component: DailyVersesUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/daily-verses/:dailyVersesId/view',
    name: 'DailyVersesView',
    component: DailyVersesDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
