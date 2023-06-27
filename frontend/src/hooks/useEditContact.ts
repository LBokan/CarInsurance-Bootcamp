import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { editContactApi } from '@/api/Contact';
import { useSnackbarStore } from '@/stores/snackbar';
import { useAssignmentStore } from '@/stores/assignment';
import { useContactStore } from '@/stores/contact';

export function useEditContact() {
  const { assignment } = storeToRefs(useAssignmentStore());
  const { contact } = storeToRefs(useContactStore());
  const { getContactDataAPI } = useContactStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const editContact = async () => {
    const contactData = getContactDataAPI();

    isLoading.value = true;

    try {
      await editContactApi({
        id: +contact.value.id,
        data: {
          ...contactData,
          assignmentId: +assignment.value.id
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
    editContact,
    isLoading,
    isSuccess
  };
}
