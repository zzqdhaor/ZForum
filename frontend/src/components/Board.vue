<script setup>

</script>

<template>
  <el-card v-for="i in things">
    <p style="text-align: center; font-size: 20px">{{i.title}}</p>
    <p>{{i.text}}</p>
  </el-card>
</template>

<script>
import axios from "axios";

export default {
  name: 'board',
  data() {
    return {
      things: [{title: '顶栏', text: '导航菜单默认为垂直模式，通过将 mode 属性设置为 horizontal 来使导航菜单变更为水平模式。 另外，在菜单中通过 sub-menu 组件可以生成二级菜单。 Menu 还提供了background-color、text-color和active-text-color，分别用于设置菜单的背景色、菜单的文字颜色和当前激活菜单的文字颜色。'}]
    }
  },
  mounted() {
    let that = this
    axios.get("http://localhost:10000/api/forum/info").then(r => {
      if (r.data.status === 0) {
        that.things = r.data.info
      }
    })
  }
}
</script>