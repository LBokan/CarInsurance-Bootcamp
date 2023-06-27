import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';

export const getUserApi = async () => {
  return createRequest({
    endpoint: 'api/v1/user',
    method: HTTP_METHODS.get
  });
};