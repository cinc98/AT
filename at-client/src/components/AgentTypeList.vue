<template>
  <div class="agentTypeList">
    <v-card class="mx-auto cardList" max-width="400" raised>
      <div class="listHeader">
        <v-subheader>AGENT TYPES</v-subheader>
        <v-btn class="addTypeBtn mx-2" fab dark small color="blue darken-2">
          <v-icon>mdi-plus</v-icon>
        </v-btn>
      </div>

      <v-list-item v-for="(at,i) in agentTypes" :key="i" @click="newAgent(at.name)" link>
        <v-list-item-content>
          <v-list-item-title class="mb-1">{{at.name}}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-card>
    <add-agent-dialog
      v-bind:show="this.showDialog"
      v-bind:agentType="this.agentType"
      v-bind:dialogToggle="this.toggleDialogClick"
    />
  </div>
</template>

<script>
import axios from "axios";
import AddAgentDialog from "./AddAgentDialog.vue";

export default {
  name: "AgentTypeList",
  data() {
    return {
      agentTypes: [],
      showDialog: false,
      agentType: ""
    };
  },
  components: {
    AddAgentDialog
  },
  methods: {
    newAgent(name) {
      this.agentType = name;
      this.toggleDialogClick();
    },
    toggleDialogClick() {
      this.showDialog = !this.showDialog;
    }
  },
  created() {
    axios
      .get(`http://localhost:8080/ATProjectWAR/rest/agents/classes`)
      .then(response => {
        this.agentTypes = response.data;
      })
      .catch(error => {
        alert(error);
      });
  }
};
</script>

<style scoped>
.agentTypeList {
  width: 100%;
}
.cardList {
  height: calc(100% - 30px);
  margin-top: 15px;
  overflow: auto;
}
.listHeader {
  display: flex;
}
.addTypeBtn {
  margin-top: 7px;
  left: 215px;
}
</style>