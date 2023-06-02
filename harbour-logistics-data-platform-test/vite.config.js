import { fileURLToPath, URL } from "node:url";

import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";

// https://vitejs.dev/config/
export default defineConfig({
	// base:"/HLDP/",
	plugins: [vue()],
	resolve: {
		alias: {
			"@": fileURLToPath(new URL("./src", import.meta.url)),
		},
	},
	server: {
		proxy: {
		  '/api': {
			target: 'http://127.0.0.1:8080',
			rewrite: (path) => path.replace(/^\/api/, ''),
			changeOrigin: true,
		  },
		}
	}
});
