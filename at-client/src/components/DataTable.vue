<template>
  <div class="datatable">
    <v-card>
      <v-card-title>
        Cars
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table :headers="headers" :items="cars" :search="search"></v-data-table>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "DataTable",
  data() {
    return {
      search: "",
      headers: [
        { text: "Year", value: "year" },
        { text: "Mileage (km)", value: "km" },
        { text: "Cubic Capacity (cm³)", value: "power" },
        { text: "Price ($)", value: "price" },
      ]
    };
  },
  methods:{
 
  },
   computed: {
    cars() {
      return this.$store.state.cars;
    }
  },
};
</script>

<style scoped>
</style>