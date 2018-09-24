<template>
  <div class="outer__container">
    <div class="button__container">
        <div class="button__header lighter__text">
          <h3>Choose a blood type: </h3>
        </div>
        <v-btn @click="setType('O_PLUS')" :class="{'btn__active': selectedType === 'O_PLUS'}" fab color="white">O+</v-btn>
        <v-btn @click="setType('O_MINUS')" :class="{'btn__active': selectedType === 'O_MINUS'}" fab>O-</v-btn>
        <v-btn @click="setType('A_PLUS')" :class="{'btn__active': selectedType === 'A_PLUS'}" fab color="white">A+</v-btn>
        <v-btn @click="setType('A_MINUS')" :class="{'btn__active': selectedType === 'A_MINUS'}" fab>A-</v-btn>
        <v-btn @click="setType('B_PLUS')" :class="{'btn__active': selectedType === 'B_PLUS'}" fab>B+</v-btn>
        <v-btn @click="setType('B_MINUS')" :class="{'btn__active': selectedType === 'B_MINUS'}" fab>B-</v-btn>
        <v-btn @click="setType('AB_PLUS')" :class="{'btn__active': selectedType === 'AB_PLUS'}" fab color="white">AB+</v-btn>
        <v-btn @click="setType('AB_MINUS')" :class="{'btn__active': selectedType === 'AB_MINUS'}" fab>AB-</v-btn>
    </div>
    <div class="stock__container">
      <blood-container title="red cells" :percent="getRedCellsPercent" />
      <blood-container title="plasma" :percent="getPlasmaPercent" color="#E8EEF2" />
      <blood-container title="platelets" :percent="getPlateletsPercent" color="#07BE88" />
    </div>
    <div class="actions__container">
       <v-btn color="success" @click="openAddDialog">Add to Stock</v-btn>
      <v-btn color="danger">Remove from Stock</v-btn>
    </div>
    <div class="details__container">
      <p class="details__title  padding__bottom">
        Maximum Capacity: <span> {{ stockMaxSize }}L </span>
      </p>
      <p class="details__title lighter__text">
        Plasma Quantity: <span> {{ getPlasma }}L </span>
      </p>
      <p class="details__title lighter__text">
        Platelets Quantity: <span> {{ getPlatelets }}L </span>
      </p>
      <p class="details__title lighter__text">
        Red cells Quantity: <span> {{ getRedCells }}L </span>
      </p>
    </div>

    <v-dialog v-model="add" persistent max-width="800px">
      <v-card>
        <v-card-title>
          <span class="headline">Add to Stock</span>
        </v-card-title>
        <v-card-text>
          <v-subheader class="pa-0">Donation Id</v-subheader>
          <v-autocomplete
           v-model="selectedId"
           :items="ids"
           persistent-hint
          >
        <v-slide-x-reverse-transition
          slot="append-outer"
          mode="out-in"
        >
        </v-slide-x-reverse-transition>
      </v-autocomplete>
        <v-text-field
          v-model="plasma"
          label="Plasma quantity (in ml)"
          required
        ></v-text-field>
        <v-text-field
          v-model="platelet"
          label="Platelet quantity (in ml)"
          required
        ></v-text-field>
        <v-text-field
          v-model="redCell"
          label="Red cell Quantity (in ml)"
          required
        ></v-text-field>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1 danger" flat @click.native="clearAddDialog">Close</v-btn>
          <v-btn color="blue darken-1" flat @click.native="addToStock">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>

</template>
<script>
import BloodContainer from "@/components/BloodContainer"
import { mapActions, mapState, mapGetters } from 'vuex'

export default {
  components: {
    BloodContainer
  },
  data() {
    return {
      selectedType: 'O_PLUS',
      add: false,
      isEditing: true,
      selectedId: '',
      redCell: '0',
      plasma: '0',
      platelet: '0'
    }
  },
  computed: {
    ...mapState(['stock']),
    ...mapGetters(['stockMaxSize', 'ids']),
    getPlasma() {
      return Math.ceil(this.stock[this.selectedType].plasma / 1000.0)
    },
    getPlatelets() {
      return Math.ceil(this.stock[this.selectedType].platelet / 1000.0)
    },
    getRedCells() {
      return Math.ceil(this.stock[this.selectedType].redCell / 1000.0);
    },
    getPlasmaPercent() {
      return Math.ceil((this.stock[this.selectedType].plasma / (this.stockMaxSize * 1000)) * 100)
    },
    getPlateletsPercent() {
      return Math.ceil((this.stock[this.selectedType].platelet / (this.stockMaxSize * 1000)) * 100)
    },
    getRedCellsPercent() {
      return Math.ceil((this.stock[this.selectedType].redCell / (this.stockMaxSize * 1000)) * 100)
    }
  },
  methods: {
    ...mapActions(['getStockInfo', 'getIds', 'updateDonation']),
    setType(type) {
      this.selectedType = type
    },
    openAddDialog() {
      this.add = true
    },
    clearAddDialog() {
      this.add = false
    },
    addToStock() {
      let data = {
        donationId: this.selectedId,
        plasma: this.plasma,
        platelet: this.platelet,
        redCell: this.redCell
      }
      this.updateDonation(data)
      this.add = false;
    }
  },
  mounted () {
    this.getStockInfo()
    this.getIds()
  }
};
</script>
<style>
.outer__container {
  margin-top: 2rem;
  padding: 1rem;
  display: grid;
  grid-template-columns: 2fr 3fr;
}
.stock__container {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-gap: 1rem;
}
.button__container {
  display: grid;
  align-items: center;
  grid-template-columns: 1fr 1fr;
}
.button__container .v-btn.v-btn--floating {
  height: 100px;
  width: 100px;
  background-color: #f5f5f5 !important;
  color: #E20E52;
  font-size: 2rem;
  font-family: 'Roboto Mono', sans-serif;
}
.details__container {
  grid-row: 2;
  grid-column: 2;
  padding: 2rem 1rem;
}
.details__title {
  font-family: 'Roboto Mono', sans-serif;
  font-size: 1.2rem;
  font-weight: 500;
}
.button__header {
  grid-column: 1 / 3;
  padding: 1rem;
  text-transform: capitalize;
  font-size: 1.5rem;
  font-weight: 300;
}
.padding__bottom {
  padding-bottom: 1rem;
}
.button__container .v-btn.v-btn--floating.btn__active {
  background-color: #e20e52 !important;
  color: #f5f5f5 !important;
}
.actions__container {
  grid-column: 2 / 1;
  padding: 3rem .5rem;
}
.actions__container .v-btn.danger {
  background-color: #e20e52 !important;
  color: #F5F5F5;
}
.actions__container .v-btn.success {
  background-color: #07BE88 !important;
}
</style>

