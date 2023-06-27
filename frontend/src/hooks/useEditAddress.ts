import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { editAddressApi } from '@/api/Address';
import { useSnackbarStore } from '@/stores/snackbar';
import { useContactStore } from '@/stores/contact';
import { useAddressStore } from '@/stores/address';

export function useEditAddress() {
  const { contact } = storeToRefs(useContactStore());
  const { address } = storeToRefs(useAddressStore());
  const { getAddressDataAPI } = useAddressStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const editAddress = async () => {
    const addressData = getAddressDataAPI();

    isLoading.value = true;

    try {
      await editAddressApi({
        id: +address.value.id,
        data: {
          ...addressData,
          contactInfoId: +contact.value.id
        }
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
    editAddress,
    isLoading,
    isSuccess
  };
}
