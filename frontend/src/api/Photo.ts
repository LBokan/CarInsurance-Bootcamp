import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';

interface IGetPhotoApiArgs {
  userId: number,
  assignmentId: number,
  photoName: string
}

export const getPhotoApi = async ({userId, assignmentId, photoName}: IGetPhotoApiArgs) => {
  return createRequest({
    endpoint: `api/v1/photo/photosofimpact/${userId}/${assignmentId}/${photoName}`,
    method: HTTP_METHODS.get,
    isGetPhoto: true
  });
};