import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';

interface IEditAddressApiArgs {
  id: number,
  data: {
    contactInfoId: number
  }
}

interface ICreateAddressApiArgs {
  contactInfoId: number
}

export const getAddressTypesApi = async () => {
  return createRequest({
    endpoint: 'api/v1/addressType',
    method: HTTP_METHODS.get
  });
};

export const createAddressApi = async (data: ICreateAddressApiArgs) => {
  return createRequest({
    endpoint: `api/v1/insurance/address`,
    method: HTTP_METHODS.post,
    data: JSON.stringify(data)
  });
};

export const editAddressApi = async ({id, data}: IEditAddressApiArgs) => {
  return createRequest({
    endpoint: `api/v1/insurance/address/${id}`,
    method: HTTP_METHODS.put,
    data: JSON.stringify(data)
  });
};

export const deleteAddressApi = async (id: number) => {
  return createRequest({
    endpoint: `api/v1/insurance/address/${id}`,
    method: HTTP_METHODS.delete
  });
};