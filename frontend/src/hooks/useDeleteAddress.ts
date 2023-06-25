import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { deleteAddressApi } from '@/api/Address';
import { useSnackbarStore } from '@/stores/snackbar';
import { useAddressStore } from '@/stores/address';

export function useDeleteAddress() {
  const { address } = storeToRefs(useAddressStore());
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const deleteAddress = async () => {
    isLoading.value = true;

    try {
      await deleteAddressApi(+address.value.id);

      errorData.value = '';
      isSuccess.value = true;
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
    deleteAddress,
    isLoading,
    isSuccess
  };
}
