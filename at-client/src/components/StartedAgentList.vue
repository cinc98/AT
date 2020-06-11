<template>
  <div class="startedAgentList">
    <v-card class="mx-auto cardStartedAgentList" max-width="600" raised>
      <v-subheader>ACTIVE AGENTS</v-subheader>

      <v-list-item v-for="(at,i) in agents" :key="i">
        <v-list-item-content>
          <v-list-item-title class="mb-1">{{at.id.type.name}}@{{at.id.name}}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "StartedAgentList",
  data() {
    return {
    };
  },
  methods: {
    addActiveAgents(event) {
      this.$store.commit("fetchActiveAgents", event);
    }
  },
  computed: {
    agents() {
      return this.$store.state.activeAgents;
    }
  },
  created() {
    axios
      .get(`http://localhost:8080/ATProjectWAR/rest/agents/running`)
      .then(response => {
        this.addActiveAgents(response.data);
      })
      .catch(error => {
        alert(error);
      });
  }
};
</script>

<style scoped>
.startedAgentList {
  height: 100%;
}
.cardStartedAgentList {
  height: calc(100% - 30px);
  margin-top: 15px;
  overflow: auto;
}
</style>