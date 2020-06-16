<template>
  <div class="nameTextField">
    <v-text-field v-model="year" label="Year"></v-text-field>
    <v-text-field v-model="km" label="KM"></v-text-field>
    <v-text-field v-model="cubic" label="Cubic Capacity"></v-text-field>
    <v-btn @click="predict" color="blue darken-2 white--text">Predict</v-btn>

    <h2 v-if="price!==''" class="price">Predicted price: {{price}}</h2>

  </div>
</template>

<script>
export default {
  name: "CarInput",
  data() {
    return {
      year: "",
      km: "",
      cubic: "",
      price: ""
    };
  },
  methods: {
    predict() {
      axios
        .get(
          `http://localhost:8080/ATProjectWAR/rest/predict/${this.year}/${this.km}/${this.cubic}`
        )
        .then(response => {
          this.price = response.data;
          this.year = "";
          this.km = "";
          this.cubic = "";
        })
        .catch(error => {
          alert(error);
        });
    }
  }
};
</script>

<style scoped>
.nameTextField {
  width: 400px !important;
}
.price{
  margin-top: 70px;
}
</style>