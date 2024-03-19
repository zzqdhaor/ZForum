<script setup>

</script>

<template>
  <span>
    ZForum后台管理
  </span>

  <el-menu mode="horizontal" router>
    <el-menu-item index="/admin/user">用户管理</el-menu-item>
    <el-menu-item index="/admin/thread">帖子管理</el-menu-item>
    <el-menu-item index="/admin/info">信息管理</el-menu-item>
  </el-menu>
  <router-view/>
</template>

<script>
export default {
  data() {
    return {
      name: 'admin'
    }
  },

}
</script>