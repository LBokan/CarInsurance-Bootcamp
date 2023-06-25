import type { IContactState, IPhoneNumberState, IAddressState } from '@/helpers/interfaces';

export const statesCodesLabels = [
  'AL', 'AK', 'AZ', 'AR', 'CA', 'CO', 'CT', 'DE', 'FL', 'GA', 'HI', 'ID',
  'IL', 'IN', 'IA', 'KS', 'KY', 'LA', 'ME', 'MD', 'MA', 'MI', 'MN', 'MS',
  'MO', 'MT', 'NE', 'NV', 'NH', 'NJ', 'NM', 'NY', 'NC', 'ND', 'OH', 'OK',
  'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WA', 'WV',
  'WI', 'WY'
].sort();

export const contactTemplate: IContactState = {
  isCreateModalOpen: false,
  isEditModalOpen: false,
  formModel: null,
  id: 0,
  type: 'Police',
  firstName: '',
  lastName: '',
  email: '',
  phoneNumbers: [{
    id: 0,
    type: 'Mobile',
    number: ''
  }],
  addresses: [{
    id: 0,
    type: 'Home',
    city: '',
    state: '',
    zip: '',
    addressLine: ''
  }]
};

export const phoneNumberTemplate: IPhoneNumberState = {
  isCreateModalOpen: false,
  isEditModalOpen: false,
  formModel: null,
  id: 0,
  type: 'Mobile',
  number: ''
};

export const addressTemplate: IAddressState = {
  isCreateModalOpen: false,
  isEditModalOpen: false,
  formModel: null,
  id: 0,
  type: 'Home',
  city: '',
  state: '',
  zip: '',
  addressLine: ''
};

export const getContactDataPropName = (infoType: string) => {
  const infoTypeInLowerCase = infoType.toLowerCase();

  if (infoTypeInLowerCase.includes('phone')) {
    return 'phoneNumbers';
  } else if (infoTypeInLowerCase.includes('address')) {
    return 'addresses';
  }

  return '';
};