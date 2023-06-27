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
export interface IUserState {
  id: number | null,
  firstName: string,
  lastName: string,
  email: string,
  role: string,
  insuranceCompany?: string,
  companyOfWork?: string
};

export interface ICredentials {
  email: string,
  password: string
};

// Assignment interface
export interface IPhoneNumber {
  id: number | string,
  type: string,
  number: string
}

export interface IPhoneNumberState extends IPhoneNumber {
  isCreateModalOpen?: boolean,
  isEditModalOpen?: boolean,
  formModel?: boolean | null
}

export interface IAddress {
  id: number | string,
  type: string,
  city: string,
  state: string,
  zip: string,
  addressLine: string
}

export interface IAddressState extends IAddress {
  isCreateModalOpen?: boolean,
  isEditModalOpen?: boolean,
  formModel?: boolean | null
}

export interface IContact {
  id: number | string,
  type: string,
  firstName: string,
  lastName: string,
  email: string,
  phoneNumbers: IPhoneNumber[],
  addresses: IAddress[]
}

export interface IContactState extends IContact {
  isCreateModalOpen?: boolean,
  isEditModalOpen?: boolean,
  formModel?: boolean | null
}

interface IVehicleInfo {
  vinNumber: string,
  carMake: string,
  carModel: string,
  yearOfManufacture: number,
  odometerValue: number,
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

export interface IComment {
  id: number,
  dateOfCreation: Date,
  text: string,
  isRead: number
}

export interface IAssignment {
  isModalOpen: boolean,
  isDialogOpen: boolean,
  formModel: boolean | null,
  page: number,
  id: number | string,
  creationDate: string,
  incidentDate: Date,
  userId: number,
  status: string,
  contacts: IContact[],
  vehicleInfo: IVehicleInfo,
  vehicleConditionInfo: IVehicleConditionInfo,
  insuranceAgency: string,
  repairFacility?: string,
  comments?: IComment[]
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
  id: number,
  dateOfCreation: string,
  dateOfIncident: string,
  userId: number,
  status: string,
  contactsInfo: IContact[],
  vehicleInfo: IVehicleInfo,
  vehicleConditionInfo: IGetVehicleConditionInfo,
  insuranceAgency: string,
  repairFacility?: string
}

export interface ICreateCommentAPI {
  text: string
}
