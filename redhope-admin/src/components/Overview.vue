<template>
  <div class="overview__wrapper">
    <h2 class="display-2">Region <span class="region">{{ getRegion }}</span></h2>
    <template v-if="getUsers.length > 0">
      <v-data-table
        :headers="headers"
        :items="getUsers"
        hide-actions
        class="elevation-1"
      >
        <template slot="items" slot-scope="props">
          <td>{{ props.item.id }}</td>
          <td class="text-xs-left">{{ props.item.email }}</td>
          <td class="text-xs-left">{{ props.item.firstName }}</td>
          <td class="text-xs-left">{{ props.item.lastName }}</td>
          <td class="text-xs-left">{{ props.item.address }}</td>
          <td class="text-xs-left">{{ props.item.phone }}</td>
          <td class="text-xs-left">{{ props.item.type }}</td>
        </template>
      </v-data-table>
    </template>
    <div class="no__users" v-else>
      <h1>No Users </h1>
    </div>
  </div>
</template>
<script>
import { mapGetters, mapState, mapActions, mapMutations } from "vuex";
export default {
  data() {
    return {
      headers: [
        {
          text: "#",
          align: "left",
          sortable: false,
          value: "email"
        },
        { text: "Email ", value: "email", align: "left" },
        { text: "First Name", value: "firstName" },
        { text: "Last Name", value: "lastName" },
        { text: "Phone Number", value: "phone" },
        { text: "Address", value: "address" },
        { text: "Blood Type", value: "type" }
      ]
    };
  },
  computed: {
    ...mapGetters(["getUsers", "getRegion"])
  }
};
</script>
<style>
.overview__wrapper {
  padding: 1rem;
}
.region {
  color: rgba(237, 37, 78, 1);
  font-weight: 500;
}
.display-2 {
  margin-top: 3rem;
  margin-bottom: 2rem;
}
</style>
