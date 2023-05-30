import { reactive } from 'vue';
import { defineStore } from 'pinia';
import { type IUserState } from '@/utils/interfaces';

export const useUserStore = defineStore('user', () => {
  const userState: IUserState = reactive({
    email: '',
    firstName: '',
    lastName: '',
    role: {},
    userId: null
  });

  function setUserData( data: IUserState ) {
    userState.email = data.email;
    userState.firstName = data.firstName;
    userState.lastName = data.lastName;
    userState.role = data.role;
    userState.userId = data.userId;
  }

  return { userState, setUserData }
})
