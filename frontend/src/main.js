import {createApp} from "vue";
import Login from "@/components/Login.vue";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import {createRouter, createWebHistory} from "vue-router";
import App from "@/App.vue";
import Home from "@/components/Home.vue";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from "axios";
import My from "@/components/User.vue";
import Thread from "@/components/Thread.vue";
import Main from "@/components/Main.vue";
import User from "@/components/User.vue";
import Board from "@/components/Board.vue";
window.remoteAddr="http://localhost:10000"
const routes = [
    {
        path: '/',
        redirect: '/login',
        children: [
            {
                path: '/login',
                component: Login
            },
            {
                path: '/main',
                component: Main,
                children: [
                    {
                        path: '/main/home',
                        component: Home
                    },
                    {
                        path: '/main/user/:userId',
                        name: 'user',
                        component: User
                    },
                    {
                        path: '/main/thread/:id',
                        name: 'thread',
                        component: Thread
                    },
                    {
                        path: '/main/board',
                        name: 'board',
                        component: Board
                    }
                ]
            },

        ]
    },
]


// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单
const router = createRouter({
    // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHistory(),
    routes, // `routes: routes` 的缩写
})

// 5. 创建并挂载根实例
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
axios.defaults.withCredentials = true
//确保 _use_ 路由实例使
//整个应用支持路由。
app.use(router)
app.use(ElementPlus)
app.mount('#app')
