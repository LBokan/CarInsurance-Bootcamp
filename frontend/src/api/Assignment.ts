import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';

interface ISetAssignmentRepairFacilityApiArgs {
  id: number,
  data: string
}

export const getAssignmentsApi = async () => {
  return createRequest({
    endpoint: 'api/v1/assignment',
    method: HTTP_METHODS.get
  });
};

export const getAssignmentByIdApi = async (id: number) => {
  return createRequest({
    endpoint: `api/v1/assignment/${id}`,
    method: HTTP_METHODS.get
  });
};

export const createAssignmentApi = async (data: FormData) => {
  return createRequest({
    endpoint: 'api/v1/client/assignment',
    method: HTTP_METHODS.post,
    isMultipartHeaders: true,
    data
  });
};

export const setAssignmentRepairFacilityApi = async ({id, data}: ISetAssignmentRepairFacilityApiArgs) => {
  return createRequest({
    endpoint: `api/v1/insurance/assignment/${id}`,
    method: HTTP_METHODS.put,
    data: JSON.stringify(data)
  });
};