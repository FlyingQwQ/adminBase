
let homeInfo = {
    url: '/main/',
    name: '首页',
    closable: false,
    breadcrumb: []
}

export default {
    state: {
        tabsList: [
            { ...homeInfo }
        ], // 已经打开的页签
        activityTabs: {
            ...homeInfo
        }, // 活动页签
        breadcrumb: [...homeInfo.breadcrumb] // 面包屑
    },
    mutations: {
        PUSH_TABS_LIST(state, data) {
            state.tabsList.push(data);
        },
        SET_TABS_LIST(state, data) {
            state.tabsList = data;
        },
        SET_ACTIVITY_TAB(state, data) {
            state.activityTabs = data;
        },
        RESET_TAB_CACHEKEY(state, tabName) {
            state.tabsList.map((item) => {
                if(item.name == tabName) {
                    item.cacheKey = Math.floor(Math.random() * 10000);
                }
            });
        },
        SET_BREADCRUMB(state, data) {
            state.breadcrumb = data;
        }
    },
    actions: {

    },
    getters: {
        
    }
}