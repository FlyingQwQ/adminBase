<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <SimpleTable ref="table" :columns="columns" findApi="findDictionaryItemByCode" :params="params"></SimpleTable>
    </div>
</template>

<script>

export default {
    data() {
        return {
            resourceButtonConfig: {
                add: {
                    click: this.add
                },
            },
            params: {
                code: this.$route.query.code
            },
            columns: [
                { label: 'ID', key: 'id' },
                { label: '字典键', key: 'key' },
                { label: '字典值', key: 'value' },
                { label: '描述', key: 'describe' },
                {
                    label: '操作',
                    render: (h, row) => {
                        return h('div', [
                            h('el-button', {
                                props: {
                                    type: 'primary',
                                    size: 'small'
                                },
                                on: {
                                    click: () => { 
                                        this.edit(row);
                                    }
                                }
                            }, '编辑'),
                            h('el-button', {
                                props: {
                                    type: 'danger',
                                    size: 'small'
                                },
                                on: {
                                    click: () => { 
                                        this.delete(row);
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ]
        }
    },
    mounted() {
        this.$refs.table.loadData();
    },
    methods: {

    }
}
</script>
  
<style lang="less" scoped>
  
</style>