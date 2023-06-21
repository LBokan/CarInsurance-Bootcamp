import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from '@/App.vue';
import router from '@/router/router';
import vuetify from '@/plugins/vuetify';

import '@vuepic/vue-datepicker/dist/main.css';
import '@/main.css';

const pinia = createPinia();
const app = createApp(App);

app
  .use(vuetify)
  .use(pinia)
  .use(router)
  .mount('#app');
