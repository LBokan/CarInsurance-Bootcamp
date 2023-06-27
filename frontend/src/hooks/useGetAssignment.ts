import { ref } from 'vue';

import { getAssignmentByIdApi } from '@/api/Assignment';
import { useSnackbarStore } from '@/stores/snackbar';
import { useAssignmentStore } from '@/stores/assignment';
import type { IGetAssignmentAPI } from '@/helpers/interfaces';

export function useGetAssignment() {
  const { setAssignmentDataAPI } = useAssignmentStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const getAssignmentById = async (assignmentId: number) => {
    isLoading.value = true;

    try {
      const response: IGetAssignmentAPI = await getAssignmentByIdApi(assignmentId);

      errorData.value = '';
      isSuccess.value = true;

      setAssignmentDataAPI(response);
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
    getAssignmentById,
    isLoading,
    isSuccess
  };
}
