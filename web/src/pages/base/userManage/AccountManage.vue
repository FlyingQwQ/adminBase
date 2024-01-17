<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <SimpleTable ref="table" :columns="columns" findApi="getAllUserInfo"></SimpleTable>


    </div>
</template>

<script>
import { fetch } from '../config';
import tabsTool from '@/utils/tabsTool';

export default {
    data() {
        return {
            resourceButtonConfig: {
                add: {
                    click: this.add
                }
            },
            columns: [
                {
                    label: '序号',
                    key: 'id',
                },
                {
                    label: '用户名',
                    key: 'username'
                },
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
                                        this.modify(row);
                                    }
                                }
                            }, '修改'),
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
        this.init();
    },
    methods: {
        init() {
            this.$refs.table.loadData();
        },

        add() {

        },
        modify(row) {
            tabsTool.openTab({ 
                url: `/main/accountEdit?userId=${row.id}`, 
                name: `账号编辑(${row.username})`, 
            });
        },
        delete(row) {
            this.$confirm('此操作将永久删除该权限, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                fetch.deleteUser({
                    userId: row.id
                }).then((res) => {
                    if(res.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.init();
                    }
                });
            }).catch(() => {});
        }
    }
}
</script>
  
<style lang="less" scoped>
  
</style>