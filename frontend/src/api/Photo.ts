import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/authConstants';

interface IGetPhotoApiArgs {
  userId: number,
  assignmentId: number,
  photoName: string
}

export const getPhotoApi = async ({userId, assignmentId, photoName}: IGetPhotoApiArgs) => {
  return createRequest({
    endpoint: `api/user/photo/photosofimpact/${userId}/${assignmentId}/${photoName}`,
    method: HTTP_METHODS.get,
    isGetPhoto: true
  });
};