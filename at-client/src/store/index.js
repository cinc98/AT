import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    activeAgents:[],
    messages:[],
    cars:[]
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
    fetchCars(state, cars){
      state.cars.length = 0;
        state.cars=cars;
     
    },
    fetchMessages(state, m){
        state.messages.push(JSON.parse(m));
     
    },
    
  },
  actions: {
  },
  modules: {
  }
})
