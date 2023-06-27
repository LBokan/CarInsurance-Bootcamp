import { reactive, ref } from 'vue';
import { getRepairFacilitiesApi } from '@/api/Company';
import { useSnackbarStore } from '@/stores/snackbar';

export function useGetRepairFacilities() {
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const data = reactive<string[]>([]);
  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');
  const isSuccess = ref<boolean>(false);

  const getRepairFacilities = async () => {
    isLoading.value = true;

    try {
      const response: string[] = await getRepairFacilitiesApi();

      errorData.value = '';
      isSuccess.value = true;

      response.forEach(company => {
        data.push(company);
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
    getRepairFacilities,
    data,
    isLoading,
    isSuccess
  };
}
