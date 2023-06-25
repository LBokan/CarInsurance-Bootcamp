import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';
import type { ICredentials } from '@/helpers/interfaces';

export const authenticateUser = async (data: ICredentials) => {
  return createRequest({
    endpoint: 'api/v1/auth/login',
    method: HTTP_METHODS.post,
    data: JSON.stringify(data)
  });
};