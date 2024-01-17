import Vue from 'vue'
import Vuex from 'vuex'

import userStore from './userStore';
import tabsStore from './tabsStore';

Vue.use(Vuex);

export default new Vuex.Store({
    modules: {
        user: userStore,
        tabs: tabsStore
    }
});