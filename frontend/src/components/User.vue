<script setup>

import {Calendar, Comment} from "@element-plus/icons-vue";
</script>

<template>
  <el-dialog v-model="show.uploadIcon">
    <el-upload with-credentials action="http://localhost:10000/api/user-info/icon" :on-success="()=>{window.location.reload()}">
      <el-button>点击上传</el-button>
    </el-upload>
  </el-dialog>
  <el-card style="margin: 20px; height: 200px" shadow="never">
    <el-space direction="horizontal">
      <el-avatar v-if="userId !== ''" :src="'http://localhost:10000/api/user-info/get-icon?userId=' + userId" @click="() => { if($route.params.userId === '0') show.uploadIcon = true }" :size="80"/>
      <p style="font-weight: 400; font-size: 30px">{{ info.username }}</p>
      <el-button v-show="!me" @click="changeWatchingState">{{ isWatching ? '取关' : '关注' }}</el-button>
    </el-space>
    <br/>
    <el-space style="margin: 20px" direction="horizontal">
      <p>{{ info.fans }}粉丝</p>
      <p>{{ info.watching }}关注</p>
      <p>{{ info.threadCount }}发帖</p>
    </el-space>
  </el-card>
  <el-tabs style="margin: 20px" v-model="tab">
    <el-tab-pane label="发帖" name="threads">
      <el-card @click="goTo(t.id)" style="margin: 10px" v-for="t in threads">
        <el-space direction="horizontal">
          {{t.title}}
          <el-icon>
            <Calendar/>
          </el-icon>
          {{t.postDate}}
          <el-icon>
            <Comment/>
          </el-icon>
          {{t.replyNum}}
        </el-space>
      </el-card>
    </el-tab-pane>
    <el-tab-pane label="粉丝" name="fans">
      <div @click="goToUser(f.id)" style="margin: 5px" v-for="f in fans">
        <el-space>
          <el-avatar v-if="f.id !== undefined" :src="'http://localhost:10000/api/user-info/get-icon?userId=' + f.id"/>
          {{f.username}}
        </el-space>
      </div>
    </el-tab-pane>
    <el-tab-pane label="关注" name="watching">
      <div @click="goToUser(w.id)" style="margin: 5px" v-for="w in watching">
        <el-space>
          <el-avatar v-if="w.id !== undefined" :src="'http://localhost:10000/api/user-info/get-icon?userId=' + w.id"/>
          {{w.username}}
        </el-space>
      </div>
    </el-tab-pane>
  </el-tabs>
</template>
<script>
import axios from "axios";

export default {
  name: 'user',
  mounted() {
    this.load(this.$route.params.userId)
  },
  methods: {
    changeWatchingState() {
      axios.post("http://localhost:10000/api/user-info/watch?toWatchUser=" + this.userId).then(r => {
        if (r.data.status === 0) {
          window.location.reload();
        }
      })
    },
    load(userId) {
      let that = this
      this.userId = userId;
      if (userId === '0') this.me = true;
      axios.get("http://localhost:10000/api/user-info/get-basic-info?userId=" + userId).then(r => {
        if (r.data.status === 0)
          that.info = r.data.info
      })
      axios.get("http://localhost:10000/api/user-info/has-watch?otherUser=" + userId).then(r => {
        if (r.data.status === 0) {
          that.isWatching = r.data.info
        }
      })
      axios.get("http://localhost:10000/api/forum/get-threads-by-user-id?userId=" + userId).then(r => {
        if (r.data.status === 0) {
          that.threads = r.data.info
        }
      })
      axios.get("http://localhost:10000/api/user-info/get-fans?userId=" + userId).then(r => {
        if (r.data.status === 0) {
          that.fans = r.data.info
        }
      })
      axios.get("http://localhost:10000/api/user-info/get-watching?userId=" + userId).then(r => {
        if (r.data.status === 0) {
          that.watching = r.data.info
        }
      })
    },
    goTo(id) {
      this.$router.replace('/main/thread/' + id)
    },
    goToUser(id) {
      this.$router.replace('/main/user/' + id)
      this.userId = id
      this.load(id)
    }
  },
  data() {
    return {
      show: {
        uploadIcon: false
      },
      tab: 'threads',
      me: false,
      userId: '',
      threads: [{title: '', postDate: '', replyNum: ''}],
      isWatching: false,
      info: {fans: '', watching: '', threadCount: ''},
      fans: [{username: ''}],
      watching: [{username: ''}]
    }
  }
}
</script>