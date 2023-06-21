import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/authConstants';

export const createAssignmentApi = async (data: FormData) => {
  return createRequest({
    endpoint: 'api/user/assignment',
    method: HTTP_METHODS.post,
    isMultipartHeaders: true,
    data
  });
};

export const getAssignmentsApi = async () => {
  return createRequest({
    endpoint: 'api/user/assignment',
    method: HTTP_METHODS.get
  });
};

export const getAssignmentByIdApi = async (id: number) => {
  return createRequest({
    endpoint: `api/user/assignment/${id}`,
    method: HTTP_METHODS.get
  });
};