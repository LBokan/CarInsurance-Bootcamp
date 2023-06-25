import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { setIsCommentReadApi } from '@/api/Comment';
import { useSnackbarStore } from '@/stores/snackbar';
import { useAssignmentStore } from '@/stores/assignment';

export function useSetIsCommentRead() {
  const { assignment } = storeToRefs(useAssignmentStore());
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const setIsCommentRead = async (commentId: number) => {
    isLoading.value = true;

    try {
      await setIsCommentReadApi({
        assignmentId: +assignment.value.id,
        commentId
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
    setIsCommentRead,
    isLoading,
    isSuccess
  };
}
