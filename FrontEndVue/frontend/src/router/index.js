import { createRouter, createWebHistory } from 'vue-router'
import axios from 'axios';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/HomeViewAdmin.vue'),
      children: [
        {
          path: '',
          name: 'HomeMain',
          component: () => import('../views/HomePageView.vue')
        },
        {
          path: 'factorys',
          name: 'factorys',
          component: () => import('../views/FactorysView.vue')
        },{
          path: 'ItemsUserPerfil/:id/:rol',
          name: 'ItemsUserPerfil',
          component:()=> import('../views/ItemsView.vue'),
          props:true
        },{
          path: 'AdminRoles',
          name: 'AdminRoles',
          component:()=>import('../views/User/RoleAdminView.vue')
        },{
          path: 'AdminPerfiles',
          name: 'AdminPerfiles',
          component:()=>import('../views/User/PerfilesPermAdminView.vue')
        },{
          path: 'UsersAdmin',
          name: 'UsersAdmin',
          component:()=>import('../views/User/UserAdminView.vue')
        }
      ],
      meta: { requiresAuth: true }
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!await isLoggedIn()) {
      next({
        path: '/login',
      })
    } else {
      next()
    }
  } else {
    next() // Asegúrate de siempre llamar a next()!
  }
})



async function isLoggedIn() {
  try {
    const response = await axios.get('http://127.0.0.1:7777/isAuthenticated', { withCredentials: true });
    if (response.status === 200) {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    return false;
  }
}


export default router
