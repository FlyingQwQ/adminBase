export default {
    props: {
        render: {
            type: Function,
            required: true
        },
        row: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            
        }
    },
    render(h) {
        return this.render(h, this.row) // 调用传入的渲染函数
    }
}