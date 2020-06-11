<template>
  <div class="sendMessage">
    <v-card class="mx-auto cardSendMessage" max-width="400" raised>
      <v-subheader>MESSAGES</v-subheader>
      <v-select :items="performatives" class="nameTextField" label="Performative"></v-select>
      <v-select class="nameTextField" label="Sender"></v-select>
      <v-select class="nameTextField" label="Receiver"></v-select>
      <v-select class="nameTextField" label="Reply to"></v-select>
      <v-text-field class="nameTextField" label="Content"></v-text-field>
      <v-text-field class="nameTextField" label="Language"></v-text-field>
      <v-text-field class="nameTextField" label="Encoding"></v-text-field>
      <v-text-field class="nameTextField" label="Ontology"></v-text-field>
      <v-text-field class="nameTextField" label="Protocol"></v-text-field>
      <v-text-field class="nameTextField" label="Conversation ID"></v-text-field>
      <v-text-field class="nameTextField" label="Reply with"></v-text-field>
      <v-text-field class="nameTextField" label="Reply by"></v-text-field>
      <v-btn class="nameTextField" color="blue darken-2 white--text">Send message</v-btn>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SendMessage",
  data() {
    return {
      performatives: []
    };
  },
  created() {
    axios
      .get(`http://localhost:8080/ATProjectWAR/rest/messages`)
      .then(response => {
        this.performatives = response.data;
      })
      .catch(error => {
        alert(error);
      });
  }
};
</script>

<style scoped>
.sendMessage {
  width: 100%;
}
.cardSendMessage {
  height: calc(100% - 30px);
  margin-top: 15px;
  overflow: auto;
}
.nameTextField {
  margin: 0px 16px !important;
  min-height: unset !important;
}
</style>