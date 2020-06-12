import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    activeAgents:[]
  },
  mutations: {
    fetchActiveAgentsString(state, agents){
      state.activeAgents.length = 0;
      state.activeAgents=JSON.parse(agents);
     
    },
    fetchActiveAgents(state, agents){
      state.activeAgents.length = 0;
        state.activeAgents=agents;
     
    },
    
  },
  actions: {
  },
  modules: {
  }
})
