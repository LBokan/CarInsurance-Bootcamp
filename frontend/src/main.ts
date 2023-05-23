import { createApp } from 'vue';

import App from '@/App.vue';
import router from '@/router/router';
import vuetify from '@/plugins/vuetify';

const app = createApp(App);

app
  .use(vuetify)
  .use(router)
  .mount('#app');
