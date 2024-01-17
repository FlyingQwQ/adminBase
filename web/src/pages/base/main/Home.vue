<template>
  <div>
    表单测试
    
    <ParamForm :params="params"></ParamForm>
  </div>
</template>

<script>
export default {
  data() {
    return {
      params: [
        {
          label: '姓名',
          key: 'name',
          type: 'text'
        },
        {
          label: '年龄',
          key: 'age',
          type: 'text'
        },
        {
          label: '性别',
          key: 'sex',
          type: 'lookup',
          code: 'XB'
        },
        {
          label: '选择时间',
          key: 'dates',
          type: 'date',
        },
      ]
    }
  },
  mounted() {
    
  },
  methods: {
    
  }
}
</script>

<style lang="less" scoped>
  
</style>