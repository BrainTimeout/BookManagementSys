<template>
  <div>
    <el-table :data="tableData" stripe>
      <el-table-column prop="'name" label="名称"></el-table-column>
      <el-table-column prop="'age" label="年龄"></el-table-column>
      <el-table-column prop="'address" label="住址"></el-table-column>
      <el-table-column prop="'phone" label="电话"></el-table-column>
    </el-table>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  data(){
    return{
      tableData:[
        { "name": "王二", "age": 20, "address": "通辽", "phone": "1313131313" },
        { "name": "李四", "age": 22, "address": "北京", "phone": "13987654321" },
        { "name": "张三", "age": 25, "address": "上海", "phone": "13823456789" },
        { "name": "赵六", "age": 19, "address": "广州", "phone": "13210020030" },
        { "name": "孙七", "age": 24, "address": "深圳", "phone": "13345678901" },
        { "name": "周八", "age": 28, "address": "武汉", "phone": "13456789012" }
      ]
    }
  }
}
</script>
