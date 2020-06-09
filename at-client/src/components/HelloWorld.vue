<template>
  <div>
    <button @click="click">asd</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "HelloWorld",
  props: {
    msg: String
  },
  methods: {
    click() {
      axios
        .post(`http://localhost:8080/ATProjectWAR/rest/chat/post/dadas`)
        .then(response => {
          alert(response.data);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  created() {
    this.socket = new WebSocket(`ws://localhost:8080/ATProjectWAR/ws`);
    this.socket.onopen = function(event) {
      console.log(event);
    };
    this.socket.onmessage = function(event) {
      console.log(event);
    };
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
