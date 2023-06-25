import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { createContactApi } from '@/api/Contact';
import { useSnackbarStore } from '@/stores/snackbar';
import { useAssignmentStore } from '@/stores/assignment';
import { useContactStore } from '@/stores/contact';

export function useCreateContact() {
  const { assignment } = storeToRefs(useAssignmentStore());
  const { getContactDataAPI } = useContactStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const createContact = async () => {
    const contactData = getContactDataAPI();

    isLoading.value = true;

    try {
      await createContactApi({
        ...contactData,
        assignmentId: +assignment.value.id
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
    createContact,
    isLoading,
    isSuccess
  };
}
