<template>
    <div v-if="resourceButtonList.length > 0" class="resourceButton">
        <el-button-group>
            <el-button v-for="item in resourceButtonList" v-if="item.resourceKey in config"
                :key="item.id"
                type="primary" 
                size="small" 
                :disabled="config[item.resourceKey]?.disabled"
                @click="() => { config[item.resourceKey].click() }">
                {{ item.resourceName }}
            </el-button>
        </el-button-group>
    </div>
</template>

<script>
import { fetch } from '@/config';

export default {
    props: {
        config: {
            type: Object,
            required: true,
            default: () => {}
        }
    },
    data() {
        return {
            resourceButtonList: []
        }
    },
    mounted() {
      this.loadResourceButtonList();
    },
    methods: {
        loadResourceButtonList(id) {
            fetch.findResourceButtonByMenuId({
                menuId: this.$route.query.menuId
            }).then((res) => {
                this.resourceButtonList = res.data ?? [];
            });
        }
    }
}
</script>
  
<style lang="less" scoped>
.resourceButton {
    padding: 10px;
    background-color: white;
    text-align: right;
    border-bottom: 1px solid #E4E7ED;
    box-sizing: border-box;
}
</style>