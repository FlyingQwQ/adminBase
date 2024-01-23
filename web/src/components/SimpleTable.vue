<template>
    <div class="simpleTable">
        <el-table
            :header-cell-style="{ background: '#eef1f6', color: '#606266' }"
            :data="tableData"
            style="width: 100%"
            @row-click="rowClick"
            highlight-current-row>

            <template v-for="item in columns">
                <el-table-column
                    v-if="!item.render"
                    :prop="item.key"
                    :label="item.label"
                    :fixed="item.fixed">
                </el-table-column>
                <el-table-column
                    v-else
                    :prop="item.key"
                    :label="item.label"
                    :fixed="item.fixed">
                    <template slot-scope="scope">
                        <TableColumnRender :row="scope.row" :render="item.render"></TableColumnRender>
                    </template>
                </el-table-column>
            </template>
        </el-table>

        <el-pagination
            @size-change="handleSizeChange"
            @current-change="paginationChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 40, 60, 100]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
    </div>
</template>

<script>
import { fetch } from '@/config';
import TableColumnRender from './TableColumnRender';

export default {
    props: {
        columns: {
            type: Array,
            rquired: true,
        },
        findApi: {
            type: String,
            rquired: true,
        },
        params: {
            type: Object,
            rquired: false,
            default: () => {}
        }
    },
    data() {
        return {
            tableData: [],
            pageSize: 10,
            total: 0,

            currentPage: 1,
        }
    },
    mounted() {
        
    },
    methods: {
        loadData() {
            fetch[this.findApi]({
                pageNum: this.currentPage,
                pageSize: this.pageSize,
                ...this.params
            }).then((res) => {
                if(res.code == 1) {
                    this.pageSize = res.data.pageSize;
                    this.total = res.data.total;
                    this.tableData = res.data.list;
                }
            });
        },

        paginationChange(currentPage) {
            this.currentPage = currentPage;
            this.loadData();
        },
        handleSizeChange(size) {
            this.pageSize = size;
            this.loadData();
        },

        rowClick(row, column, event) {
            this.$emit('row-click', row, column, event);
        },  
    },
    components: {
        TableColumnRender
    }
}
</script>
  
<style lang="less" scoped>
.el-pagination {
    margin-top: 3px;
    padding: 0;
    text-align: center;
}
.el-table {
    height: 100%;
}
</style>