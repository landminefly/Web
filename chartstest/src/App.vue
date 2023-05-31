<script>
import * as echarts from 'echarts';
export default {
  mounted() {
    console.log("111");
    var myChart = echarts.init(document.getElementById('main'));
    // 绘制图表
    myChart.setOption({
      tooltip: {},
      xAxis: {
        data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
      },
      yAxis: {
        type: 'value',
      },
      legend: {
        textStyle:{
          color:'#fff'
        }
      },
      series: [
        {
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20],
          itemStyle: {
            color: '#6cf',
            barBorderRadius: 8,
          },
          label: {
            show: true,
            position: 'inside',
            formatter: '{a}'
          }
        },
        {
          name: '原神',
          type: 'bar',
          data: [12,32,18,22,16,26],
          itemStyle: {
            color: '#d8c',
            barBorderRadius: 8,
          },
          label: {
            show: true,
            position: 'inside',
            formatter: '{a}'
          },
        }
      ]
    });
    window.addEventListener('resize', function () {
      myChart.resize();
    });
  }
}
</script>

<template>
  <div id="main" style="height: 500px; width: 70%;"></div>
</template>

<style>
body {

  
  background-color: rgb(26, 26, 26);
  color: white;
}
</style>
