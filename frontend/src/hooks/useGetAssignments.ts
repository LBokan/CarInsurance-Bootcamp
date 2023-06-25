import { reactive, ref } from 'vue';

import { getAssignmentsApi } from '@/api/Assignment';
import { useSnackbarStore } from '@/stores/snackbar';
import type { IGetAssignmentAPI } from '@/helpers/interfaces';

export function useGetAssignments() {
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const data = reactive<IGetAssignmentAPI[]>([]);
  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const getAssignments = async () => {
    data.splice(0);
    isLoading.value = true;

    try {
      const response: IGetAssignmentAPI[] = await getAssignmentsApi();

      errorData.value = '';
      isSuccess.value = true;

      response.forEach(assignment => {
        data.push(assignment);
      });
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
    getAssignments,
    data,
    isLoading,
    isSuccess
  };
}
