<!-- 权限管理 -->
<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <SimpleTable ref="table" :columns="columns" findApi="findAllPermissions"></SimpleTable>

        <el-dialog title="新建权限" :visible.sync="addDialog">
            <ParamForm 
                ref="addParamForm" 
                :params="[
                    {
                        label: '权限名',
                        key: 'permissionName',
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
                    permissionName: [{ required: true, message: '请输入权限名称', trigger: 'blur' }],
                    describe: [{ required: true, message: '请输入权限描述', trigger: 'blur' }]
                }">
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="addLoading" @click="addButtonOk">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="编辑权限" :visible.sync="editDialog">
            <ParamForm 
                ref="editParamForm" 
                :params="[
                    {
                        label: '权限名',
                        key: 'permissionName',
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
                    permissionName: [{ required: true, message: '请输入权限名称', trigger: 'blur' }],
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
            columns: [
                {
                    label: 'ID',
                    key: 'id',
                },
                {
                    label: '权限名',
                    key: 'permissionName'
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

        modify(row) {
            this.currEditRow = row;
            this.editDialog = true;

            this.$nextTick().then(() => {
                this.$refs.editParamForm.setFormValue('permissionName', row.permissionName);
                this.$refs.editParamForm.setFormValue('describe', row.describe);
            });
        },
        delete(row) {
            this.$confirm('此操作将永久删除该权限, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                fetch.deletePermission({
                    permissionId: row.id
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

        add() {
            this.addDialog = true;
        },
        search() {
            this.init();
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
                fetch.addPermission({
                    ...this.$refs.addParamForm.getFormValue()
                }).then((res) => {
                    if(res.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '添加成功!'
                        });
                        this.init();
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
                fetch.editPermission({
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