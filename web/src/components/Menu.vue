<template>
    <el-menu
        v-if="menuList.length > 0"
        :collapse="false"
        class="el-menu-vertical-demo"
        :default-active="$store.state.tabs.activityTabs.url"
        @open="handleOpen"
        @close="handleClose"
        @select="handleSelect">
        <template v-for="item in menuList">
            <MenuItem :item="item" :key="item.name"></MenuItem>
        </template>
    </el-menu>
    <el-empty v-else :image-size="100" description="没有分配菜单"></el-empty>
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
        this.$bus.$on('reloadMenu', () => {
            this.loadMenu();
        });
    },
    beforeDestroy() {
        this.$bus.$off('reloadMenu');
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
            let breadcrumb = [...indexPath];
            breadcrumb[breadcrumb.length - 1] = attr.name;
            tabsTool.openTab({ url: attr.url, name: attr.name, id: attr.id, breadcrumb });
        }
    }
}
</script>

<style lang="less" scoped>
.el-menu {
    border-right: none;
}

</style>