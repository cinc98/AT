<template>
  <div class="asd">
    <v-text-field v-model="yearFrom" label="Year from"></v-text-field>
    <v-text-field v-model="yearTo" label="Year to"></v-text-field>
    <v-text-field v-model="priceFrom" label="Price from"></v-text-field>
    <v-text-field v-model="priceTo" label="Price to"></v-text-field>
    <v-btn class="nameTextField" @click="collect" color="blue darken-2 white--text">Collect</v-btn>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CarRangeInput",
  data() {
    return {
      yearFrom: "",
      yearTo: "",
      priceFrom: "",
      priceTo: ""
    };
  },
  methods: {
    collect() {
      axios
        .get(
          `http://localhost:8080/ATProjectWAR/rest/search/${this.yearFrom}/${this.yearTo}/${this.priceFrom}/${this.priceTo}`
        )
        .then(response => {
          this.newCars(response.data);
          this.yearFrom = "";
          this.yearTo = "";
          this.priceFrom = "";
          this.priceTo = "";
        })
        .catch(error => {
          alert(error);
        });
    },
    newCars(m) {
      this.$store.commit("fetchCars", m);
    }
  }
};
</script>

<style scoped>
.asd {
  width: 400px;
}
</style>