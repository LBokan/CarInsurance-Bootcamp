import { reactive } from 'vue';
import { defineStore } from 'pinia';

import { format } from 'date-fns';
import { assignmentTemplate } from '@/helpers/assignment';
import type {
  IAssignment,
  IContact,
  IPhoneNumber,
  IAddress,
  IComment,
  IAssignmentInfoDataAPI,
  ICreateAssignmentAPI,
  IGetAssignmentAPI
} from '@/helpers/interfaces';

type TypeAssignmentKeys = keyof IAssignment;
type TypeGetAssignmentKeys = keyof IGetAssignmentAPI;

export const useAssignmentStore = defineStore('assignment', () => {
  const assignment: IAssignment = reactive(structuredClone(assignmentTemplate));

  function showAssignmentModal() {
    assignment.isModalOpen = true;
  }

  function showAssignmentDialog() {
    assignment.isDialogOpen = true;
  }

  function setAssignmentData<K extends TypeAssignmentKeys>(data: IAssignment) {
    Object.keys(data).forEach((key) => {
      const typedKey = key as K;

      assignment[typedKey] = data[typedKey];
    })
  }

  function setAssignmentDataAPI<K extends TypeGetAssignmentKeys>(data: IGetAssignmentAPI) {
    Object.keys(data).forEach((key) => {
      const typedKey = key as K;

      switch (key) {
        case 'dateOfCreation':
          assignment.creationDate = data.dateOfCreation;
          break;
        case 'dateOfIncident':
          assignment.incidentDate = new Date(data.dateOfIncident.split('T')[0]);
          break;
        case 'contactsInfo':
          assignment.contacts = data.contactsInfo;
          break;
        case 'vehicleConditionInfo':
          assignment.vehicleConditionInfo.directionOfImpact = data.vehicleConditionInfo.directionOfImpact;
          assignment.vehicleConditionInfo.namesOfPhotosOfImpact = data.vehicleConditionInfo.namesOfPhotosOfImpact;
          break;
        default:
          assignment[typedKey] = data[typedKey];
      }
    });
  }

  function setPhotosOfImpactStrings(photos: string[]) {
    assignment.vehicleConditionInfo.photosOfImpactStrings = photos;
  }

  function setCommentsData(data: IComment[]) {
    assignment.comments = data;
  }

  function goToNextPage() {
    if (assignment.page < 3) {
      assignment.page = assignment.page + 1;
    }
  }

  function goToPrevPage() {
    if (assignment.page > 1) {
      assignment.page = assignment.page - 1;
    }
  }

  function addContactData(contactData: IContact) {
    assignment.contacts.push(contactData);
  }

  function addContactInfoData<T extends IPhoneNumber | IAddress>(
    contactIndex: number,
    assignmentDataPropName: keyof IContact,
    infoData: T
  ) {
    const propValue = assignment
      .contacts[contactIndex][assignmentDataPropName] as Array<T>;

    propValue.push(infoData);
  }

  function removeContactData(contactIndex: number) {
    assignment.contacts.splice(contactIndex, 1);
  }

  function removePhoneNumberData(contactIndex: number, phoneNumberId: string) {
    const phoneNumbersArray = assignment.contacts[contactIndex].phoneNumbers;
    const filteredPhoneNumbersData = phoneNumbersArray.filter(
      (phoneNumber) => phoneNumber.id !== phoneNumberId
    );

    assignment.contacts[contactIndex].phoneNumbers = filteredPhoneNumbersData;
  }

  function removeAddressData(contactIndex: number, addressId: string) {
    const addressesArray = assignment.contacts[contactIndex].addresses;
    const filteredAddressesData = addressesArray.filter(
      (address) => address.id !== addressId
    );

    assignment.contacts[contactIndex].addresses = filteredAddressesData;
  }

  function closeAndResetAssignmentModalOrDialog<K extends TypeAssignmentKeys>() {
    Object.keys(assignmentTemplate).forEach((key) => {
      const typedKey = key as K;

      assignment[typedKey] = structuredClone(assignmentTemplate[typedKey]);
    });
  }

  function getAssignmentDataAPI(): ICreateAssignmentAPI {
    const formattedContacts: IAssignmentInfoDataAPI[] = assignment.contacts.map(contact => {
      const formattedPhoneNumbers: IAssignmentInfoDataAPI[] = contact.phoneNumbers.map(phoneNumber => ({
        type: phoneNumber.type,
        number: phoneNumber.number
      }));

      const formattedAddresses: IAssignmentInfoDataAPI[] = contact.addresses.map(address => ({
        type: address.type,
        city: address.city,
        state: address.city,
        zip: address.zip,
        addressLine: address.addressLine
      }));

      return {
        type: contact.type,
        firstName: contact.firstName,
        lastName: contact.lastName,
        email: contact.email,
        phoneNumbers: formattedPhoneNumbers,
        addresses: formattedAddresses
      };
    });
    
    return {
      dateOfIncident: format(new Date(assignment.incidentDate), 'yyyy-MM-dd'),
      contactsInfo: formattedContacts,
      vehicleInfo: {
        vinNumber: assignment.vehicleInfo.vinNumber,
        carMake: assignment.vehicleInfo.carMake,
        carModel: assignment.vehicleInfo.carModel,
        yearOfManufacture: assignment.vehicleInfo.yearOfManufacture,
        odometerValue: assignment.vehicleInfo.odometerValue,
        licensePlateNumber: assignment.vehicleInfo.licensePlateNumber,
        licenseState: assignment.vehicleInfo.licenseState,
        licenseExpirationDate: format(new Date(assignment.vehicleInfo.licenseExpirationDate), 'yyyy-MM-dd')
      },
      vehicleConditionInfo: {
        directionOfImpact: assignment.vehicleConditionInfo.directionOfImpact
      }
    }
  }

  return {
    assignment,
    showAssignmentModal,
    showAssignmentDialog,
    setAssignmentData,
    setAssignmentDataAPI,
    setPhotosOfImpactStrings,
    setCommentsData,
    goToNextPage,
    goToPrevPage,
    addContactData,
    addContactInfoData,
    removeContactData,
    removePhoneNumberData,
    removeAddressData,
    closeAndResetAssignmentModalOrDialog,
    getAssignmentDataAPI
  }
})
