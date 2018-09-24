<template>
  <v-app>
    <v-navigation-drawer
      persistent
      :mini-variant="miniVariant"
      :clipped="clipped"
      v-model="drawer"
      enable-resize-watcher
      fixed
      app
    >
      <v-list>
        <v-list-tile
          value="true"
          v-for="(item, i) in items"
          :key="i"
          @click="navigate(item.path)"
        >
          <v-list-tile-action>
            <v-icon v-html="item.icon"></v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title class="black__text" v-text="item.title"></v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
    <v-toolbar
      app
      :clipped-left="clipped"
    >
      <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
      <v-btn icon @click.stop="miniVariant = !miniVariant">
        <v-icon v-html="miniVariant ? 'chevron_right' : 'chevron_left'"></v-icon>
      </v-btn>
      <!-- <v-btn icon @click.stop="clipped = !clipped">
        <v-icon>web</v-icon>
      </v-btn> -->
      <v-toolbar-title v-text="title"></v-toolbar-title>
      <v-spacer />
      <v-toolbar-items>
        <v-menu bottom left>
            <v-btn
              slot="activator"
              icon
            >
              <v-icon>more_vert</v-icon>
            </v-btn>
            <v-list>
              <v-list-tile @click="disconnect">
                <v-list-tile-title>logout</v-list-tile-title>
              </v-list-tile>
            </v-list>
          </v-menu>
       </v-toolbar-items>
    </v-toolbar>
    <v-content>
      <router-view/>
      <loader />
    </v-content>
    <v-footer :fixed="fixed" app>
      <span>&copy; MQL 2018</span>
    </v-footer>
  </v-app>
</template>

<script>
import Navigation from "@/components/Header";
import Loader from "@/components/Loader";

import { mapState, mapMutations, mapActions } from "vuex";

export default {
  components: {
    Navigation,
    Loader
  },
  data() {
    return {
      clipped: false,
      drawer: true,
      fixed: false,
      miniVariant: true,
      right: true,
      rightDrawer: false,
      title: "RedHope",
      items: [
        { title: "Home", icon: "dashboard", path: "/dashboard/overview" },
        { title: "Map", icon: "map", path: "/dashboard/map" },
        { title: "Profile", icon: "person", path: "/dashboard/profile" },
        { title: "Schedule", icon: "timer", path: "/dashboard/schedules" },
        { title: "Events", icon: "event_available", path: "/dashboard/events" },
        { title: "Stock", icon: "invert_colors", path: "/dashboard/stock" }
      ]
    };
  },
  name: "App",
  computed: {
    router() {
      return this.$router;
    },
    ...mapState(["isConnected", "email"])
  },
  methods: {
    showSidebar() {
      this.drawer = true;
    },
    navigate(to) {
      this.$router.push({ path: to });
    },
    ...mapMutations(["CONNECTED_ADMIN", "DISCONNECT_ADMIN"]),
    ...mapActions(["getUserInfo"]),
    disconnect() {
      this.DISCONNECT_ADMIN();
    }
  },
  watch: {
    isConnected() {},
    $route() {
      console.log("router changed ");
    }
  },
  mounted: function() {
    const token = this.$localStorage.get("_token");
    console.log("mounted the token is ", token);
    if (token) {
      this.getUserInfo(token);
    } else {
      //this.DISCONNECT_ADMIN()
      this.$router.push("/");
    }
  }
};
</script>

<style>

#app {
  font-family: "Roboto", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  margin: 0 auto;
  min-width: 85%;
}
.wrapping__container {
  padding: 1rem;
}
.fade-enter-active,
.fade-leave-active {
  transition-property: opacity;
  transition-duration: 0.25s;
}

.fade-enter-active {
  transition-delay: 0.25s;
}

.fade-enter,
.fade-leave-active {
  opacity: 0;
}

.white__bg {
  background-color: white;
}

.list.pt-0.list--dense div:hover {
  transition: all 0.4s ease;
  background-color: #ddd;
}
.black__text {
  color: black !important;
}
.lighter__text {
  color: rgba(102, 101, 101, 0.87);
}
</style>
