<script setup>

</script>

<template>
  <el-dialog style="text-align: center; width: 400px" :title="'对用户' + operatingUser.username + '的管理'" v-model="userDialog">
    <el-avatar size="large" :src="operatingUser.src"/>
    <p>{{operatingUser.username}}</p>
    <p>用户ID: {{operatingUser.id}}</p>
    <el-card shadow="never">
      操作：
      <el-button type="warning">重置密码</el-button>
      <el-button type="danger">删除</el-button>
    </el-card>
  </el-dialog>
  <el-input style="margin: 10px; width: 200px" placeholder="用户名搜索" v-model="username"/>
  <el-button @click="getUserInfoByUsername(this.username)">搜索</el-button>
  <el-input type="number" style="margin: 10px; width: 200px" placeholder="ID搜索" v-model="id"/>
  <el-button @click="getUserInfoById(this.id)">搜索</el-button>
  <br/>
<el-card @click="changeOperatingUser(u)" style="width: 100px; display: inline-block; margin: 10px; text-align: center" v-for="u in user">
  <el-avatar :src="u.src"/>
  {{u.username}}

</el-card>
</template>

<script>
import axios from "axios";

export default {
  name: 'admin-user',
  data() {
    return {
      user: [],
      username: '',
      id: '',
      userDialog: false,
      operatingUser: {
        username: '',
        id: '',
        src: ''
      }
    }
  },
  mounted() {
    this.getUserInfo()
  },
  methods: {
    changeOperatingUser(u) {
      this.userDialog = true
      this.operatingUser = {
        username: u.username,
        src: u.src,
        id: u.id
      }
    },
    getUserInfo() {
      let that = this
      axios.get(this.$address + '/api/account/admin/user-info').then(r => {
        if (r.data.status === 0) {
          that.user = r.data.info
          that.user.forEach(u => {
            u.src = that.$address + '/api/user-info/get-icon?userId=' + u.id
          })
        }
      })
    },
    getUserInfoById(id) {
      let that = this
      axios.get(this.$address + '/api/account/admin/user-info?id=' + id).then(r => {
        if (r.data.status === 0) {
          that.user = r.data.info
          that.user.forEach(u => {
            u.src = that.$address + '/api/user-info/get-icon?userId=' + u.id
          })
        }

      })
    },
    getUserInfoByUsername(username) {
      let that = this
      axios.get(this.$address + '/api/account/admin/user-info?username=' + username).then(r => {
        if (r.data.status === 0) {
          that.user = r.data.info
          that.user.forEach(u => {
            u.src = that.$address + '/api/user-info/get-icon?userId=' + u.id
          })
        }

      })
    }
  }
}
</script>