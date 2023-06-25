import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { deletePhoneNumberApi } from '@/api/PhoneNumber';
import { useSnackbarStore } from '@/stores/snackbar';
import { usePhoneNumberStore } from '@/stores/phoneNumber';

export function useDeletePhoneNumber() {
  const { phoneNumber } = storeToRefs(usePhoneNumberStore());
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const deletePhoneNumber = async () => {
    isLoading.value = true;

    try {
      await deletePhoneNumberApi(+phoneNumber.value.id);

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
    deletePhoneNumber,
    isLoading,
    isSuccess
  };
}
