<template>
  <div>
    <b-navbar data-cy="navbar" toggleable="md" type="dark" class="bg-primary">
      <b-navbar-brand class="logo" b-link to="/">
        <span class="logo-img"></span>
        <span v-text="$t('global.title')" class="navbar-title">churchApp</span> <span class="navbar-version">{{ version }}</span>
      </b-navbar-brand>
      <b-navbar-toggle
        right
        class="jh-navbar-toggler d-lg-none"
        href="javascript:void(0);"
        data-toggle="collapse"
        target="header-tabs"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <font-awesome-icon icon="bars" />
      </b-navbar-toggle>

      <b-collapse is-nav id="header-tabs">
        <b-navbar-nav class="ml-auto">
          <b-nav-item to="/" exact>
            <span>
              <font-awesome-icon icon="home" />
              <span v-text="$t('global.menu.home')">Home</span>
            </span>
          </b-nav-item>
          <b-nav-item-dropdown right id="entity-menu" v-if="authenticated" active-class="active" class="pointer" data-cy="entity">
            <span slot="button-content" class="navbar-dropdown-menu">
              <font-awesome-icon icon="th-list" />
              <span class="no-bold" v-text="$t('global.menu.entities.main')">Entities</span>
            </span>

            <b-dropdown-item to="/friends">
              <font-awesome-icon icon="asterisk" />
              <span v-text="$t('global.menu.entities.friends')">Friends</span>
            </b-dropdown-item>
            <b-dropdown-item to="/publication">
              <font-awesome-icon icon="asterisk" />
              <span v-text="$t('global.menu.entities.publication')">Publication</span>
            </b-dropdown-item>
            <b-dropdown-item to="/digests">
              <font-awesome-icon icon="asterisk" />
              <span v-text="$t('global.menu.entities.digests')">Digests</span>
            </b-dropdown-item>
            <b-dropdown-item to="/public-notice">
              <font-awesome-icon icon="asterisk" />
              <span v-text="$t('global.menu.entities.publicNotice')">Public Notice</span>
            </b-dropdown-item>
            <b-dropdown-item to="/videos">
              <font-awesome-icon icon="asterisk" />
              <span v-text="$t('global.menu.entities.videos')">Videos</span>
            </b-dropdown-item>
            <b-dropdown-item to="/daily-verses">
              <font-awesome-icon icon="asterisk" />
              <span v-text="$t('global.menu.entities.dailyVerses')">Daily Verses</span>
            </b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item-dropdown
            right
            id="admin-menu"
            xxx-v-if="authenticated && hasAnyAuthority('ROLE_ADMIN') "
            :class="{ 'router-link-active': subIsActive('/admin') }"
            active-class="active"
            class="pointer"
            data-cy="adminMenu"
          >
            <span slot="button-content" class="navbar-dropdown-menu">
              <font-awesome-icon icon="users-cog" />
              <span class="no-bold" v-text="$t('global.menu.admin.main')">Administration</span>
            </span>
            <b-dropdown-item to="/admin/user-management" active-class="active">
              <font-awesome-icon icon="users" />
              <span v-text="$t('global.menu.admin.userManagement')">User management</span>
            </b-dropdown-item>
            <b-dropdown-item to="/admin/metrics" active-class="active">
              <font-awesome-icon icon="tachometer-alt" />
              <span v-text="$t('global.menu.admin.metrics')">Metrics</span>
            </b-dropdown-item>
            <b-dropdown-item to="/admin/health" active-class="active">
              <font-awesome-icon icon="heart" />
              <span v-text="$t('global.menu.admin.health')">Health</span>
            </b-dropdown-item>
            <b-dropdown-item to="/admin/configuration" active-class="active">
              <font-awesome-icon icon="cogs" />
              <span v-text="$t('global.menu.admin.configuration')">Configuration</span>
            </b-dropdown-item>
            <b-dropdown-item to="/admin/logs" active-class="active">
              <font-awesome-icon icon="tasks" />
              <span v-text="$t('global.menu.admin.logs')">Logs</span>
            </b-dropdown-item>
            <b-dropdown-item v-if="openAPIEnabled" to="/admin/docs" active-class="active">
              <font-awesome-icon icon="book" />
              <span v-text="$t('global.menu.admin.apidocs')">API</span>
            </b-dropdown-item>
            <b-dropdown-item v-if="!inProduction" href="./h2-console/" target="_tab">
              <font-awesome-icon icon="database" />
              <span v-text="$t('global.menu.admin.database')">Database</span>
            </b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item-dropdown id="languagesnavBarDropdown" right v-if="languages && Object.keys(languages).length > 1">
            <span slot="button-content">
              <font-awesome-icon icon="flag" />
              <span class="no-bold" v-text="$t('global.menu.language')">Language</span>
            </span>
            <b-dropdown-item
              v-for="(value, key) in languages"
              :key="`lang-${key}`"
              v-on:click="changeLanguage(key)"
              :class="{ active: isActiveLanguage(key) }"
            >
              {{ value.name }}
            </b-dropdown-item>
          </b-nav-item-dropdown>
          <b-nav-item-dropdown
            right
            href="javascript:void(0);"
            id="account-menu"
            :class="{ 'router-link-active': subIsActive('/account') }"
            active-class="active"
            class="pointer"
            data-cy="accountMenu"
          >
            <span slot="button-content" class="navbar-dropdown-menu">
              <font-awesome-icon icon="user" />
              <span class="no-bold" v-text="$t('global.menu.account.main')"> Account </span>
            </span>
            <b-dropdown-item data-cy="settings" to="/account/settings" tag="b-dropdown-item" v-if="authenticated" active-class="active">
              <font-awesome-icon icon="wrench" />
              <span v-text="$t('global.menu.account.settings')">Settings</span>
            </b-dropdown-item>
            <b-dropdown-item data-cy="passwordItem" to="/account/password" tag="b-dropdown-item" v-if="authenticated" active-class="active">
              <font-awesome-icon icon="lock" />
              <span v-text="$t('global.menu.account.password')">Password</span>
            </b-dropdown-item>
            <b-dropdown-item data-cy="logout" v-if="authenticated" v-on:click="logout()" id="logout" active-class="active">
              <font-awesome-icon icon="sign-out-alt" />
              <span v-text="$t('global.menu.account.logout')">Sign out</span>
            </b-dropdown-item>
            <b-dropdown-item data-cy="login" v-if="!authenticated" v-on:click="openLogin()" id="login" active-class="active">
              <font-awesome-icon icon="sign-in-alt" />
              <span v-text="$t('global.menu.account.login')">Sign in</span>
            </b-dropdown-item>
            <b-dropdown-item
              data-cy="register"
              to="/register"
              tag="b-dropdown-item"
              id="register"
              v-if="!authenticated"
              active-class="active"
            >
              <font-awesome-icon icon="user-plus" />
              <span v-text="$t('global.menu.account.register')">Register</span>
            </b-dropdown-item>
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
    <div class="container mt-2">
       
        <b-nav pills content-class="mt-0" justified >
          <b-nav-item to="/" exact> <span class="icon-home"></span>
            <span class="d-none d-sm-inline">&nbsp;首頁</span></b-nav-item>
          <b-nav-item to="/dailyDigests"> <span class="icon-baguette"></span>
           <span class="d-none d-sm-inline">&nbsp;每日靈糧 </span></b-nav-item>
          <b-nav-item to="/videoPage"> <span class="icon-video-clip"></span>
           <span class="d-none d-sm-inline">&nbsp;影音 </span></b-nav-item>
          <b-nav-item to="/"> <span class="icon-book-music"></span>
           <span class="d-none d-sm-inline">&nbsp;詩歌 </span></b-nav-item>
          <b-nav-item to="/friends"> <span class="icon-qrcode-scan"></span>
            <span class="d-none d-sm-inline">&nbsp;簽到</span> </b-nav-item>
          <b-nav-item to="/public-bullhorn"> <span class="icon-home"></span>
            <span class="d-none d-sm-inline">&nbsp;公告 </span></b-nav-item>
          <b-nav-item to="/public-pray"> <span class="icon-home"></span>
            <span class="d-none d-sm-inline">&nbsp;代禱</span> </b-nav-item>
        </b-nav>
    </div>
   
  </div>
</template>

<script lang="ts" src="./jhi-navbar.component.ts"></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
/* ==========================================================================
    Navbar
    ========================================================================== */
.navbar-version {
  font-size: 10px;
}

@media screen and (min-width: 768px) {
  .jh-navbar-toggler {
    display: none;
  }
}

@media screen and (min-width: 768px) and (max-width: 1150px) {
  span span {
    display: none;
  }
}

.navbar-title {
  display: inline-block;
  vertical-align: middle;
}

/* ==========================================================================
    Logo styles
    ========================================================================== */
.navbar-brand.logo {
  padding: 5px 15px;
}

.logo .logo-img {
  height: 45px;
  display: inline-block;
  vertical-align: middle;
  width: 70px;
}

.logo-img {
  height: 100%;
  background: url('../../../content/images/logo-jhipster.png') no-repeat center center;
  background-size: contain;
  width: 100%;
  filter: drop-shadow(0 0 0.05rem white);
}
</style>
