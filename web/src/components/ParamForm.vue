<template>
    <div class="simpleForm">
        <el-form ref="form" :rules="rules" label-position="right" :model="form" label-width="auto">
            <el-row :gutter="20">
                <el-col v-for="item in params" :key="item.label" :span="item.span ?? 6">
                    <el-form-item 
                        :prop="item.key"
                        :label="item.label" 
                        v-if="
                            item.type === 'text' ||
                            item.type === 'password' ||
                            item.type === 'number'
                        ">
                        <el-input 
                            v-model="form[item.key]"
                            :disabled="item.disabled"
                            :placeholder="item.placeholder"
                            :type="item.type">
                        </el-input>
                    </el-form-item>

                    <el-form-item 
                        :prop="item.key"
                        :label="item.label" 
                        v-else-if="
                            item.type === 'lookup'
                        ">
                        <DictionarySelect 
                            :value.sync="form[item.key]"
                            :code="item.code" 
                            :disabled="item.disabled"
                            :placeholder="item.placeholder"
                            >
                        </DictionarySelect>
                    </el-form-item>

                    <el-form-item 
                        :prop="item.key"
                        :label="item.label" 
                        v-else-if="
                            item.type === 'date'
                        ">
                        <el-date-picker 
                            type="date" 
                            v-model="form[item.key]" 
                            :disabled="item.disabled"
                            :placeholder="item.placeholder"
                            style="width: 100%;">
                        </el-date-picker>
                    </el-form-item>

                    <el-form-item 
                        :prop="item.key"
                        :label="item.label" 
                        v-else-if="
                            item.type === 'slot'
                        ">
                        <slot :name="item.key" :formValue="form"></slot>
                    </el-form-item>

                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>

export default {
    props: {
        params: {
            type: Array,
            default: () => [],
            required: true
        },
        rules: {
            type: Object,
            default: () => {}
        }
    },
    data() {
        return {
            form: {}
        }
    },
    methods: {
        getFormValue() {
            return this.form;
        },
        resetForm() {
            this.form = {};
            this.$refs.form.resetFields();
        },
        setFormValue(key, value){
            this.$set(this.form, key, value);
        },
        setFormValues(values){
            this.form = values;
        },
        validate() {
            return this.$refs.form.validate();
        }
    }
}
</script>
  
<style lang="less" scoped>
.simpleForm {
    margin: 20px 0 20px 0;
    padding: 0 10px;
    box-sizing: border-box;
}

.el-form-item {
    margin-bottom: 0px;
}

.el-col {
    margin: 10px 0 10px 0;
}
</style>