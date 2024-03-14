<!-- 账号编辑 -->
<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>

        <el-divider content-position="left">账号信息</el-divider>
        <ParamForm 
            ref="ParamForm" 
            :params="params">
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
        </ParamForm>

        <el-divider content-position="left">账号角色</el-divider>
        <el-checkbox-group v-model="roleCheck" >
            <el-checkbox 
                v-for="item in roleList"
                :key="item.id"
                :label="item.id">
                {{ item.describe }}
            </el-checkbox>
        </el-checkbox-group>
    </div>
</template>

<script>
import { fetch } from '../config';
import tabsTool from '@/utils/tabsTool';

export default {
    data() {
        return {
            resourceButtonConfig: {
                save: {
                    click: this.save 
                }
            },
            params: [
                {
                    label: '用户名',
                    key: 'username',
                    type: 'text',
                    disabled: true
                },
                {
                    label: '密码',
                    key: 'password',
                    type: 'password',
                    placeholder: '不填则不修改'
                },
                {
                    label: '账号状态',
                    key: 'state',
                    type: 'lookup',
                    code: 'ZHZT'
                },
            ],

            userId: 0,

            roleList: [],
            roleCheck: [],
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            this.userId = this.$route.query.userId;

            fetch.getUserInfoById({
                userId: this.userId
            }).then((res) => {
                if(res.code == 1) {
                    this.$refs.ParamForm.setFormValues(res.data);
                }
            });

            fetch.findAllRoles().then((res) => {
                if(res.code == 1) {
                    this.roleList = res.data.list;
                    this.setRoleDefaultCheck();
                }
            });

            
        },

        setRoleDefaultCheck() {
            fetch.findRoles({
                userId: this.userId
            }).then((res) => {
                if(res.code == 1) {
                    this.roleCheck = res.data.map(item => item.id);
                }
            });
        },

        save() {
            fetch.editUser({
                ...this.$refs.ParamForm.getFormValue(),
                roles: this.roleCheck
            }, true).then((res) => {
                if(res.code == 1) {
                    this.$message.success('保存成功');
                    tabsTool.closeCurrentTab();
                    this.$bus.$emit('reloadMenu');
                }
            });
        },
    }
}
</script>
  
<style lang="less" scoped>
.el-checkbox-group {
    margin: 20px;
}
</style>