<template>
  <div>
    <p>文件上次测试</p>
    <FileUpload></FileUpload>
    <hr>
    <p>表单测试</p>
    <ParamForm :params="params"></ParamForm>
    <hr>
    <p>图表测试</p>
    <div class="box-pie" style="height: 400px" ref="chart"></div>
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
      ],
      option: {
        title: {
          text: 'Referer of a Website',
          subtext: 'Fake Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: 'Search Engine' },
              { value: 735, name: 'Direct' },
              { value: 580, name: 'Email' },
              { value: 484, name: 'Union Ads' },
              { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      chart: null,
    }
  },
  mounted() {
    this.chart = this.$echarts.init(this.$refs.chart);
    this.chart.setOption(this.option);
  },
  methods: {

  }
}
</script>

<style lang="less" scoped></style>