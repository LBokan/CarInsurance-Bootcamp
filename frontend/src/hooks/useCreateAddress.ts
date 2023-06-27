import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { createAddressApi } from '@/api/Address';
import { useSnackbarStore } from '@/stores/snackbar';
import { useContactStore } from '@/stores/contact';
import { useAddressStore } from '@/stores/address';

export function useCreateAddress() {
  const { contact } = storeToRefs(useContactStore());
  const { getAddressDataAPI } = useAddressStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const createAddress = async () => {
    const addressData = getAddressDataAPI();

    isLoading.value = true;

    try {
      await createAddressApi({
        ...addressData,
        contactInfoId: +contact.value.id
      });

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
    createAddress,
    isLoading,
    isSuccess
  };
}
