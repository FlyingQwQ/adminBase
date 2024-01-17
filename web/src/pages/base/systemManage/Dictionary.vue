<template>
    <div>
        <SimpleTable ref="table" :columns="columns" findApi="findAllDictionary"></SimpleTable>
    </div>
</template>

<script>
import tabsTool from '@/utils/tabsTool';

export default {
    data() {
        return {
            columns: [
                { label: 'ID', key: 'id' },
                { label: '字典名', key: 'name' },
                { label: '字典编码', key: 'code' },
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
                                        this.showList(row);
                                    }
                                }
                            }, '列表'),
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
        showList(row) {
            tabsTool.openTab({ 
                name: `数据字典(详细) - ${row.name}`, 
                url: `/main/dictionaryList?code=${row.code}` 
            });
        },
        edit(row) {

        },
        delete(row) {
            console.log(row);
        }
    }
}
</script>
  
<style lang="less" scoped>
  
</style>