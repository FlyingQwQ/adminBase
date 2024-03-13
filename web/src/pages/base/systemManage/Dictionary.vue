<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <ParamForm ref="ParamForm" :params="params"></ParamForm>
        <SimpleTable ref="table" :columns="columns" findApi="findAllDictionary" :params="filterParams"></SimpleTable>

        <el-dialog title="新建字典" :visible.sync="addDialog">
            <ParamForm 
                ref="addParamForm" 
                :params="[
                    {
                        label: '字典名',
                        key: 'name',
                        type: 'text',
                        span: 8
                    },
                    {
                        label: '字典编码',
                        key: 'code',
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
                    name: [{ required: true, message: '请输入字典名', trigger: 'blur' }],
                    code: [{ required: true, message: '请输入字典编码', trigger: 'blur' }],
                    describe: [{ required: true, message: '请输入字典描述', trigger: 'blur' }]
                }">
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="addLoading" @click="addButtonOk">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="编辑字典" :visible.sync="editDialog">
            <ParamForm 
                ref="editParamForm" 
                :params="[
                    {
                        label: '字典名',
                        key: 'name',
                        type: 'text',
                        span: 8
                    },
                    {
                        label: '字典编码',
                        key: 'code',
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
                    name: [{ required: true, message: '请输入字典名', trigger: 'blur' }],
                    code: [{ required: true, message: '请输入字典编码', trigger: 'blur' }],
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
                    label: '字典名',
                    key: 'name',
                    type: 'text'
                },
                {
                    label: '字典编码',
                    key: 'code',
                    type: 'text'
                },
                {
                    label: '描述',
                    key: 'describe',
                    type: 'text'
                },
            ],
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
            ],
            filterParams: {},

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

        showList(row) {
            tabsTool.openTab({ 
                name: `数据字典(详细) - ${row.name}`, 
                url: `/main/dictionaryList?code=${row.code}` 
            });
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
                fetch.deleteDictionary({
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

        cancelDialog() {
            this.addDialog = false;
            this.editDialog = false;
        },
        addButtonOk() {
            this.$refs.addParamForm.validate().then((valid) => {
                this.addLoading = true;
                fetch.addDictionary({
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
            this.$refs.editParamForm.validate().then((valid) => {
                this.editLoading = true;
                fetch.editDictionary({
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