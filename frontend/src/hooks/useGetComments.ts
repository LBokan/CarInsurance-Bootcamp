import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { getCommentsApi } from '@/api/Comment';
import { useAssignmentStore } from '@/stores/assignment';
import { useSnackbarStore } from '@/stores/snackbar';
import type { IComment } from '@/helpers/interfaces';

export function useGetComments() {
  const { assignment } = storeToRefs(useAssignmentStore());
  const { setCommentsData } = useAssignmentStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const getComments = async () => {
    isLoading.value = true;

    try {
      const response: IComment[] = await getCommentsApi(+assignment.value.id);

      errorData.value = '';
      isSuccess.value = true;

      setCommentsData(response);
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
    getComments,
    isLoading,
    isSuccess
  };
}
