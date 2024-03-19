<script setup>

import {Calendar, Comment, User} from "@element-plus/icons-vue";
</script>

<template>
  <el-dialog title="发布新贴子" v-model="show.newThread">
    <form ref="form" enctype="multipart/form-data" method="post" action="http://localhost:10000/api/forum/new-thread">
      标题
      <el-input v-model="title"/>
      内容
      <br/>
      <textarea name="text"/>
      <br/>
      图片
      <el-button style="margin: 10px" @click="addPics">添加</el-button>
      <input hidden="hidden" name="title" :value="title"/>
      <input style="margin: 10px" v-for="file in files" :name="file" type="file"/>
    </form>
    <el-button @click="() => {
        this.$refs.form.submit();
      }">提交
    </el-button>
  </el-dialog>
  <div style="text-align: center">
    <el-carousel style="margin-bottom: 40px">
      <el-carousel-item v-for="c in carousel">
        <el-image style="width: 100%; height: 100%" @click="openLink(c.link)" :src="'http://localhost:10000/api/forum/pic?picId=' + c.pic"/>
      </el-carousel-item>
    </el-carousel>
  </div>

  <div style="display: flex">
    <el-button style="margin-left: 20px" type="primary" icon="Plus" @click="() => {show.newThread = true}">发帖</el-button>
    <el-radio-group style="margin-left: 60px" v-model="sort">
      <el-radio-button label="post">按发帖</el-radio-button>
      <el-radio-button label="reply">按回复</el-radio-button>
    </el-radio-group>
  </div>
  <br/>
  <div style="margin: 10px">
    <el-card @click="() => {$router.push({name: 'thread', params: {id: t.id}})}" shadow="hover" style="margin: 10px" v-for="t in threads">
      <span style="font-size: 18px; font-weight: 500; color: rgb(51, 51, 51)">
        {{ t.title }}
      </span>
      <br/>
      <span style="display: -webkit-box; -webkit-box-orient: vertical; -webkit-line-clamp: 2; overflow: hidden; text-overflow: ellipsis; white-space: pre-wrap; font-size: 15px; font-weight: 400; color: #666">
        {{t.text}}
      </span>
      <br/>
      <el-space>
        <el-image v-if="t.pics[0] !== ''" v-for="pic in t.pics" style="width: 230px" :src="'http://localhost:10000/api/forum/pic?picId=' +pic"/>
      </el-space>
      <br/>
      <el-icon color="#666" :size="15" style="margin: 10px">
        <User/>
      </el-icon>
      <span style="color: #666">
        <a :href="'/main/user/' + t.userId">{{ t.username }}</a>
      </span>
      <el-icon color="#666" :size="15" style="margin: 10px">
        <Calendar/>
      </el-icon>
      <span style="color: #666">{{ t.postDate.replace('T', ' ') }}</span>
      <el-icon color="#666" :size="15" style="margin: 10px">
        <Comment/>
      </el-icon>
      <span style="color: #666">{{ t.replyNum }}</span>
      <div style="text-align: left; width: 100%">
        <div style="width: 100%">
        </div>
      </div>
    </el-card>
  </div>
  <div style="width: 100%; text-align: center">
    <el-pagination style="justify-content: center" background layout="prev, pager, next" :page-count="totalPage"
                   @current-change="loadPage"/>
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
    openLink(link) {
      window.open(link)
    },
    getCarousel() {
      let that = this
      axios.get("http://localhost:10000/api/forum/carousel").then(r => {
        if (r.data.status === 0) {
          that.carousel = r.data.info
        }
      })
    },
    loadPage(val) {
      let that = this
      if (val === undefined) val = 1
      axios.get("http://localhost:10000/api/forum/threads?page=" + val).then(r => {
        if (r.data.status === 0) {
          r.data.info.forEach(t => {
            t.pics = t.pics.split(',')
            if (t.pics[0] === '') t.pics = []
          })
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
      totalPage: 2,
      sort: 'post',
      carousel: []
    }
  },
  mounted() {
    this.loadPage();
    this.getCarousel();
  }
}
</script>