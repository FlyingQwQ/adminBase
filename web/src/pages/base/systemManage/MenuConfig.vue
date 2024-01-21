<!-- 菜单配置 -->
<template>
    <div class="menuManage">
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <el-row :gutter="10">
            <el-col :span="5">
                <el-tree default-expand-all highlight-current :data="treeData" :props="defaultProps" @node-click="handleNodeClick"></el-tree>
            </el-col>
            <el-col :span="19">
                <el-empty v-if="!currSelectMenu.id" description="请选择菜单"></el-empty>
                <div v-show="currSelectMenu.id">
                    <el-divider content-position="left">菜单信息</el-divider>
                    <ParamForm 
                        ref="ParamForm" 
                        :params="params"
                        :rules="{
                            name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }]
                        }">
                        <template #parentId="formValue">
                            <TreeSelect
                                v-if="treeData.length > 0"
                                ref="treeSelect" 
                                placeholder="不选默认为顶级" 
                                :props="defaultProps" 
                                :options="treeData" 
                                @getValue="parentSelect">
                            </TreeSelect>
                        </template>
                        <template #icon="{ formValue }">
                            <IconPicker :value.sync="formValue.icon"></IconPicker>
                        </template>
                    </ParamForm>

                    <el-divider content-position="left">配置资源按钮</el-divider>
                    <el-tag
                        v-for="resourceButton in resourceButtonList"
                        :key="resourceButton.resourceKey"
                        closable
                        @close="handleResourceButtonDelete(resourceButton.id)">
                        {{resourceButton.resourceName}}
                    </el-tag>
                    <span class="resourceButtonTips" v-if="resourceButtonList.length < 1">该页面没有资源按钮.</span>
                </div>
            </el-col>
        </el-row>



        
        <el-dialog title="新增菜单" :visible.sync="addMenuDialog">
            <ParamForm 
                ref="addMenuParamForm" 
                :params="addMenuParams" 
                :rules="{
                    name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
                    url: [{ required: true, message: '请输入菜单地址', trigger: 'blur' }]
                }">
                <template #parentId="{ formValue }">
                    <TreeSelect
                        v-if="treeData.length > 0"
                        placeholder="不选默认为顶级" 
                        :props="defaultProps" 
                        :options="treeData" 
                        @getValue="addParentSelect">
                    </TreeSelect>
                </template>
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="addMenuLoading" @click="addMenuOk">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog :title="`添加资源按钮(${currSelectMenu.name})`" :visible.sync="addResourceButtonDialog">
            <ParamForm 
                ref="addResourceButtonParamForm" 
                :params="addResourceButtonParams" 
                :rules="{
                    resourceId: [{ required: true, message: '请选择资源按钮', trigger: 'change' }]
                }">
                <template #resourceId="{ formValue }">
                    <el-select 
                        v-model="formValue.resourceId" 
                        style="width: 100%;">
                        <el-option v-for="item in allResourceButtonList" :key="item.id" :label="item.resourceName" :value="item.id"></el-option>
                    </el-select>
                </template>
            </ParamForm>
            <div slot="footer" class="dialog-footer">
                <el-button @click="cancelDialog">取 消</el-button>
                <el-button type="primary" :loading="addResourceButtonLoading" @click="addResourceButtonOk">确 定</el-button>
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
            save: { 
                disabled: true,
                click: this.save 
            },
            add: { 
                click: this.add 
            },
            addResBtn: { 
                disabled: true,
                click: this.addResourceButton 
            },
            delete: { 
                disabled: true,
                click: this.delete 
            },
        },
        treeData: [],
        defaultProps: {
            children: 'children',
            label: 'name',
            value: 'id'
        },
        currSelectMenu: {}, //当前选中的菜单

        params: [
            {
                label: '菜单名称',
                key: 'name',
                type: 'text'
            },
            {
                label: '菜单URL',
                key: 'url',
                type: 'text'
            },
            {
                label: '上级菜单',
                key: 'parentId',
                type: 'slot',
            },
            {
                label: '菜单图标',
                key: 'icon',
                type: 'slot',
                span: 8
            }
        ],
        resourceButtonList: [],

        addMenuDialog: false,
        addMenuLoading: false,
        addMenuParams: [
            {
                label: '菜单名称',
                key: 'name',
                type: 'text',
                span: 8
            },
            {
                label: '菜单URL',
                key: 'url',
                type: 'text',
                span: 8
            },
            {
                label: '上级菜单',
                key: 'parentId',
                type: 'slot',
                span: 8
            }
        ],

        allResourceButtonList: [],
        addResourceButtonDialog: false,
        addResourceButtonLoading: false,
        addResourceButtonParams: [
            {
                label: '选择按钮',
                key: 'resourceId',
                type: 'slot',
                span: 12
            }
        ],
    }
  },
  mounted() {
      this.init();
  },
  methods: {
    init() {
        fetch.getMenus().then((res) => {
            if(res.code == 1) {
                this.treeData = res.data;
            }
        });
    },
    handleNodeClick(data, node, nodeBody) {
        let { id, name, url, parentId, icon } = data;
        this.currSelectMenu = Object.assign(this.currSelectMenu, data);

        this.$refs.ParamForm.setFormValue('name', name);
        this.$refs.ParamForm.setFormValue('url', url);
        this.$refs.ParamForm.setFormValue('parentId', parentId);
        this.$refs.ParamForm.setFormValue('icon', icon);
        this.$refs.treeSelect.setValue(parentId);

        this.loadResourceButtonList(id);
        this.resourceButtonConfig.save.disabled = false;
        this.resourceButtonConfig.addResBtn.disabled = false;
        this.resourceButtonConfig.delete.disabled = false;
    },

    parentSelect(id) {
        if(id) {
            this.$refs.ParamForm.setFormValue('parentId', id);
        } else {
            this.$refs.ParamForm.setFormValue('parentId', 0);
        }
    },

    loadResourceButtonList(id) {
        fetch.findResourceButtonByMenuId({
            menuId: id
        }).then((res) => {
            this.resourceButtonList = res.data ?? [];
        })
    },

    // 资源按钮事件
    save() {
        this.$refs.ParamForm.validate().then(() => {
            fetch.modifyMenu({
                ...this.$refs.ParamForm.getFormValue(),
                menuId: this.currSelectMenu.id
            }).then((res) => {
                if(res.code == 1) {
                    this.init();
                    this.$message({
                        type: 'success',
                        message: '保存成功!'
                    });
                }
            });
        }).catch((error) => {});
    },
    add() {
        this.addMenuDialog = true;
    },
    addResourceButton() {
        this.addResourceButtonDialog = true;
        fetch.findAllResourceButton().then((res) => {
            if(res.code == 1) {
                this.allResourceButtonList = res.data.list;
            }
        });
    },
    delete() {
        this.$confirm('此操作将永久删除该菜单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
                if (action === 'confirm') {
                    instance.confirmButtonLoading = true;
                    instance.confirmButtonText = '执行中...';
                    fetch.deleteMenu({
                        menuId: this.currSelectMenu.id
                    }).then((res) => {
                        if(res.code == 1) {
                            this.init();
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }
                    }).finally(() => {
                        done();
                    });
                } else {
                    done();
                }
            }
        }).then(() => {}).catch(() => {});
    },


    cancelDialog() {
        this.addMenuDialog = false;
        this.addMenuLoading = false;
        this.addResourceButtonDialog = false;
        this.addResourceButtonLoading = false;
    },
    addMenuOk() {
        this.$refs.addMenuParamForm.validate().then(() => {
            this.addMenuLoading = true;
            fetch.addMenu({
                ...this.$refs.addMenuParamForm.getFormValue(),
            }).then((res) => {
                if(res.code == 1) {
                    this.init();
                }
            }).finally(() => {
                this.addMenuDialog = false;
                this.addMenuLoading = false;
            });
        }).catch((error) => {});
    },
    addResourceButtonOk() {
        this.$refs.addResourceButtonParamForm.validate().then(() => {
            this.addResourceButtonLoading = true;
            let form = this.$refs.addResourceButtonParamForm.getFormValue();
            fetch.addResourceButtonByMenuId({
                ...form,
                menuId: this.currSelectMenu.id
            }).then((res) => {
                if(res.code == 1) {
                    this.loadResourceButtonList(this.currSelectMenu.id);
                }
            }).catch((error) => {}).finally(() => {
                this.addResourceButtonDialog = false;
                this.addResourceButtonLoading = false;
            });
        }).catch((error) => {});
    },
    addParentSelect(id) {
        if(id) {
            this.$refs.addMenuParamForm.setFormValue('parentId', id);
        } else {
            this.$refs.addMenuParamForm.setFormValue('parentId', 0);
        }
    },
    handleResourceButtonDelete(resourceId) {
        this.$confirm('此操作将永久删除该菜单的资源按钮, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            beforeClose: (action, instance, done) => {
                if (action === 'confirm') {
                    instance.confirmButtonLoading = true;
                    instance.confirmButtonText = '执行中...';

                    fetch.deleteResourceButtonByMenuId({
                        resourceId,
                        menuId: this.currSelectMenu.id
                    }).then((res) => {
                        if(res.code == 1) {
                            this.loadResourceButtonList(this.currSelectMenu.id);
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            });
                        }
                    }).finally(() => {
                        done();
                    });
                } else {
                    done();
                }
            }
        }).then(() => {}).catch(() => {}); 
    }
  }
}
</script>

<style lang="less" scoped>

.menuManage {
    padding: 0 10px;
    box-sizing: border-box;
}

.el-tree {
    border-right: 1px solid #E4E7ED;
}

.resourceButtonTips {
    color: #909399;
    font-size: 13px;
}

.el-tag {
    user-select: none;
}
</style>