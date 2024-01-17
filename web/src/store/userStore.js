
export default {
    state: {
        userInfo: {}, // 用户信息
    },
    mutations: {
        SET_USER_INFO(state, data) {
            state.userInfo = data;
        },
        CLEAR_USER_INFO(state) {
            state.userInfo = {};
            localStorage.removeItem('userInfo');
        }
    },
    actions: {

    },
    getters: {
        
    }
}