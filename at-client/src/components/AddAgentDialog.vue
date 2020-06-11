<template>
  <div>
    <v-dialog v-model="show" persistent max-width="350">
      <v-card>
        <v-card-title class="headline">Start new agent</v-card-title>
        <v-text-field class="nameTextField" label="Name" v-model="agentName" outlined></v-text-field>
        <h4 class="nameTextField">Type of agent: {{this.$props.agentType}}</h4>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn color="red darken-1" text @click="closeDialog">Close</v-btn>

          <v-btn color="green darken-1" text @click="addNewAgent">Submit</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: "AddAgentDialog",
  data() {
    return {
      agentName: ""
    };
  },
  props: ["show", "dialogToggle", "agentType"],
  methods: {
    addNewAgent() {
      axios
        .put(`http://localhost:8080/ATProjectWAR/rest/agents/running/${this.$props.agentType}/${this.agentName}`)
        .then(response => {
          this.closeDialog();
        })
        .catch(error => {
          alert(error);
        });
    },
    closeDialog() {
      this.$props.dialogToggle();
      this.agentName = "";
    }
  }
};
</script>

<style scoped>
.nameTextField {
  margin: 0px 20px !important;
}
</style>