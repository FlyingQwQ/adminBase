<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
        <el-row :gutter="10">
            <el-col :span="7">
                <SimpleTable 
                    ref="table" 
                    :columns="columns" 
                    findApi="findAllRoles"
                    @row-click="rowClick"></SimpleTable>
            </el-col>
            <el-col :span="17">
                <el-tree 
                    default-expand-all 
                    highlight-current 
                    :show-checkbox="!!currRow"
                    check-strictly
                    node-key="mark"
                    :default-checked-keys="checked"
                    :data="treeData" 
                    :props="defaultProps"
                    @check="check">
                </el-tree>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import { fetch } from '../config';

export default {
    data() {
        return {
            resourceButtonConfig: {
                save: { 
                    disabled: true,
                    click: this.save 
                },
            },
            columns: [
                {
                    label: '序号',
                    key: 'id'
                },
                {
                    label: '角色名称',
                    key: 'describe'
                },
            ],

            treeData: [],
            defaultProps: {
                children: 'children',
                label: 'name',
                value: 'id'
            },

            currRow: null,
            treeCheck: [],
            checked: [],    // 默认选中
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.$refs.table.loadData();
            
        },

        loadRoleMenu(roleId) {
            this.treeCheck = [];
            this.checked = [];

            fetch.getAllRoleMenu({
                roleId
            }).then((res) => {
                if(res.code == 1) {
                    this.treeData = this.nodeMerge(res.data);
                }
            });
        },
        rowClick(row, column, event) {
            this.currRow = row;
            this.resourceButtonConfig.save.disabled = false;
            this.loadRoleMenu(row.id);
        },

        nodeMerge(item) {
            return item.map((item) => {
                item.mark = encodeURIComponent(item.id + item.name);    // 树节点唯一标记
                if(item.roleHave) {
                    this.checked.push(item.mark);
                }

                const newItem = { ...item };
                if(newItem.children && newItem.children.length > 0) {
                    newItem.children = this.nodeMerge(newItem.children);
                }
                if(newItem.resourceButtonEntities && newItem.resourceButtonEntities.length > 0) {
                    newItem.children = newItem.children.concat(newItem.resourceButtonEntities.map((resourceButton) => {
                        resourceButton.name = `${resourceButton.resourceName} (资源按钮)`;
                        resourceButton.menuId = newItem.id;
                        resourceButton.mark = encodeURIComponent((newItem.id + newItem.name) + (resourceButton.id + resourceButton.resourceName));    // 树节点唯一标记
                        if(resourceButton.roleHave) {
                            this.checked.push(resourceButton.mark);
                        }
                        return resourceButton;
                    }));
                }
                return newItem;
            });
        },

        check(data, state) {
            this.treeCheck = state.checkedNodes;
        },
        save() {
            let menus = [];
            let resourceButtons = [];
            this.treeCheck.map((item) => {
                if(item.url) {
                    menus.push(item.id);
                } else {
                    resourceButtons.push({  resourceId: item.id, menuId: item.menuId });
                }
            });

            fetch.setRoleMenu({
                roleId: this.currRow.id,
                menus,
                resourceButtons
            }, true).then((res) => {
                if(res.code == 1) {
                    this.$message.success('保存成功');
                    this.$bus.$emit('reloadMenu');
                }
            });
        }
    }
}
</script>
  
<style lang="less" scoped>
  
</style>