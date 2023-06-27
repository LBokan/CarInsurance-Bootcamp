import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { deleteContactApi } from '@/api/Contact';
import { useSnackbarStore } from '@/stores/snackbar';
import { useContactStore } from '@/stores/contact';

export function useDeleteContact() {
  const { contact } = storeToRefs(useContactStore());
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const deleteContact = async () => {
    isLoading.value = true;

    try {
      await deleteContactApi(+contact.value.id);

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
    deleteContact,
    isLoading,
    isSuccess
  };
}
