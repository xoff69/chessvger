import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import { createVuetify } from 'vuetify';
import 'vuetify/styles';
import { aliases, mdi } from 'vuetify/iconsets/mdi';
import { createPinia } from 'pinia';

// Configuration de Vuetify
const vuetify = createVuetify({
  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
      mdi,
    },
  },
});

const app = createApp(App);
app.use(router);
app.use(createPinia());
app.use(vuetify);
app.mount('#app');
