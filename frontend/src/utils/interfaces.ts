// General interface
export type AppEvents = {
  isAssignmentCreated: boolean;
};

// Rules interface
type TypeValidateFunc = (value: string) => boolean | string;

export interface IFormRules {
  [n: string]: TypeValidateFunc
};

type TypeValidateFilesFunc = (value: File[]) => boolean | string;

export interface IFormFilesRules {
  [n: string]: TypeValidateFilesFunc
};

// User interface
interface IRoleObj {
  roleId: number,
  name: string
};

export interface IUserState {
  email: string,
  firstName: string,
  lastName: string,
  role: IRoleObj,
  roleId: number | null,
  userId: number | null,
};

export interface ICredentials {
  email: string,
  password: string
};

// Assignment interface
export interface IPhoneNumbers {
  id: string | number,
  type: string,
  number: string
}

export interface IAddresses {
  id: string | number,
  type: string,
  city: string,
  state: string | null,
  zip: string,
  addressLine: string
}

export interface IContacts {
  id: string | number,
  type: string,
  firstName: string,
  lastName: string,
  email: string,
  phoneNumbers: IPhoneNumbers[],
  addresses: IAddresses[]
}

interface IVehicleInfo {
  vinNumber: string,
  carMake: string,
  carModel: string,
  yearOfManufacture: number,
  odometerValue: string,
  licensePlateNumber: string,
  licenseState: string,
  licenseExpirationDate: Date | string
}

interface IVehicleConditionInfo {
  directionOfImpact: string,
  namesOfPhotosOfImpact: string,
  photosOfImpactFiles: File[]
  photosOfImpactStrings: string[]
}

export interface IAssignment {
  isModalOpen: boolean,
  isDialogOpen: boolean,
  formModel: boolean | null,
  page: number,
  id: number,
  creationDate: string,
  incidentDate: Date,
  status: string,
  contacts: IContacts[],
  vehicleInfo: IVehicleInfo,
  vehicleConditionInfo: IVehicleConditionInfo
}

// Assignment API interface
export interface IAssignmentInfoDataAPI {
  [key: string]: string | IAssignmentInfoDataAPI[]
}

export interface ICreateAssignmentAPI {
  dateOfIncident: string,
  contactsInfo: IAssignmentInfoDataAPI[],
  vehicleInfo: IVehicleInfo,
  vehicleConditionInfo: IAssignmentInfoDataAPI
}

interface IGetVehicleConditionInfo {
  directionOfImpact: string,
  namesOfPhotosOfImpact: string
}

export interface IGetAssignmentAPI {
  assignmentId: number,
  dateOfCreation: string,
  dateOfIncident: string,
  status: string,
  contactsInfo: IContacts[],
  vehicleInfo: IVehicleInfo,
  vehicleConditionInfo: IGetVehicleConditionInfo
}
