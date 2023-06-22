import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { createAssignmentApi } from '@/api/Assignment';
import { useSnackbarStore } from '@/stores/snackbar';
import { useAssignmentStore } from '@/stores/assignment';

export function useCreateAssignment() {
  const { assignment } = storeToRefs(useAssignmentStore());
  const { getAssignmentDataAPI } = useAssignmentStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const createAssignment = async () => {
    const formData = new FormData();
    const assignmentData = getAssignmentDataAPI();

    isLoading.value = true;

    formData.append('assignment', new Blob( [JSON.stringify(assignmentData)], { type: 'application/json' } ));

    assignment.value.vehicleConditionInfo.photosOfImpactFiles.forEach(photo => {
      formData.append('photosOfImpact', photo);
    });

    try {
      await createAssignmentApi(formData);

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
    createAssignment,
    isLoading,
    isSuccess
  };
}
