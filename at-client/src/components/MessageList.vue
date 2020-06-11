<template>
  <div class="messageList">
    <v-card class="mx-auto cardMessageList" max-width="600" raised>
        <v-subheader>MESSAGE LIST</v-subheader>
        

    </v-card>
  </div>
</template>

<script>
export default {
  name: "MessageList"
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