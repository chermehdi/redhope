<template>
  <div class="wrapper">
    <v-app>
    <div class="flex__header">
      <h1>Schedules For <span class="date"> {{ dateString }} </span> </h1>
        <div class="wrapper">
          <v-date-picker v-model="date" no-title @input="showDate = false"></v-date-picker>
        </div>
    </div>
    <v-data-table
    :headers="headers"
    :items="schedules"
    class="elevation-1"
  >
    <template slot="items" slot-scope="props">
      <td>{{ props.item.name }}</td>
      <td class="text-xs-left">{{ props.item.region }}</td>
      <td class="text-xs-left">{{ props.item.type }}</td>
      <td class="text-xs-left">{{ props.item.date }}</td>
      <td class="text-xs-left">
        <v-chip color="secondary" text-color="white" v-if="props.item.status === 'PENDING'">
        {{ props.item.status }}
        </v-chip>
        <v-chip color="success" text-color="white" v-if="props.item.status !== 'PENDING'">
        {{ props.item.status }}
        </v-chip>
      </td>
      <td> <v-btn raised color="success" v-if="props.item.status !== 'DONE'" @click.prevent="updateSchedule(props.item)">action</v-btn> </td>
    </template>
  </v-data-table>
    <v-dialog v-model="dialog" max-width="500px">
        <v-card>
          <v-card-title>
            <h2> Donation Schduled for <span class="date">{{ selectedSchedule.date }}</span> </h2>
          </v-card-title>
          <v-card-text>
            <table class="table full-width">
              <tbody>
                <tr>
                  <td>
                    id
                  </td>
                  <td>
                    {{ selectedSchedule.id }}
                  </td>
                </tr>
                 <tr>
                  <td>
                    region
                  </td>
                  <td>
                    {{ selectedSchedule.region }}
                  </td>
                </tr>
                 <tr>
                  <td>
                    date
                  </td>
                  <td>
                    {{ selectedSchedule.date }}
                  </td>
                </tr>
                <tr>
                  <td>
                    Donation Id
                  </td>
                  <td>
                    <v-text-field
                      v-model="donationId"
                      :counter="10"
                      label="Donation Id"
                      required
                   ></v-text-field>
                  </td>
                </tr>
                <tr>
                  <td>Blood Type</td>
                  <td>
                  <v-select
                    v-model="bloodType"
                    :items="types"
                    label="Blood types"
                    required />
                  </td>
                </tr>
              </tbody>
            </table>
          </v-card-text>
          <v-card-actions>
            <v-btn color="primary" raised @click.stop="dialog=false">Close</v-btn>
            <v-btn color="success" raised @click.stop="markDone">Mark Done</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-app>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
export default {
  data() {
    return {
      date: "",
      dialog: false,
      showDate: false,
      date: "",
      donationId: "",
      bloodType: "",
      types: [
        "A_PLUS",
        "A_MINUS",
        "B_PLUS",
        "B_MINUS",
        "O_PLUS",
        "O_MINUS",
        "AB_PLUS",
        "AB_MINUS"
      ],
      selectedSchedule: {

      },
      headers: [
        { text: "Name", value: "name" },
        { text: "Region", value: "region" },
        { text: "Blood type", value: "type" },
        { text: "Date", value: "date" },
        { text: "Status", value: "status" },
        { text: "Action", value: "action" }
      ]
    };
  },
  computed: {
    ...mapGetters(["schedules"]),
    dateString() {
      return this.date === '' ? 'All Days': this.date
    }
  },
  methods: {
    ...mapActions(["getScheduleList", "getScheduleForDay", "markDoneSchedule"]),
    fetchSchedulesForDay() {
      this.getScheduleForDay(this.date);
    },
    markDone() {
      let id = this.selectedSchedule.id
      this.markDoneSchedule({id, donationId: this.donationId, type: this.bloodType})
      this.closeDialog();
    },
    closeDialog() {
      this.dialog = false
      this.donationId = ""
      this.bloodType = ""
    },
    updateSchedule(scheduleItem) {
      this.selectedSchedule = scheduleItem
      this.dialog = true
      console.log('the schedule item is ', scheduleItem)
    }
  },
  mounted() {
    this.getScheduleList()
  },
  watch: {
    date(val) {
      this.getScheduleForDay(val)
    }
  }
};
</script>

<style>
@import "../assets/css/vuetify.min.css";

.flex__header {
  display: flex;
  align-items: center;
  justify-content: space-around;
  margin-top: 1rem;
  margin-bottom: 1rem;
}
.btn__content {
  color: black;
}
.date {
  font-family: 'Roboto Mono';
  margin-left: 1rem;
}
.full-width {
  width: 100%;
}
</style>


