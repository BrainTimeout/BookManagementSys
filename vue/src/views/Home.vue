<template>
  <div class="container">
    <!-- 日期范围选择器 -->
    <div class="date-selector">
      <label for="dateRange" class="label">选择日期范围: </label>
      <select id="dateRange" v-model="selectedRange" @change="filterData" class="select">
        <option value="week">最近一周</option>
        <option value="month">最近一个月</option>
        <option value="year">最近一年</option>
      </select>
    </div>

    <!-- 图表容器 -->
    <div id="main" class="chart-container"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import request from "@/utils/request";

export default {
  name: 'Home',
  data() {
    return {
      borrowData: [],  // 存储借书数据
      revertData: [],  // 存储还书数据
      dates: [],  // 存储日期
      allBorrowData: [],  // 存储所有借书数据
      allRevertData: [],  // 存储所有还书数据
      selectedRange: 'week',  // 默认选择最近一周
    };
  },
  mounted() {
    this.fetchData();  // 获取借书和还书数据
  },
  methods: {
    // 获取数据
    async fetchData() {
      try {
        const borrowResponse = await request.get("/Borrow/List");  // 获取借书数据
        const revertResponse = await request.get("/Borrow/RevertList");  // 获取还书数据

        this.allBorrowData = borrowResponse.data;  // 存储所有借书数据
        this.allRevertData = revertResponse.data;  // 存储所有还书数据

        // 处理数据
        this.filterData();
      } catch (error) {
        console.error("数据获取失败：", error);
      }
    },

    // 根据日期范围过滤数据
    filterData() {
      const filteredBorrowData = this.filterByDateRange(this.allBorrowData);
      const filteredRevertData = this.filterByDateRange(this.allRevertData);

      // 处理过滤后的数据
      this.borrowData = this.processData(filteredBorrowData, 'createtime');
      this.revertData = this.processData(filteredRevertData, 'revertDate');
      this.dates = this.extractDates(filteredBorrowData, filteredRevertData);

      this.initChart();  // 数据过滤后初始化图表
    },

    // 根据日期范围过滤数据
    filterByDateRange(data) {
      const today = new Date();
      const dateLimit = new Date();

      // 根据选择的日期范围进行筛选
      switch (this.selectedRange) {
        case 'week':
          dateLimit.setDate(today.getDate() - 7);  // 最近一周
          break;
        case 'month':
          dateLimit.setMonth(today.getMonth() - 1);  // 最近一个月
          break;
        case 'year':
          dateLimit.setFullYear(today.getFullYear() - 1);  // 最近一年
          break;
        default:
          dateLimit.setDate(today.getDate() - 7);  // 默认是最近一周
      }

      // 过滤掉在日期范围之外的数据
      return data.filter(item => {
        const itemDate = new Date(item.createtime.split(' ')[0]);  // 获取借书日期
        return itemDate >= dateLimit;
      });
    },

    // 处理数据：统计每个日期的借书或还书数量
    processData(data, dateField) {
      const dateCount = {};
      data.forEach(item => {
        const date = item[dateField].split(' ')[0];  // 提取日期部分
        dateCount[date] = (dateCount[date] || 0) + 1;  // 每个日期加1
      });
      return Object.values(dateCount);  // 返回每个日期的借书/还书数量
    },

    // 提取所有日期：从借书和还书数据中提取出所有日期
    extractDates(borrowData, revertData) {
      const dates = new Set();
      borrowData.forEach(item => dates.add(item.createtime.split(' ')[0]));
      revertData.forEach(item => dates.add(item.revertDate.split(' ')[0]));
      return Array.from(dates).sort();  // 返回排序后的日期数组
    },

    // 初始化图表
    initChart() {
      const chartDom = document.getElementById('main');
      const myChart = echarts.init(chartDom);

      // 设置图表的配置项
      const option = {
        title: {
          text: '图书借还统计',
          left: 'center',  // 将标题居中
          top: '5%',  // 设置标题距离顶部的间距
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold',
          }
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['借书', '还书'],
          top: '10%',  // 将图例位置调整到顶部，避免与标题重合
          right: '10%',  // 将图例放到右上角
          textStyle: {
            fontSize: 14
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: this.dates  // 使用提取的日期数据
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '借书',
            type: 'line',
            data: this.borrowData  // 使用借书数据
          },
          {
            name: '还书',
            type: 'line',
            data: this.revertData  // 使用还书数据
          }
        ]
      };

      myChart.setOption(option);
    }
  }
};
</script>

<style scoped>
/* 主要容器 */
.container {
  width: 100%;
  padding: 20px;
  box-sizing: border-box;
}

/* 日期范围选择器样式 */
.date-selector {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  justify-content: flex-start;
}

.label {
  margin-right: 10px;
  font-size: 16px;
  font-weight: bold;
}

.select {
  padding: 5px 10px;
  font-size: 16px;
  cursor: pointer;
  border: 1px solid #ccc;
  border-radius: 4px;
}

/* 图表容器 */
.chart-container {
  width: 100%;
  height: 600px;
  margin-top: 20px;
}
</style>
