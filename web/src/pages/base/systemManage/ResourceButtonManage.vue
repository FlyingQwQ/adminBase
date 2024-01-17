<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <SimpleTable ref="table" :columns="columns" findApi="findAllResourceButton"></SimpleTable>

        <el-dialog title="新增资源按钮" :visible.sync="addResourceButtonDialog">
            <ParamForm 
                ref="addResourceButtonParamForm" 
                :params="addResourceButtonParams" 
                :rules="{
                    resourceName: [{ required: true, message: '请输入资源按钮名称', trigger: 'blur' }],
                    resourceKey: [{ required: true, message: '请输入资源按钮编码', trigger: 'blur' }]
                }">
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="addResourceButtonLoading" @click="addResourceButtonOk">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="编辑资源按钮" :visible.sync="editResourceButtonDialog">
            <ParamForm 
                ref="editResourceButtonParamForm" 
                :params="editResourceButtonParams" 
                :rules="{
                    resourceName: [{ required: true, message: '请输入资源按钮名称', trigger: 'blur' }],
                    resourceKey: [{ required: true, message: '请输入资源按钮编码', trigger: 'blur' }]
                }">
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="editResourceButtonLoading" @click="editResourceButtonOk">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { fetch } from '@/config';

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
                    label: 'ID',
                    key: 'id'
                },
                {
                    label: '资源名',
                    key: 'resourceName'
                },
                {
                    label: '资源编码',
                    key: 'resourceKey'
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
                                        this.modifyResBtn(row);
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
                                        this.deleteResBtn(row);
                                    }
                                }
                            }, '删除')
                        ]);
                    }
                }
            ],

            addResourceButtonDialog: false,
            addResourceButtonParams: [
                {
                    label: '资源名',
                    key: 'resourceName',
                    type: 'text',
                    span: 8
                },
                {
                    label: '资源编码',
                    key: 'resourceKey',
                    type: 'text',
                    span: 8
                },
            ],
            addResourceButtonLoading: false,

            editResourceButtonDialog: false,
            editResourceButtonParams: [
                {
                    label: '资源名',
                    key: 'resourceName',
                    type: 'text',
                    span: 8
                },
                {
                    label: '资源编码',
                    key: 'resourceKey',
                    type: 'text',
                    span: 8
                },
            ],
            editResourceButtonLoading: false,
            currEditResourceButton: {},
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
            this.addResourceButtonDialog = true;
        },

        modifyResBtn(row) {
            this.editResourceButtonDialog = true;
            this.currEditResourceButton = row;
            this.$nextTick().then(() => {
                this.$refs.editResourceButtonParamForm.setFormValue('resourceName', row.resourceName);
                this.$refs.editResourceButtonParamForm.setFormValue('resourceKey', row.resourceKey);
            });
            
        },
        deleteResBtn(row) {
            this.$confirm('此操作将永久删除该资源按钮, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                fetch.deleteResourceButton({
                    resourceId: row.id
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
            this.addResourceButtonDialog = false;
            this.editResourceButtonDialog = false;
        },
        addResourceButtonOk() {
            this.$refs.addResourceButtonParamForm.validate().then(() => {
                this.addResourceButtonLoading = true;
                fetch.addResourceButton({
                    ...this.$refs.addResourceButtonParamForm.getFormValue()
                }).then((res) => {
                    if(res.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '添加成功!'
                        });
                        this.init();
                    }
                }).finally(() => {
                    this.addResourceButtonLoading = false;
                    this.addResourceButtonDialog = false;
                });
            }).catch((error) => {});
        },
        editResourceButtonOk() {
            this.$refs.editResourceButtonParamForm.validate().then(() => {
                this.editResourceButtonLoading = true;
                fetch.editResourceButton({
                    ...this.$refs.editResourceButtonParamForm.getFormValue(),
                    resourceId: this.currEditResourceButton.id
                }).then((res) => {
                    if(res.code == 1) {
                        this.$message({
                            type: 'success',
                            message: '修改成功!'
                        });
                        this.init();
                    }
                }).finally(() => {
                    this.editResourceButtonLoading = false;
                    this.editResourceButtonDialog = false;
                });
            }).catch((error) => {});
        }
    }
}
</script>
  
<style lang="less" scoped>
  
</style>