import { ref } from 'vue';

import { getPhotoApi } from '@/api/Photo';
import { useAssignmentStore } from '@/stores/assignment';
import { useSnackbarStore } from '@/stores/snackbar';

interface IGetPhotosArgs {
  userId: number, 
  assignmentId: number,
  namesOfPhotosOfImpact: string
}

export function useGetPhotos() {
  const { setPhotosOfImpactStrings } = useAssignmentStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const isLoading = ref<boolean>(false);
  const errorData = ref<string>('');

  const getPhotos = async ({userId, assignmentId, namesOfPhotosOfImpact}: IGetPhotosArgs) => {
    const namesOfPhotosOfImpactArr = namesOfPhotosOfImpact.split(';');

    isLoading.value = true;

    try {
      const photoPromises = namesOfPhotosOfImpactArr.map(async (photoName) => {
        const response = await getPhotoApi({ userId, assignmentId, photoName });

        const blob: Blob = await response.blob();
        const photoDataUrl: string = String(await createPhotoDataUrl(blob));
  
        return photoDataUrl;
      });
  
      const photosOfImpactStrings = await Promise.all(photoPromises);

      errorData.value = '';
  
      setPhotosOfImpactStrings(photosOfImpactStrings);
    } catch (error) {
      let errorMessage = 'Unknown Error';

      if (error instanceof Error) {
        errorMessage = error.message;
      }

      errorData.value = errorMessage;

      setSnackbarDataAndShow(errorData.value, 'error');
    } finally {
      isLoading.value = false;
    }
  };

  const createPhotoDataUrl = async (blob: Blob) => {
    const reader = new FileReader();

    const result = await new Promise((resolve) => {
      reader.onloadend = () => {
        resolve(reader.result);
      };
  
      reader.readAsDataURL(blob);
    });
  
    return result;
  };

  return {
    getPhotos,
    isLoading
  };
}
