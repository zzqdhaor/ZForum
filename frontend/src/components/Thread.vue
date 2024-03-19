<script setup>

import {Back} from "@element-plus/icons-vue";
</script>

<template>
  <el-dialog v-model="show.replyToThread">
    内容
    <el-input v-model="reply.text"/>
    图片
    <el-button style="margin: 10px" @click="addPics">添加</el-button>
    <form ref="form" enctype="multipart/form-data" method="post" action="http://localhost:10000/api/forum/reply-to-thread">
      <input hidden="hidden" name="threadId" :value="threadInfo.id">
      <input hidden="hidden" name="text" :value="reply.text"/>
      <input style="margin: 5px" v-for="file in reply.files" :name="file" type="file"/>
    </form>
    <el-button @click="() => {
        this.$refs.form.submit();
      }">提交</el-button>
  </el-dialog>
  <el-dialog v-model="show.replyToReply">
    内容
    <el-input v-model="replyToReply.text"/>
    图片
    <el-button style="margin: 10px" @click="addPicsForReply">添加</el-button>
    <form ref="form" enctype="multipart/form-data" method="post" action="http://localhost:10000/api/forum/reply-to-thread-reply">
      <input hidden name="replyId" :value="replyToReply.replyId">
      <input hidden name="text" :value="replyToReply.text"/>
      <input hidden name="threadId" :value="threadInfo.id"/>
      <input style="margin: 5px" v-for="file in replyToReply.files" :name="file" type="file"/>
    </form>
    <el-button @click="() => {
        this.$refs.form.submit();
      }">提交</el-button>
  </el-dialog>
  <el-icon @click="() => {$router.go(-1)}" color="#666" size="20">
    <Back/>
  </el-icon>
  <span style="font-size: 15px; margin: 10px; display: inline-block">{{threadInfo.title}}</span>
  <el-card style="margin: 10px" shadow="never">
    <el-space direction="horizontal">
      <div style="text-align: center">
        <el-avatar v-if="threadInfo.userId !== undefined" :size="80" :src="'http://localhost:10000/api/user-info/get-icon?userId=' + threadInfo.userId"/>
        <br/>
        <a style="text-decoration: none" :href="'/main/user/' + threadInfo.userId">{{threadInfo.username}}</a>
      </div>
      <div style="margin-left: 40px">
      <span style="display: -webkit-box; -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; white-space: pre-wrap; font-size: 15px; font-weight: 400">
        {{threadInfo.text}}

      </span>
         <br/>
        <el-image v-for="pic in threadInfo.pics" style="width: 100px" :src="'http://localhost:10000/api/forum/pic?picId=' +pic"/>
      </div>
    </el-space>
  </el-card>
  <el-card style="margin: 10px" v-for="reply in replies" shadow="never">
    <el-space direction="horizontal">
      <div style="text-align: center">
        <el-avatar v-if="reply.userId !== undefined" :size="80" :src="'http://localhost:10000/api/user-info/get-icon?userId=' + reply.userId"/>
        <br/>
        <a :href="'/main/user/' + reply.userId">{{reply.username}}</a>
      </div>
      <div style="margin-left: 40px">
        {{reply.text}} <br/>
        <el-image v-if="reply.pics[0] !== ''" v-for="pic in reply.pics" style="width: 100px" :src="'http://localhost:10000/api/forum/pic?picId=' +pic"/>
        <el-card v-if="reply.replyToReplies !== undefined && reply.replyToReplies.length > 0" shadow="never" style="margin-top: 30px; margin-bottom: 10px; width: 100%">
          <div v-for="rtr in reply.replyToReplies">
            <span>{{rtr.username}}: {{rtr.text}}
              <span v-for="iPic in rtr.pics">
                <el-link style="margin-right: 5px" :href="'http://localhost:10000/api/forum/pic?picId=' + iPic">查看图片</el-link>
              </span>
            </span>
            <br/>
          </div>
        </el-card>
      </div>
    </el-space>
    <br/>
    <el-button style="margin-left: 130px" @click="() => {show.replyToReply = true; replyToReply.replyId=reply.id}">回复</el-button>
  </el-card>
  <el-button style="margin: 10px" @click="() => show.replyToThread = true">回复</el-button>
</template>

<style scoped>

</style>
<script>
import axios from "axios";

export default {
  name: 'Thread',
  data() {
    return {
      replies: [],
      toReply: '',
      threadInfo: {
        title: '',
        text: '',
        username: '',
        pics: '',
        id: ''
      },
      reply: {
        text: '',
        pics: '',
        files: [],
        num: 0
      },
      replyToReply: {
        text: '',
        pics: '',
        files: [],
        num: 0,
        replyId: -1
      },
      show: {
        replyToThread: false,
        replyToReply: false
      }
    }
  },
  methods: {
    addPics() {
      this.reply.files.push("file" + this.reply.num);
      this.reply.num++;
    },
    addPicsForReply() {
      this.replyToReply.files.push("file" + this.replyToReply.num);
      this.replyToReply.num++;
    }
  },
  mounted() {
    let that = this
    axios.get("http://localhost:10000/api/forum/thread-info?id=" + this.$route.params.id).then(r => {
      if (r.data.status === 0) {
        r.data.info.pics = r.data.info.pics.split(',')
        if (r.data.info.pics[0] === '') r.data.info.pics = []
        that.threadInfo = r.data.info
      }
    })
    axios.get("http://localhost:10000/api/forum/get-reply-by-id?threadId=" + this.$route.params.id).then(r => {
      if (r.data.status === 0) {
        r.data.info.forEach(reply => {
          reply.pics = reply.pics.split(',')
        })
        that.replies = r.data.info
        that.replies.forEach(s => {
          axios.get("http://localhost:10000/api/forum/get-reply-to-reply?replyId=" + s.id).then(q => {
            if (q.data.status === 0) {
              q.data.info.forEach(rtr => {
                rtr.pics = rtr.pics.split(',')
                if (rtr.pics[0] === '') rtr.pics = []
              })
              s.replyToReplies = q.data.info
            }
          })
        })
      }
    })
  }
}
</script>