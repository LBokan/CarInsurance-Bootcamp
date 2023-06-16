import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/authConstants';
import { type ICredentials } from '@/utils/interfaces';

export const authenticateUser = async (data: ICredentials) => {
  return createRequest({
    endpoint: 'api/auth/login',
    method: HTTP_METHODS.post,
    data: JSON.stringify(data)
  });
};