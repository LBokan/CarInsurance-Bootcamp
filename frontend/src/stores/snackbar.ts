import { reactive } from 'vue';
import { defineStore } from 'pinia';

interface ISnackbarState {
  isShown: boolean,
  message: string,
  type: string,
  timeout: number
};

export const useSnackbarStore = defineStore('snackbar', () => {
  const snackbarState: ISnackbarState = reactive({
    isShown: false,
    message: '',
    type: 'info',
    timeout: 5000
  });
  
  function showSnackbar() {
    snackbarState.isShown = true;
  }

  function setSnackbarDataAndShow( 
    message: string, 
    type: string = 'info', 
    timeout: number = 5000
  ) {
    snackbarState.message = message;
    snackbarState.type = type;
    snackbarState.timeout = timeout;

    showSnackbar();
  }

  function hideAndResetSnackbar() {
    snackbarState.isShown = false;
    snackbarState.message = '';
    snackbarState.type = 'info';
    snackbarState.timeout = 5000;
  }

  return { snackbarState, setSnackbarDataAndShow, hideAndResetSnackbar }
})
