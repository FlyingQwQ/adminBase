<template>
    <div>
        <template v-if="item.children.length > 0">
            <el-submenu :index="item.name">
                <template slot="title">
                    <i :class="item.icon ?? ''"></i>
                    <span>{{ item.name }}</span>
                </template>
                <template v-for="item in item.children">
                    <MenuItem :item="item"></MenuItem>
                </template>
            </el-submenu>
        </template>
        <template v-else>
            <el-menu-item :index="item.url" :item="item">
                <i :class="item.icon ?? ''"></i>
                <template slot="title">
                    <span>{{ item.name }}</span>
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