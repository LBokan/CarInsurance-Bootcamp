import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';

export const getRepairFacilitiesApi = async () => {
  return createRequest({
    endpoint: `api/v1/insurance/repairFacility`,
    method: HTTP_METHODS.get
  });
};