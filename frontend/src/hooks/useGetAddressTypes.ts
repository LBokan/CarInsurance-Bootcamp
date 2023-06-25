import { reactive, ref } from 'vue';

import { getAddressTypesApi } from '@/api/Address';
import { useSnackbarStore } from '@/stores/snackbar';

export function useGetAddressTypes() {
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const data = reactive<string[]>([]);
  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const getAddressTypes = async () => {
    isLoading.value = true;

    try {
      const response: string[] = await getAddressTypesApi();

      errorData.value = '';
      isSuccess.value = true;

      response.forEach(addressType => {
        data.push(addressType);
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
    getAddressTypes,
    data,
    isLoading,
    isSuccess
  };
}
