import { ref } from 'vue';
import { getUserApi } from '@/api/User';
import { useSnackbarStore } from '@/stores/snackbar';
import { useUserStore } from '@/stores/user';
import { type IUserState } from '@/utils/interfaces';

export function useUser() {
  const { setUserData } = useUserStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const getUser = async () => {
    isLoading.value = true;

    try {
      const response: IUserState = await getUserApi();

      errorData.value = '';
      isSuccess.value = true;

      setUserData(response);
    } catch (error) {
      let errorMessage = 'Unknown Error';

      if (error instanceof Error) {
        errorMessage = error.message;
      }

      errorData.value = errorMessage;
      isSuccess.value = false;

      setSnackbarDataAndShow(errorData.value, 'error');
    } finally {
      isLoading.value = false;
    }
  };

  return {
    getUser,
    isLoading,
    isSuccess
  };
}
