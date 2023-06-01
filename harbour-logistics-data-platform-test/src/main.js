//导入vue-router
import { createRouter, createWebHistory } from "vue-router";
//导入vue
import { createApp } from "vue";
//导入根组件
import App from "./App.vue";
//导入路由组件
import redirect from "./components/redirect.vue";
import login from "./components/login.vue";
import admin from "./components/admin.vue";
import cust from "./components/cust.vue";
//清除默认样式
import "./assets/reset.css";

//设置路由
const routes = [
	{
		path: "/",
		component: redirect,
	},
	{
		path: "/login",
		component: login,
	},
	{
		path: "/cust",
		component: cust,
	},
	{
		path: "/admin",
		component: admin,
	},
];

const router = createRouter({
	history: createWebHistory(), //使用历史模式
	routes, // routes: routes 的缩写
});

//注册应用
const app = createApp(App);
//注册路由
app.use(router);
//挂载
app.mount("#app");
