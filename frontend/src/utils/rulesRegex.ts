import { reactive } from 'vue';
import { type IFormRules, type IFormFilesRules } from '@/utils/interfaces';

export const rules: IFormRules = reactive({
  required: (value) => value ? true : 'Value is required',
  email: (value) => /.+@.+\..+/.test(value) ? 
    true : 
    'Value is not a valid email address',
  password: (value) => (value && value.length >= 8) ? 
    true : 
    'This field should be at least 8 characters long',
  phoneNumber: (value) => 
    /^[+(]?\d{0,2}[)-\s.]?\d{3}[-\s.]?\d{3}[-\s.]?\d{3,4}$/.test(value) ? 
    true : 
    'Value is not a valid phone number',
  zipCode: (value) => /^\d{5}[-\s.]?(\d{4})?$/.test(value) ? 
    true : 
    'Value is not a valid zip code',
  odometerValue: (value) => /^\d*$/.test(value) && +value > 0 ? 
    true : 
    'Value is not a valid odometer value',
  licensePlate: (value) => /^[\dA-Za-z]{7,8}$/.test(value) ? 
    true : 
    'Value is not a valid license plate value',
  vinNumber: (value) => /^[\dA-Za-z]{17}$/.test(value) ? 
    true : 
    'Invalid VIN code'
});

export const rulesFiles: IFormFilesRules = reactive({
  photoSizeMax: (value) => !value || !value.length || value[0]?.size < 10485760 ? 
    true :
    'A photo should be less than 10 MB'
});