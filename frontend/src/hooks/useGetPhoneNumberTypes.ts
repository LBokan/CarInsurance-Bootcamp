import { reactive, ref } from 'vue';

import { getPhoneNumberTypesApi } from '@/api/PhoneNumber';
import { useSnackbarStore } from '@/stores/snackbar';

export function useGetPhoneNumberTypes() {
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const data = reactive<string[]>([]);
  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const getPhoneNumberTypes = async () => {
    isLoading.value = true;

    try {
      const response: string[] = await getPhoneNumberTypesApi();

      errorData.value = '';
      isSuccess.value = true;

      response.forEach(phoneNumberType => {
        data.push(phoneNumberType);
      });
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
    getPhoneNumberTypes,
    data,
    isLoading,
    isSuccess
  };
}
