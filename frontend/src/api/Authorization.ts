import { createRequest } from '@/api/ApiProvider';
import { METHOD } from '@/helpers/constants';
import { type ICredentials } from '@/utils/interfaces';

export const loginAction = async (data: ICredentials) => {
  return createRequest('api/auth/login', METHOD.post, data);
};