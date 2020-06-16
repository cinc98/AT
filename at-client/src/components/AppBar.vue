<template>
    <v-app-bar class="appBar"
    color="blue darken-2 white--text">
      <router-link to="/" tag="v-toolbar-title">AGENT SYSTEM</router-link>
      <router-link class="carP" to="/predict" tag="v-toolbar-title">car predict</router-link>
    </v-app-bar>
</template>

<script>
export default {
    name: 'AppBar',
    methods: {
       
    },
};
</script>

<style>
.appBar{
    z-index: 10000;
    position: fixed;
    top: 0;
    width: 100%;
}
.carP{
    margin-left: 20px;
}
</style>