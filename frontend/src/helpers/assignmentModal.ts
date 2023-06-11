import { type IAssignmentState } from '@/utils/interfaces';

type typeGetYearToday = () => number;

export const contactTypesLabels = [
  'Vehicle owner information',
  'Estimator information',
  'Police information',
  'Vehicle driver information',
  'Attorney information',
  'Rental information'
];

export const phoneNumberTypesLabels = [
  'Home', 
  'Business', 
  'Mobile', 
  'Fax', 
  'Other'
];

export const addressesTypesLabels = [
  'Home', 
  'Work', 
  'Mailing', 
  'Other'
];

export const statesCodesLabels = [
  'AL', 'AK', 'AZ', 'AR', 'CA', 'CO', 'CT', 'DE', 'FL', 'GA', 'HI', 'ID',
  'IL', 'IN', 'IA', 'KS', 'KY', 'LA', 'ME', 'MD', 'MA', 'MI', 'MN', 'MS',
  'MO', 'MT', 'NE', 'NV', 'NH', 'NJ', 'NM', 'NY', 'NC', 'ND', 'OH', 'OK',
  'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WA', 'WV',
  'WI', 'WY'
].sort();

export const impactDirectionsLabels = [
  'Front', 'Front Right', 'Right Side', 'Right Quarter Panel', 'Right Rear', 
  'Rear', 'Front Left', 'Left Side', 'Left Quarter Panel', 'Left Rear'
];

export const getYearToday: typeGetYearToday = () => {
  return new Date().getFullYear();
}

export const assignmentTemplateState: IAssignmentState = {
  isOpen: false,
  formModel: null,
  page: 1,
  contactsInfo: [
    {
      id: 0,
      type: 'Vehicle owner information',
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
      }],
    }
  ],
  vehicleInfo: {
    vinNumber: '',
    carMake: '',
    carModel: '',
    yearOfManufacture: getYearToday(),
    odometerValue: '',
    licensePlateNumber: '',
    licenseState: '',
    licenseExpirationDate: new Date()
  },
  vehicleConditionInfo: {
    directionsOfImpact: [],
    photosOfImpact: []
  }
};