<template>
  <div style="text-align: center; justify-content: center; display: flex; align-items: center; height: 100vh">
    <el-card style="display: inline-block; width: 400px">
      论坛登录
      <br/>
      <el-radio-group style="margin: 10px" v-model="method">
        <el-radio-button label="user">用户登录</el-radio-button>
        <el-radio-button label="admin">管理员登录</el-radio-button>
      </el-radio-group>
      <el-input style="margin-top: 10px" placeholder="用户名" v-model="username"></el-input>
      <el-input type="password" style="margin-top: 10px" placeholder="密码" v-model="password"></el-input>
      <div style="margin-top: 20px">
        <el-button @click="handleLogin">登录</el-button>
        <el-button v-if="method === 'user'" @click="handleRegister" type="primary">注册</el-button>
      </div>

    </el-card>
  </div>
</template>

<style scoped>

</style>
<script>
import axios from "axios";
import qs from "qs";

export default {
  name: 'login',
  data() {
    return {
      username: '',
      password: '',
      method: 'user'
    }
  },
  methods: {
    handleLogin() {
      let that = this
      let method = this.method
      let url = this.$address
      if (method === 'user') url += '/api/account/login'
      if (method === 'admin') url += '/api/account/admin/login'
      axios.post(url, qs.stringify({username: this.username, password: this.password}, {withCredentials: true})).then(r => {
        if (r.data.status === 0 && method === 'user') {
          that.$router.replace("/main/home")
        }
        if (r.data.status === 0 && method === 'admin') {
          that.$router.replace("/admin")
        }
        if (r.data.status !== 0) {
          this.$message({
            message: '登录失败',
            type: 'error'
          })
        }
      }).catch(() => {
        this.$message({
          message: '网络错误',
          type: 'error'
        })
      })
    },
    handleRegister() {
      axios.post("http://localhost:10000/api/account/register", qs.stringify({username: this.username, password: this.password})).then(r => {
        this.$message({
          message: '注册成功，请登录',
          type: "success"
        })
      })
    }
  }
}
</script>