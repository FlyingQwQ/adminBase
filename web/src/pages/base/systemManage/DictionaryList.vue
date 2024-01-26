<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <SimpleTable ref="table" :columns="columns" findApi="findDictionaryItemByCode" :params="params"></SimpleTable>

        <el-dialog title="新建字典项" :visible.sync="addDialog">
            <ParamForm 
                ref="addParamForm" 
                :params="[
                    {
                        label: '字典键',
                        key: 'key',
                        type: 'text',
                        span: 8
                    },
                    {
                        label: '字典值',
                        key: 'value',
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
                    key: [{ required: true, message: '请输入字典键', trigger: 'blur' }],
                    value: [{ required: true, message: '请输入字典值', trigger: 'blur' }],
                    describe: [{ required: true, message: '请输入字典描述', trigger: 'blur' }]
                }">
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="addLoading" @click="addButtonOk">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="编辑字典项" :visible.sync="editDialog">
            <ParamForm 
                ref="editParamForm" 
                :params="[
                   {
                        label: '字典键',
                        key: 'key',
                        type: 'text',
                        span: 8
                    },
                    {
                        label: '字典值',
                        key: 'value',
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
                    key: [{ required: true, message: '请输入字典键', trigger: 'blur' }],
                    value: [{ required: true, message: '请输入字典值', trigger: 'blur' }],
                    describe: [{ required: true, message: '请输入字典描述', trigger: 'blur' }]
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
            ],

            addDialog: false,
            addLoading: false,

            editDialog: false,
            editLoading: false,

        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.dictionaryId = this.$route.query.userId;
            this.$refs.table.loadData();
        },

        edit(row) {
            this.editDialog = true;

            this.$nextTick().then(() => {
                this.$refs.editParamForm.setFormValues(row);
            });
        },
        delete(row) {
            this.$confirm('此操作将永久删除该字典, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                fetch.deleteDictionaryItem({
                    id: row.id
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

        cancelDialog() {
            this.addDialog = false;
            this.editDialog = false;
        },
        addButtonOk() {
            this.$refs.addParamForm.validate().then((valid) => {
                this.addLoading = true;
                fetch.addDictionaryItem({
                    ...this.$refs.addParamForm.getFormValue(),
                    dictionaryCode: this.$route.query.code
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
            this.$refs.editParamForm.validate().then((valid) => {
                this.editLoading = true;
                fetch.editDictionaryItem({
                    ...this.$refs.editParamForm.getFormValue()
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