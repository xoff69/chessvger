import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import 'vuetify/dist/vuetify.min.css'; // Import des styles de Vuetify
import router from "./router";
import { createPinia } from "pinia";
loadFonts()

createApp(App)
  .use(vuetify).use(createPinia()).use(router)
  .mount('#app')
