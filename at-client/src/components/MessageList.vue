<template>
  <div class="messageList">
    <v-card class="mx-auto cardMessageList" max-width="600" raised>
      <v-subheader>MESSAGE LIST</v-subheader>
      <v-list-item v-for="(at,i) in messages" :key="i" link>
        <v-list-item-content>
          <v-list-item-title class="mb-1">
            Sender: {{at.sender.type.name}}@{{at.sender.name}}
            <br />Receivers:{{at.receivers[0].type.name}}@{{at.receivers[0].name}}
            <br />
            Content: {{at.content}}
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "MessageList",
  computed: {
    messages() {
      return this.$store.state.messages;
    },
  
  },
  methods:{
    getText(ads){
      console.log(ads);
      
    }
  }
};
</script>

<style scoped>
.messageList {
  height: 100%;
}
.cardMessageList {
  height: calc(100% - 30px);
  margin-top: 15px;
  overflow: auto;
}
</style>