import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/Login'
import Signup from '@/pages/Signup'
import Dashboard from '@/pages/Dashboard'
import Overview from '@/pages/Overview'
import Profile from '@/pages/Profile'
import Scheduling from '@/pages/Scheduling'
import Events from '@/pages/Events'
import store from '../store/index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      beforeEnter: (to, from, next) => {
        if (store.state.isConnected) {
          next({path: '/dashboard/overview'})
        } else {
          next()
        }
      }
    },
    {
      path: '/signup',
      name: 'Signup',
      component: Signup,
      beforeEnter: (to, from, next) => {
        if (store.state.isConnected) {
          next({path: '/dashboard/overview'})
        } else {
          next()
        }
      }
    },
    {
      path: '/dashboard',
      name: 'dashboard',
      component: Dashboard,
      redirect: '/dashboard/overview',
      beforeEnter: (to, from, next) => {
        if (store.state.isConnected) {
          next()
        } else {
          next({path: '/'})
        }
      },
      children: [
        {
          path: 'overview',
          name: 'overview',
          component: Overview
        },
        {
          path: 'profile',
          name: 'profile',
          component: Profile
        },
        {
          path: 'scheduling',
          name: 'scheduling',
          component: Scheduling
        },
        {
          path: 'events',
          name: 'events',
          component: Events
        }
      ]
    }
  ]
})
