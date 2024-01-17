<template>
    <el-menu
    class="el-menu-vertical-demo"
    :default-active="$store.state.tabs.activityTabs.url"
    @open="handleOpen"
    @close="handleClose"
    @select="handleSelect">
        <template v-for="item in menuList">
            <MenuItem :item="item" :key="item.name"></MenuItem>
        </template>
    </el-menu>
</template>

<script>
import { fetch } from '@/config';
import tabsTool from '@/utils/tabsTool';

export default {
    data() {
        return {
            menuList: []
        }
    },
    mounted() {
        this.loadMenu();
    },
    methods: {
        loadMenu() {
            fetch.getUserMenus().then((res) => {
                if(res.code == 1) {
                    this.menuList = res.data;
                }
            });
        },

        handleOpen(index, indexPath) {
            
        },
        handleClose(index, indexPath) {
            
        },
        handleSelect(index, indexPath, vnode) {
            let attr = vnode.$attrs.item;
            tabsTool.openTab({ url: attr.url, name: attr.name, id: attr.id });
        }
    }
}
</script>

<style lang="less" scoped>
.el-menu {
    border-right: none;
}
</style>