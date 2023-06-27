import { ref } from 'vue';
import { storeToRefs } from 'pinia';

import { setAssignmentRepairFacilityApi } from '@/api/Assignment';
import { useSnackbarStore } from '@/stores/snackbar';
import { useAssignmentStore } from '@/stores/assignment';

export function useSetAssignmentRepairFacility() {
  const { assignment } = storeToRefs(useAssignmentStore());
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const setAssignmentRepairFacility = async (repairFacilityName: string) => {
    isLoading.value = true;

    try {
      await setAssignmentRepairFacilityApi({
        id: +assignment.value.id,
        data: repairFacilityName
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
    setAssignmentRepairFacility,
    isLoading,
    isSuccess
  };
}
