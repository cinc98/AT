<template>
  <div class="sendMessage">
    <v-card class="mx-auto cardSendMessage" max-width="400" raised>
      <v-subheader>MESSAGES</v-subheader>
      <v-select
        :items="performatives"
        v-model="performative"
        class="nameTextField"
        label="Performative"
      ></v-select>
      <v-select
        v-model="sender"
        :items="agents"
        :item-text="item => item.id.type.name + '@' + item.id.name"
        class="nameTextField"
        label="Sender"
      ></v-select>
      <v-select
        v-model="selectedReceivers"
        :item-text="item => item.id.type.name + '@' + item.id.name"
        :items="agents"
        class="nameTextField"
        label="Receivers"
        multiple
      ></v-select>
      <v-text-field v-model="content" class="nameTextField" label="Content"></v-text-field>
      <v-btn
        class="nameTextField"
        @click="sendMessage"
        color="blue darken-2 white--text"
      >Send message</v-btn>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "SendMessage",
  data() {
    return {
      performatives: [],
      performative: "",
      sender: "",
      selectedReceivers: [],
      content: ""
    };
  },
  methods: {
    sendMessage() {
      axios
        .post(`http://localhost:8080/ATProjectWAR/rest/messages`, {
          performative: this.performative,
          sender: {
            name: this.sender.split("@")[1],
            type: {
              name: this.sender.split("@")[0]
            }
          },
          content: this.content,
          receivers: this.receivers()
        })
        .then(response => {
          this.performative = "";
          this.sender = "";
          this.content = "";
          this.selectedReceivers = [];
        })
        .catch(error => {
          alert(error);
        });
    },
    receivers() {
      var r = [];
      this.selectedReceivers.forEach(sr => {
        r.push({
          name: sr.split("@")[1],
          type: {
            name: sr.split("@")[0]
          }
        });
      });
      return r;
    }
  },
  computed: {
    agents() {
      return this.$store.state.activeAgents;
    }
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
  margin-top: 15px;
  padding-bottom: 15px;
  overflow: auto;
}
.nameTextField {
  margin: 0px 16px !important;
  min-height: unset !important;
}
</style>