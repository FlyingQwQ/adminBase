<!-- 账号编辑 -->
<template>
    <div>
        <ResourceButton :config="resourceButtonConfig"></ResourceButton>
    </div>
</template>

<script>

export default {
    data() {
        return {
            resourceButtonConfig: {
                save: { 
                    disabled: true,
                    click: this.save 
                }
            }
        }
    }
}
</script>
  
<style lang="less" scoped>
  
</style>