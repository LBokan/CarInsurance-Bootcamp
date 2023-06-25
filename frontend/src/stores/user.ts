import { reactive } from 'vue';
import { defineStore } from 'pinia';
import type { IUserState } from '@/helpers/interfaces';

type TypeUserStateKeys = keyof IUserState;

export const useUserStore = defineStore('user', () => {
  const userState: IUserState = reactive({
    id: null,
    firstName: '',
    lastName: '',
    email: '',
    role: ''
  });

  function setUserData<K extends TypeUserStateKeys>( data: IUserState ) {
    Object.keys(data).forEach( ( key ) => {
      const typedKey = key as K;

      userState[typedKey] = data[typedKey];
    });
  }

  return { userState, setUserData }
})
