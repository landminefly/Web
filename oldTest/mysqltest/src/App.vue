<script>
import axios from 'axios';
export default {
  data() {
    return {
      user: "root",
      pwd: "073412",
      dataList: null,
    }
  },
  methods: {
    send() {
      const that = this;
      axios({
        method: "POST",
        url: "com/test",
        data: {
          user: this.user,
          pwd: this.pwd,
        }
      }).then(value =>
        this.dataList = value.data.list
      ).catch(reason =>{
        var error = reason.response.data;
        var reg = /RuntimeException/;
        if(reg.test(error)){
          alert("账号或密码错误！");
        }
      }
      )
    }
  }
}


</script>

<template>
  <div id="user-wrapper">
    <span>用户名</span> &nbsp
    <input id="user-in" type="text" v-model="user">
  </div>
  <div id="pwd-wrapper">
    <span>密码</span> &nbsp&nbsp&nbsp&nbsp
    <input id="pwd-in" type="text" v-model="pwd">
  </div>
  <div id="submit-wrapper" @click="send">
    <a href="javascript:;">提交</a>
  </div>
  <div id="table-wrapper">
    <table id="table">
      <tr class="head">
        <td>name</td> 
        <td>age</td>
        <td>grade</td>
      </tr>
      <tr v-for="item in dataList" class="item">
        <td>{{ item.name }}</td>
        <td>{{ item.age }}</td>
        <td>{{ item.grade }}</td>
      </tr>
    </table>
  </div>
</template>

<style>
#user-wrapper,
#pwd-wrapper {
  margin: 10px;
}

#user-in,
#pwd-in {
  width: 100px;
}

#submit-wrapper {
  position: absolute;
  left: 80px;
  border-radius: 10px;
  text-align: center;
  background-color: rgba(255, 0, 212, 0.8);
}

a {
  display: block;
  height: 25px;
  width: 40px;
  line-height: 25px;
  text-decoration: none;
  color: white;
  font-size: 12px;
}

#table-wrapper{
  position: absolute;
  top:150px;
  left:50px;
  font-size: 15px;
}

#table .head td{
  border:2px white solid;
  width: 100px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  font-weight: 900;
}

#table .item td{
  border:1px white solid;
  width: 100px;
  height: 30px;
  line-height: 30px;
  text-align: center;
}

#table .item:nth-child(2n){
  background-color: rgb(0, 0, 130);
}

#table .item:nth-child(2n+1){
  background-color: rgb(100, 118, 0);
}
</style>
