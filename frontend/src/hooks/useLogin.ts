import { ref, reactive } from 'vue';
import { authenticateUser } from '@/api/Authorization';
import { useSnackbarStore } from '@/stores/snackbar';
import { useUserStore } from '@/stores/user';
import { type ICredentials, type IUserState } from '@/utils/interfaces';

interface IResponse {
  user: IUserState,
  token: string
};

export function useLogin() {
  const { setUserData } = useUserStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const credentials: ICredentials = reactive({
    email: '',
    password: ''
  });

  const isLoading = ref<boolean>(false);
  const isError = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const loginFetch = async () => {
    isLoading.value = true;

    try {
      const response: IResponse = await authenticateUser({
        email: credentials.email,
        password: credentials.password,
      });

      isError.value = false;
      errorData.value = '';
      isSuccess.value = true;

      setUserData(response.user);
    } catch (error) {
      let errorMessage = 'Unknown Error';

      if (error instanceof Error) {
        errorMessage = error.message;
      }

      isError.value = true;
      errorData.value = errorMessage;
      isSuccess.value = false;

      setSnackbarDataAndShow(errorData.value, 'error');
    } finally {
      isLoading.value = false;
    }
  };

  return {
    credentials,
    loginFetch,
    isLoading,
    isError,
    errorData,
    isSuccess
  };
}
