<template>
  <div class="home">
    <agent-type-list />
    <send-message />
    <div class="thirdColumn">
      <message-list />
      <started-agent-list />
    </div>
  </div>
</template>

<script>
import AgentTypeList from "../components/AgentTypeList.vue";
import MessageList from "../components/MessageList.vue";
import StartedAgentList from "../components/StartedAgentList.vue";
import SendMessage from "../components/SendMessage.vue";

export default {
  name: "Home",
  components: {
    AgentTypeList,
    MessageList,
    StartedAgentList,
    SendMessage
  },
  methods: {
    newAgent(agent) {
      this.$store.commit("fetchActiveAgentsString", agent);
    },
    newMessage(m) {
      this.$store.commit("fetchMessages", m);
    }
  },
  created() {
    const vm = this;
    this.socket = new WebSocket(`ws://localhost:8080/ATProjectWAR/ws`);
    this.socket.onopen = function(event) {
      console.log(event);
    };
    this.socket.onmessage = function(event) {
      if (JSON.parse(event.data).performative === undefined) {
        vm.newAgent(event.data);
      } else {
             if (JSON.parse(event.data).sender !== null) vm.newMessage(event.data);
      }
    };
  }
};
</script>

<style>
.home {
  position: fixed;
  height: calc(100vh - 64px);
  width: 100%;
  top: 64px;
  display: flex;
  flex-direction: row;
  flex-basis: auto;
}
.thirdColumn {
  display: flex;
  flex-direction: column;
  width: 100%;
}
</style>
