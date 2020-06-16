import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Predict from '../views/Predict.vue'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/predict',
    name: 'Predict',
    component: Predict
  }
]

const router = new VueRouter({
  routes
})

export default router
