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
//导入vuex
import { createStore } from "vuex";
//清除默认样式
import "./assets/reset.css";
//导入主样式
import "./assets/main.css";
//导入vuex-persist持久化插件
import VuexPersistence from 'vuex-persist'

//设置vuex-persist
const vuexLocal = new VuexPersistence({
    storage: window.sessionStorage,
})
//设置vuex
const store = createStore({
	state() {
		return {
			isLogged: 0,
			isDarkMode: false,
		};
	},
	mutations: {
		logout(state) {
			state.isLogged = 0;
		},
		loginAsCus(state) {
			state.isLogged = 1;
		},
		logAsAdmin(state) {
			state.isLogged = 2;
		},
		toggleDarkMode(state){
			state.isDarkMode = !state.isDarkMode;
		}
	}, 
	//注册vuex-persist
	plugins: [vuexLocal.plugin]
});

//设置路由、路由守卫
const routes = [
	{
		path: "/",
		component: redirect,
	},
	{
		path: "/login",
		component: login,
		beforeEnter: (to, from) => {
			switch (store.state.isLogged) {
				case 0:
					return true;
				case 1:
					return '/cus';
				case 2:
					return '/admin';
			}
		},
	},
	{
		path: "/cust",
		component: cust,
		beforeEnter: (to, from) => {
			switch (store.state.isLogged) {
				case 0:
					return '/login';
				case 1:
					return true;
				case 2:
					return '/admin';
			}
		},
	},
	{
		path: "/admin",
		component: admin,
		beforeEnter: (to, from) => {
			switch (store.state.isLogged) {
				case 0:
					return '/login';
				case 1:
					return '/cus';
				case 2:
					return true;
			}
		},
	},
];

const router = createRouter({
	history: createWebHistory(), //使用历史模式
	routes, // routes: routes 的缩写
});

//设置全局守卫
router.beforeEach((to, from) => {
	if (to.path !== "/" && to.path !== "/admin" && to.path !== "/cus" && to.path !== "/login") {
		alert("该页面不存在，正在为您重定向");
		return '/';
	}
});

//注册应用
const app = createApp(App);
//注册路由
app.use(router);
//注册vuex
app.use(store);
//挂载
app.mount("#app");
