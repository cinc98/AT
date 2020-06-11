import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    activeAgents:[]
  },
  mutations: {
    fetchActiveAgents(state, agents){
      state.activeAgents.length = 0;
      agents.forEach((agent) =>{
        state.activeAgents.push(agent);
      })
    },
    newAgent(state, msg) {
      state.activeAgents.push(JSON.parse(msg));
  },
  },
  actions: {
  },
  modules: {
  }
})
