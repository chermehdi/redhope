import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/Login'
import Dashboard from '@/pages/Dashboard'
import Profile from '@/pages/Profile'
import Events from '@/pages/Events'
import CreateEvent from '@/pages/CreateEvent'
import EventList from '@/pages/EventList'
import Stock from '@/pages/Stock'

import Map from '@/components/Map'
import Overview from '@/components/Overview'
import Schedules from '@/components/Schedules'
import store from '../store'

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
          component: Profile,
          beforeEnter: (to, from, next) => {
            if (store.state.isConnected) {
              next()
            } else {
              next({path: '/'})
            }
          }
        },
        {
          path: 'map',
          name: 'map',
          component: Map,
          beforeEnter: (to, from, next) => {
            if (store.state.isConnected) {
              next()
            } else {
              next({path: '/'})
            }
          }
        },
         {
          path: 'schedules',
          name: 'schedule',
          component: Schedules,
          beforeEnter: (to, from, next) => {
            if (store.state.isConnected) {
              next()
            } else {
              next({path: '/'})
            }
          }
        },
        {
          path: 'events/create',
          name: 'create',
          component: CreateEvent,
          beforeEnter: (to, from, next) => {
            if (store.state.isConnected) {
              next()
            } else {
              next({path: '/'})
            }
          }
        },
        {
          path: 'events/list',
          name: 'EventList',
          component: EventList,
          beforeEnter: (to, from, next) => {
            if (store.state.isConnected) {
              next()
            } else {
              next({path: '/'})
            }
          }
        },
        {
          path: 'events',
          name: 'events',
          component: Events,
          beforeEnter: (to, from, next) => {
            if (store.state.isConnected) {
              next()
            } else {
              next({path: '/'})
            }
          }
        },
        {
          path: 'stock',
          name: 'stock',
          component: Stock,
          beforeEnter: (to, from, next) => {
            if (store.state.isConnected) {
              next()
            } else {
              next({path: '/'})
            }
          }
        }
      ]
    }
  ]
})
