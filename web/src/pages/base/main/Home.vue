<template>
  <div>
    <p>富文本</p>
    <quill-editor  v-model="content"
      :options="editorOption">
    </quill-editor>
    <p>{{ content }}</p>

    <p>文件上传测试</p>
    <FileUpload tips="上传文件大小不能超过 10MB!" :limit="1" :public="false"></FileUpload>
    <!-- <SmallFileUpload></SmallFileUpload> -->
    <hr>
    <p>表单测试</p>
    <ParamForm :params="params"></ParamForm>
    <hr>
    <p>图表测试</p>
    <div class="box-pie" style="height: 400px" ref="chart"></div>
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'

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

      content: '',
      editorOption: {
        modules: {
          toolbar: [
            ["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线
            ["blockquote", "code-block"], // 引用  代码块
            [{ header: 1 }, { header: 2 }], // 1、2 级标题
            [{ list: "ordered" }, { list: "bullet" }], // 有序、无序列表
            [{ script: "sub" }, { script: "super" }], // 上标/下标
            [{ indent: "-1" }, { indent: "+1" }], // 缩进
            // [{'direction': 'rtl'}],                         // 文本方向
            [{ size: ["small", false, "large", "huge"] }], // 字体大小
            [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题
            [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色
            [{ font: [] }], // 字体种类
            [{ align: [] }], // 对齐方式
            ["clean"], // 清除文本格式
            ["link", "image", "video"] // 链接、图片、视频
          ], //工具菜单栏配置
        },
      }
    }
  },
  mounted() {
    this.chart = this.$echarts.init(this.$refs.chart);
    this.chart.setOption(this.option);
  },
  methods: {

  },
  comments: {
    quillEditor
  }
}
</script>

<style lang="less" scoped></style>