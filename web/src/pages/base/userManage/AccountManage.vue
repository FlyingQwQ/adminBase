<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <ParamForm ref="ParamForm" :params="params"></ParamForm>
        <SimpleTable ref="table" :columns="columns" findApi="getAllUserInfo" :params="filterParams"></SimpleTable>

        <el-dialog title="注册新账号" :visible.sync="addDialog">
            <ParamForm 
                ref="addParamForm" 
                :params="[
                    {
                        label: '用户名',
                        key: 'userName',
                        type: 'text',
                        span: 8
                    },
                    {
                        label: '密码',
                        key: 'password',
                        type: 'password',
                        span: 8
                    },
                    {
                        label: '重复密码',
                        key: 'confirmPassword',
                        type: 'password',
                        span: 8
                    },
                ]" 
                :rules="{
                    userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                    confirmPassword: [{ required: true, message: '请输入重复密码', trigger: 'blur' }]
                }">
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="addLoading" @click="addButtonOk">确 定</el-button>
            </div>
        </el-dialog>
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
                },
                search: {
                    click: this.search
                }
            },
            params: [
                {
                    label: '用户名',
                    key: 'username',
                    type: 'text'
                }
            ],
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
                    label: '状态',
                    render: (h, row) => {
                        return h('el-tag', {
                            props: {
                                type: row.state === 0 ? 'success' : 'danger'
                            }
                        }, row.state === 0 ? '正常' : '禁用');
                    },
                    key: 'state'
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
                            row.del === 0 ? h('el-button', {
                                props: {
                                    type: 'danger',
                                    size: 'small'
                                },
                                on: {
                                    click: () => { 
                                        this.delete(row);
                                    }
                                }
                            }, '删除') : null
                        ]);
                    }
                }
            ],
            filterParams: {},

            addDialog: false,
            addLoading: false,

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
            this.addDialog = true;
        },
        search() {
            this.filterParams = this.$refs.ParamForm.getFormValue();
            this.$nextTick(() => {
                this.init();
            });
        },
        modify(row) {
            tabsTool.openTab({ 
                url: `/main/accountEdit?userId=${row.id}`, 
                name: `账号编辑(${row.username})`, 
            });
        },
        delete(row) {
            this.$confirm('此操作将永久删除该账号, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                fetch.deleteUser({
                    userId: row.id
                }).then((res) => {
                    if(res.code == 1) {
                        this.$message.success('删除成功!');
                        this.init();
                    } else {
                        this.$message.error(res.data);
                    }
                });
            }).catch(() => {});
        },

        cancelDialog() {
            this.addDialog = false;
        },
        addButtonOk() {
            this.$refs.addParamForm.validate().then(() => {
                let registerInfo = this.$refs.addParamForm.getFormValue();
                if(registerInfo.password != registerInfo.confirmPassword) {
                    this.$message.error('两次输入的密码不一致');
                    return;
                }
                fetch.register({
                    ...registerInfo
                }).then((res) => {
                    if(res.code == 1) {
                        this.$message.success('添加成功');
                        this.addDialog = false;
                        this.init();
                    }
                });
            }).catch((error) => {});
            

        }
    }
}
</script>
  
<style lang="less" scoped>
  
</style>