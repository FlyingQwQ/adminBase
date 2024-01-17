import router from "@/router";
import store from "@/store";
import urlTools from "./urlTool";

export default class tabsTool {

    static openTab(info) {
        let tabInfo = JSON.parse(JSON.stringify(info));
        tabInfo.closable = true;
        tabInfo.cacheKey = Math.floor(Math.random() * 10000);
        if(info.id) {
            tabInfo.menuId = info.id;
        } else {
            tabInfo.menuId = this.getCurrentTabInfo().id;   //用于继承父页面的菜单ID
        }
        let isExist = !!store.state.tabs.tabsList.find((item) => item.name === tabInfo.name);
        if(!isExist) {
            store.commit('PUSH_TABS_LIST', tabInfo);
        }
        this.selectTab(tabInfo.name);
    }

    static closeTab(name) {
        store.commit('SET_TABS_LIST', store.state.tabs.tabsList.filter((item) => item.name !== name));
        let activityTabs = store.state.tabs.tabsList[store.state.tabs.tabsList.length - 1];
        this.selectTab(activityTabs.name);
    }

    static selectTab(name) {
        let tabsList = store.state.tabs.tabsList;
        let tabInfo = JSON.parse(JSON.stringify(tabsList.find((item) => item.name === name)));
        // 切换路由
        router.push({path: tabInfo.url, query: {
            cacheKey: tabInfo.cacheKey,
            menuId: tabInfo.menuId,
            ...urlTools.getUrlAllParams(tabInfo.url),
        }});
        // 切换标签页
        store.commit('SET_ACTIVITY_TAB', tabInfo);
    }

    static getCurrentTabInfo() {
        return store.state.tabs.activityTabs;
    }

    static closeCurrentTab() {
        let activityTabs = this.getCurrentTabInfo();
        this.closeTab(activityTabs.name);
    }

    static refreshCurrentTab() {
        let activityTabs = this.getCurrentTabInfo();
        store.commit('RESET_TAB_CACHEKEY', activityTabs.name);
        this.selectTab(activityTabs.name);
    }

}