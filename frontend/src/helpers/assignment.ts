import { format } from 'date-fns';
import type { IAssignment } from '@/helpers/interfaces';

type typeGetYearToday = () => number;

export const impactDirectionsLabels = [
  'Front', 'Front Right', 'Right Side', 'Right Quarter Panel', 'Right Rear', 
  'Rear', 'Front Left', 'Left Side', 'Left Quarter Panel', 'Left Rear'
];

export const getYearToday: typeGetYearToday = () => {
  return new Date().getFullYear();
}

export const formatDate = (date: string | Date) => {
  if (date instanceof Date) {
    return format(new Date(date), 'MM/dd/yyyy');
  } else if (date.includes("T")) {
    return format(new Date(date.split('T')[0]), 'MM/dd/yyyy');
  }
};

export const formatDateWithTime = (date: string | Date) => {
  return format(new Date(date), 'MM/dd/yyyy p');
};

export const assignmentTemplate: IAssignment = {
  isModalOpen: false,
  isDialogOpen: false,
  formModel: null,
  page: 1,
  id: 0,
  creationDate: '',
  incidentDate: new Date(),
  userId: 0,
  status: '',
  contacts: [
    {
      id: 0,
      type: 'Vehicle owner',
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
    }
  ],
  vehicleInfo: {
    vinNumber: '',
    carMake: '',
    carModel: '',
    yearOfManufacture: getYearToday(),
    odometerValue: 0,
    licensePlateNumber: '',
    licenseState: '',
    licenseExpirationDate: new Date()
  },
  vehicleConditionInfo: {
    directionOfImpact: '',
    namesOfPhotosOfImpact: '',
    photosOfImpactFiles: [],
    photosOfImpactStrings: []
  },
  insuranceAgency: '',
  repairFacility: '',
  comments: [{
    id: 0,
    dateOfCreation: new Date(),
    text: '',
    isRead: 0
  }]
};