<template>
    <div>
        <template v-if="item.children.length > 0">
            <el-submenu :index="item.name">
                <template slot="title">
                    <i :class="item.icon ?? ''"></i>
                    {{ item.name }}
                </template>
                <template v-for="item in item.children">
                    <MenuItem :item="item" :key="item.name"></MenuItem>
                </template>
            </el-submenu>
        </template>
        <template v-else>
            <el-menu-item :index="item.url" :item="item">
                <template slot="title">
                    <i :class="item.icon ?? ''"></i>
                    {{ item.name }}
                </template>
            </el-menu-item>
        </template>
    </div>
</template>

<script>
import { fetch } from '@/config';

export default {
    props: ['item'],
    data() {
        return {
            iconName: 'el-icon-menu'
        }
    },
    mounted() {
        
    },
    methods: {
        
    }
}
</script>

<style lang="less" scoped>

</style>

