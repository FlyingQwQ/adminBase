<!-- 账号编辑 -->
<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
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
                }
            ],
        }
    },
    mounted() {
        this.init();
    },
    methods: {
        init() {
            fetch.getUserInfoById({
                userId: this.$route.query.userId
            }).then((res) => {
                if(res.code == 1) {
                    this.$refs.ParamForm.setFormValues(res.data);
                }
            });
        }
    }
}
</script>
  
<style lang="less" scoped>
  
</style>