<template>
  <div class="predict">
    <div class="inputs">
        <car-range-input/>
        <car-input class="carInput"/>
    </div>
    <div class="table">
      <data-table />
    </div>
  </div>
</template>

<script>
import DataTable from "../components/DataTable.vue";
import CarRangeInput from "../components/CarRangeInput.vue";
import CarInput from "../components/CarInput.vue";

export default {
  name: "Predict",
  components: {
    DataTable,
    CarRangeInput,
    CarInput
  }
};
</script>

<style scoped>
.predict {
  position: fixed;
  height: calc(100vh - 64px);
  width: 100%;
  top: 64px;
  display: flex;
  flex-direction: row;
  flex-basis: auto;
}
.table{
    margin: 15px;
    width: 100%;
}
.inputs{
      margin: 15px;
 
    width: 100%;
}
.carInput{
    padding-top: 50px;
}
</style>