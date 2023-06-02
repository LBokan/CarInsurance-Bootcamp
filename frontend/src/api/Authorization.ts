import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';
import { type ICredentials } from '@/utils/interfaces';

export const authenticateUser = async (data: ICredentials) => {
  return createRequest('api/auth/login', HTTP_METHODS.post, data);
};