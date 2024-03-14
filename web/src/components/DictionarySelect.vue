<template>
    <el-select 
        v-model="internalValue" 
        :disabled="disabled"
        :placeholder="placeholder"
        style="width: 100%;">
        <el-option v-for="item in data" :key="item.id" :label="item.key" :value="item.value"></el-option>
    </el-select>
</template>

<script>
import { fetch } from '@/config';

export default {
    props: {
        code: String,
        disabled: Boolean,
        placeholder: String,
        value: null
    },
    data() {
        return {
            data: [],
            internalValue: this.value
        }
    },
    mounted() {
        this.loadData();
    },
    methods: {
        loadData() {
            fetch.findDictionaryItemByCode({
                code: this.code ?? ''
            }).then((res) => {
                if(res.code == 1) {
                    this.data = res.data.list;
                }
            });
        }
    },
    watch: {
        value(newValue) {
            this.internalValue = String(newValue);
        },
        internalValue(newValue) {
            this.$emit('update:value', newValue);
        }
    }
}
</script>

<style lang="less" scoped>

</style>