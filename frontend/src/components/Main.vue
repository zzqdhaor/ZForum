<script setup>

import {Avatar, Back, Bell, ChatRound, InfoFilled, Search, Setting, UserFilled} from "@element-plus/icons-vue";
import user from "./User.vue";
import Thread from "@/components/Thread.vue";
</script>

<template>
  <div>
    <el-container>
      <el-header>
        <el-space style="margin-top: 10px; width: 90vw" alignment="center" direction="horizontal">
          <div style="font-size: 30px">ZForum</div>
          <el-input style="margin-left: 20px" placeholder="搜索任何内容"/>
          <el-icon size="20px" color="#666">
            <Search/>
          </el-icon>
          <el-space style="position: absolute; right: 40px; top: 10px">
            <el-avatar src="http://localhost:10000/api/user-info/get-icon"/>
            <el-dropdown>
              {{username}}
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="() => $router.replace('/main/user/0')">
                    <el-icon size="15">
                      <UserFilled/>
                    </el-icon>
                    个人中心
                  </el-dropdown-item>
                  <el-dropdown-item @click="handleLogout">
                    <el-icon size="15">
                      <Back/>
                    </el-icon>
                    <span>退出登录</span>
                  </el-dropdown-item>
                  <el-dropdown-item>
                    <el-icon size="15">
                      <Setting/>
                    </el-icon>
                    <span>设置</span>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </el-space>
        </el-space>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu router style="height: 100%">
            <el-menu-item index="/main/home">
              <el-icon>
                <ChatRound/>
              </el-icon>
              帖子
            </el-menu-item>
            <el-menu-item index="/main/board">
              <el-icon>
                <Bell/>
              </el-icon>
              系统公告
            </el-menu-item>
            <el-menu-item index="/main/user/0">
              <el-icon>
                <UserFilled/>
              </el-icon>
              我的
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'main',
  data() {
    return {
      username: ''
    }
  },
  methods: {
    handleLogout() {
      let that = this
      axios.get("http://localhost:10000/api/account/logout").then(r => {
        if (r.data.status === 0) {
          that.$router.replace('/login')
        }
      })
    }
  },
  mounted() {
    let that = this
    axios.get("http://localhost:10000/api/account/get-username").then(r => {
      that.username = r.data
    })
  }
}
</script>