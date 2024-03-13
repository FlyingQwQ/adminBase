<!-- 角色管理 -->
<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <ParamForm ref="ParamForm" :params="params"></ParamForm>
        <SimpleTable ref="table" :columns="columns" findApi="findAllRoles" :params="filterParams"></SimpleTable>
        
        <el-dialog title="新建角色" :visible.sync="addDialog">
            <ParamForm 
                ref="addParamForm" 
                :params="[
                    {
                        label: '角色名',
                        key: 'roleName',
                        type: 'text',
                        span: 8
                    },
                    {
                        label: '描述',
                        key: 'describe',
                        type: 'text',
                        span: 8
                    },
                ]" 
                :rules="{
                    roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
                    describe: [{ required: true, message: '请输入角色描述', trigger: 'blur' }]
                }">
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="addLoading" @click="addButtonOk">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="编辑角色" :visible.sync="editDialog">
            <ParamForm 
                ref="editParamForm" 
                :params="[
                    {
                        label: '角色名',
                        key: 'roleName',
                        type: 'text',
                        span: 8
                    },
                    {
                        label: '描述',
                        key: 'describe',
                        type: 'text',
                        span: 8
                    },
                ]" 
                :rules="{
                    roleName: [{ required: true, message: '请输入权限名称', trigger: 'blur' }],
                    describe: [{ required: true, message: '请输入权限描述', trigger: 'blur' }]
                }">
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="editLoading" @click="editButtonOk">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { fetch } from '../config';

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
                    label: '角色名',
                    key: 'roleName',
                    type: 'text'
                },
                {
                    label: '描述',
                    key: 'describe',
                    type: 'text'
                },
            ],
            columns: [
                {
                    label: 'ID',
                    key: 'id'
                },
                {
                    label: '角色名',
                    key: 'roleName'
                },
                {
                    label: '描述',
                    key: 'describe'
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
            ],
            filterParams: {},

            addDialog: false,
            addLoading: false,

            currEditRow: {},
            editDialog: false,
            editLoading: false,
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
            this.editDialog = true;
            this.currEditRow = row;

            this.$nextTick().then(() => {
                this.$refs.editParamForm.setFormValue('roleName', row.roleName);
                this.$refs.editParamForm.setFormValue('describe', row.describe);
            });
        },
        delete(row) {
            this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                fetch.deleteRole({
                    roleId: row.id
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
        },

        cancelDialog() {
            this.addDialog = false;
            this.addLoading = false;
            this.editDialog = false;
            this.editLoading = false;
        },
        addButtonOk() {
            this.$refs.addParamForm.validate().then(() => {
                this.addLoading = true;
                fetch.addRole({
                    ...this.$refs.addParamForm.getFormValue()
                }).then((res) => {
                    if(res.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '添加成功!'
                        });
                        this.init();
                        this.$refs.addParamForm.resetForm();
                    }
                }).finally(() => {
                    this.addLoading = false;
                    this.addDialog = false;
                });
            }).catch((error) => {});
        },
        editButtonOk() {
            this.$refs.editParamForm.validate().then(() => {
                this.editLoading = true;
                fetch.editRole({
                    ...this.$refs.editParamForm.getFormValue(),
                    id: this.currEditRow.id
                }).then((res) => {
                    if(res.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '修改成功!'
                        });
                        this.init();
                    }
                }).finally(() => {
                    this.editLoading = false;
                    this.editDialog = false;
                });
            }).catch((error) => {});
        }
    }
}
</script>
  
<style lang="less" scoped>
  
</style>