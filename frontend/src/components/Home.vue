<script setup>

import {Calendar, Comment, User} from "@element-plus/icons-vue";
</script>

<template>
  <el-dialog title="发布新贴子" v-model="show.newThread">
    标题
    <el-input  v-model="title"/>
    内容
    <el-input type="textarea" v-model="text"/>
    图片
    <el-button style="margin: 10px" @click="addPics">添加</el-button>
    <form ref="form" enctype="multipart/form-data" method="post" action="http://localhost:10000/api/forum/new-thread">
      <input hidden="hidden" name="title" :value="title"/>
      <input hidden="hidden" name="text" :value="text"/>
      <input style="margin: 10px" v-for="file in files" :name="file" type="file"/>
    </form>
    <el-button @click="() => {
        this.$refs.form.submit();
      }">提交
    </el-button>
  </el-dialog>
  <el-button type="primary" icon="Plus" @click="() => {show.newThread = true}">发帖</el-button>
  <br/>
  <div style="margin: 10px">
    <div v-for="t in threads">
      <el-row>
        <el-col :span="8">
          <div style="display:flex;justify-content: center; align-items:center; height: 100%">
            <router-link style="text-decoration: none; margin: 3px" :to="{name: 'thread', params: {id: t.id}}">{{ t.title }}</router-link>
          </div>
        </el-col>
        <el-col :span="5">
          <el-icon color="#666" :size="15" style="margin: 10px">
            <User/>
          </el-icon>
          <span style="color: #666">{{ t.username }}</span>
        </el-col>
        <el-col :span="8">
          <el-icon color="#666" :size="15" style="margin: 10px">
            <Calendar/>
          </el-icon>
          <span style="color: #666">{{ t.postDate.replace('T', ' ') }}</span>
        </el-col>
        <el-col :span="3">
          <el-icon color="#666" :size="15" style="margin: 10px">
            <Comment/>
          </el-icon>
          <span style="color: #666">{{ t.replyNum }}</span>
        </el-col>
      </el-row>
      <div style="text-align: left; width: 100%">
        <div style="width: 100%" direction="horizontal">
        </div>
      </div>
    </div>
  </div>
  <div style="width: 100%; text-align: center">
    <el-pagination style="justify-content: center" background layout="prev, pager, next" :page-count="totalPage"  @current-change="loadPage"/>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'home',
  methods: {
    addPics() {
      this.files.push("file" + this.num);
      this.num++;
    },
    loadPage(val) {
      let that = this
      if (val === undefined) val = 1
      axios.get("http://localhost:10000/api/forum/threads?page=" + val).then(r => {
        if (r.data.status === 0) {
          that.threads = r.data.info
        }
        console.log(r.data)
      })
    }
  },
  data() {
    return {
      show: {
        newThread: false,
      },
      title: '',
      text: '',
      files: [],
      num: 0,
      threads: [],
      page: 1,
      totalPage: 2
    }
  },
  mounted() {
    this.loadPage();
  }
}
</script>