<template>
  <div style="text-align: center; justify-content: center; display: flex; align-items: center; height: 100vh">
    <el-card style="display: inline-block; width: 400px">
      论坛登录
      <el-input style="margin-top: 10px" placeholder="用户名" v-model="username"></el-input>
      <el-input style="margin-top: 10px" placeholder="密码" v-model="password"></el-input>
      <div style="margin-top: 20px">
        <el-button @click="handleLogin">登录</el-button>
        <el-button @click="handleRegister" type="primary">注册</el-button>
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
      password: ''
    }
  },
  methods: {
    handleLogin() {
      let that = this
      axios.post("http://localhost:10000/api/account/login", qs.stringify({username: this.username, password: this.password}, {withCredentials: true})).then(r => {
        if (r.data.status === 0) {
          that.$router.replace("/main/home")
        }
      })
    },
    handleRegister() {
      axios.post("http://localhost:10000/api/account/register", qs.stringify({username: this.username, password: this.password})).then(r => {
        alert(r)
      })
    }
  }
}
</script>