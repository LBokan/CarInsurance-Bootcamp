import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/authConstants';

export const getUserApi = async () => {
  return createRequest({
    endpoint: 'api/user',
    method: HTTP_METHODS.get
  });
};