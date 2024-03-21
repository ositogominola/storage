import { createStore } from 'vuex'

export default createStore({
  state: {
    idRecurso: null,
  },
  mutations: {
    actualizarIdRecurso(state, nuevoId) {
      state.idRecurso = nuevoId;
    },
  },
})
