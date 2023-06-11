// Rules interface
type TypeValidateFunc = (value: string) => boolean | string;

export interface IFormRules {
  [n: string]: TypeValidateFunc
};

// User interface
interface IAuthoritiesObj {
  authority: string
};

interface IRoleObj {
  roleId?: number,
  name?: string
};

export interface IUserState {
  accountNonExpired?: boolean,
  accountNonLocked?: boolean,
  authorities?: Array<IAuthoritiesObj>,
  credentialsNonExpired?: boolean,
  email: string,
  enabled?: boolean,
  firstName: string,
  lastName: string,
  role: IRoleObj,
  roleId: number | null,
  userId: number | null,
  username?: string
};

export interface ICredentials {
  email: string,
  password: string
};

// Assignment interface
export interface IPhoneNumbers {
  id: number,
  type: string,
  number: string
}

export interface IAddresses {
  id: number,
  type: string,
  city: string,
  state: string | null,
  zip: string,
  addressLine: string
}

export interface IContactsInfo {
  id: number,
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
  licenseExpirationDate: Date
}

interface IVehicleConditionInfo {
  directionsOfImpact: string[],
  photosOfImpact: File[]
}

export interface IAssignmentState {
  isOpen: boolean,
  formModel: boolean | null,
  page: number,
  contactsInfo: IContactsInfo[],
  vehicleInfo: IVehicleInfo,
  vehicleConditionInfo: IVehicleConditionInfo
}