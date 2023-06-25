import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { createPhoneNumberApi } from '@/api/PhoneNumber';
import { useSnackbarStore } from '@/stores/snackbar';
import { useContactStore } from '@/stores/contact';
import { usePhoneNumberStore } from '@/stores/phoneNumber';

export function useCreatePhoneNumber() {
  const { contact } = storeToRefs(useContactStore());
  const { getPhoneNumberDataAPI } = usePhoneNumberStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const createPhoneNumber = async () => {
    const phoneNumberData = getPhoneNumberDataAPI();

    isLoading.value = true;

    try {
      await createPhoneNumberApi({
        ...phoneNumberData,
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
    createPhoneNumber,
    isLoading,
    isSuccess
  };
}
