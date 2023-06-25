import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { createCommentsApi } from '@/api/Comment';
import { useSnackbarStore } from '@/stores/snackbar';
import { useAssignmentStore } from '@/stores/assignment';
import type { ICreateCommentAPI } from '@/helpers/interfaces';

export function useCreateComment() {
  const { assignment } = storeToRefs(useAssignmentStore());
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const createComment = async (data: ICreateCommentAPI) => {
    isLoading.value = true;

    try {
      await createCommentsApi({
        assignmentId: +assignment.value.id,
        data: data
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
    createComment,
    isLoading,
    isSuccess
  };
}
