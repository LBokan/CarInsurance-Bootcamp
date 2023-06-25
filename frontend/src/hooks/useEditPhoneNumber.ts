import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { editPhoneNumberApi } from '@/api/PhoneNumber';
import { useSnackbarStore } from '@/stores/snackbar';
import { useContactStore } from '@/stores/contact';
import { usePhoneNumberStore } from '@/stores/phoneNumber';

export function useEditPhoneNumber() {
  const { contact } = storeToRefs(useContactStore());
  const { phoneNumber } = storeToRefs(usePhoneNumberStore());
  const { getPhoneNumberDataAPI } = usePhoneNumberStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const editPhoneNumber = async () => {
    const phoneNumberData = getPhoneNumberDataAPI();

    isLoading.value = true;

    try {
      await editPhoneNumberApi({
        id: +phoneNumber.value.id,
        data: {
          ...phoneNumberData,
          contactInfoId: +contact.value.id,
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
    editPhoneNumber,
    isLoading,
    isSuccess
  };
}
