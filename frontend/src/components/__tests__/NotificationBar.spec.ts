import { beforeEach, describe, it, expect, vi } from 'vitest';

import { mount } from '@vue/test-utils';

import { setActivePinia, createPinia } from 'pinia';
import { createApp } from 'vue';
import { createVuetify } from 'vuetify';
import * as components from 'vuetify/components';
import * as directives from 'vuetify/directives';
import 'vuetify/dist/vuetify.css';

import '@/mail.css';

import { useSnackbarStore } from '../../stores/snackbar';
import NotificationBar from '../NotificationBar.vue';


describe('NotificationBar', () => {
  // const app = createApp({});
  // let vuetify;

  
  
  // beforeEach(() => {
    //   // const pinia = createPinia();
    //   vuetify = createVuetify({
  //     components,
  //     directives
  //   });
  //   // app
  //   //   .use(vuetify)
  //   //   .use(pinia);
  //   setActivePinia(createPinia())
  // })
  
  
  it('renders properly', () => {;
    // const mockMyState = vi.fn();

    const spyOnSnackbarStore = vi.spyOn('@/stores/snackbar', 'useSnackbarStore');
    mockMyState('Test notification bar', 'success');

    // const { setSnackbarDataAndShow } = useSnackbarStore();
    // setSnackbarDataAndShow('Test notification bar', 'success');

    const wrapper = mount(NotificationBar);

    expect(wrapper.text()).toContain('Test notification bar');
  });


  // it('renders properly', () => {
  //   const wrapper = mount(NotificationBar, { props: { msg: 'Hello Vitest' } })
  //   expect(wrapper.text()).toContain('Hello Vitest')
  // })
})
