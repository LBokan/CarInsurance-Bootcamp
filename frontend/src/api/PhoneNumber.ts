import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';

interface IEditPhoneNumberApiArgs {
  id: number,
  data: {
    contactInfoId: number
  }
}

interface ICreatePhoneNumberApiArgs {
  contactInfoId: number
}

export const getPhoneNumberTypesApi = async () => {
  return createRequest({
    endpoint: 'api/v1/phoneNumberType',
    method: HTTP_METHODS.get
  });
};

export const createPhoneNumberApi = async (data: ICreatePhoneNumberApiArgs) => {
  return createRequest({
    endpoint: `api/v1/insurance/phoneNumber`,
    method: HTTP_METHODS.post,
    data: JSON.stringify(data)
  });
};

export const editPhoneNumberApi = async ({id, data}: IEditPhoneNumberApiArgs) => {
  return createRequest({
    endpoint: `api/v1/insurance/phoneNumber/${id}`,
    method: HTTP_METHODS.put,
    data: JSON.stringify(data)
  });
};

export const deletePhoneNumberApi = async (id: number) => {
  return createRequest({
    endpoint: `api/v1/insurance/phoneNumber/${id}`,
    method: HTTP_METHODS.delete
  });
};