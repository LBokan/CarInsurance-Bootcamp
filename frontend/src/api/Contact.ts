import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';

interface IEditContactApiArgs {
  id: number,
  data: {
    assignmentId: number
  }
}

interface ICreateContactApiArgs {
  assignmentId: number
}

export const getContactTypesApi = async () => {
  return createRequest({
    endpoint: 'api/v1/contactInfoType',
    method: HTTP_METHODS.get
  });
};

export const createContactApi = async (data: ICreateContactApiArgs) => {
  return createRequest({
    endpoint: `api/v1/insurance/contact`,
    method: HTTP_METHODS.post,
    data: JSON.stringify(data)
  });
};

export const editContactApi = async ({id, data}: IEditContactApiArgs) => {
  return createRequest({
    endpoint: `api/v1/insurance/contact/${id}`,
    method: HTTP_METHODS.put,
    data: JSON.stringify(data)
  });
};

export const deleteContactApi = async (id: number) => {
  return createRequest({
    endpoint: `api/v1/insurance/contact/${id}`,
    method: HTTP_METHODS.delete
  });
};