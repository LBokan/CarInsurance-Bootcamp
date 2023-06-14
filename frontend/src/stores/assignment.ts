import { reactive } from 'vue';
import { defineStore } from 'pinia';

import { format } from 'date-fns';
import { assignmentTemplate } from '@/helpers/assignmentModal';
import {
  type IAssignment,
  type IContacts,
  type IPhoneNumbers,
  type IAddresses,
  type IAssignmentInfoDataAPI,
  type IAssignmentAPI
} from '@/utils/interfaces';

type TypeAssignmentKeys = keyof IAssignment;

export const useAssignmentStore = defineStore('assignment', () => {
  const assignment: IAssignment = reactive(structuredClone(assignmentTemplate));

  function showAssignmentModal() {
    assignment.isOpen = true;
  }

  function setAssignmentData<K extends TypeAssignmentKeys>(data: IAssignment) {
    Object.keys(data).forEach((key) => {
      const typedKey = key as K;

      assignment[typedKey] = data[typedKey];
    })
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

  function addContactData(contactData: IContacts) {
    assignment.contacts.push(contactData);
  }

  function addContactInfoData<T extends IPhoneNumbers | IAddresses>(
    contactIndex: number,
    assignmentDataPropName: keyof IContacts,
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

  function closeAndResetAssignmentModal<K extends TypeAssignmentKeys>() {
    Object.keys(assignmentTemplate).forEach((key) => {
      const typedKey = key as K;

      assignment[typedKey] = structuredClone(assignmentTemplate[typedKey]);
    });
  }

  function getAssignmentDataAPI(): IAssignmentAPI {
    const formattedContacts: IAssignmentInfoDataAPI[] = [];

    assignment.contacts.forEach(contact => {
      const formattedPhoneNumbers: IAssignmentInfoDataAPI[] = [];
      const formattedAddresses: IAssignmentInfoDataAPI[] = [];

      contact.phoneNumbers.forEach(phoneNumber => {
        formattedPhoneNumbers.push({
          type: phoneNumber.type,
          number: phoneNumber.number
        })
      })

      contact.addresses.forEach(address => {
        formattedAddresses.push({
          type: address.type,
          city: address.city,
          state: address.city,
          zip: address.zip,
          addressLine: address.addressLine
        })
      })

      formattedContacts.push({
        type: contact.type,
        firstName: contact.firstName,
        lastName: contact.lastName,
        email: contact.email,
        phoneNumbers: formattedPhoneNumbers,
        addresses: formattedAddresses
      });
    })

    return {
      dateOfIncident: format(new Date(assignment.incidentDate), 'yyyy-MM-dd'),
      contactsInfo: formattedContacts,
      vehicleInfo: {
        vinNumber: assignment.vehicleInfo.vinNumber,
        carMake: assignment.vehicleInfo.carMake,
        carModel: assignment.vehicleInfo.carModel,
        yearOfManufacture: +format(new Date(assignment.vehicleInfo.yearOfManufacture), 'yyyy'),
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
    setAssignmentData,
    goToNextPage,
    goToPrevPage,
    addContactData,
    addContactInfoData,
    removeContactData,
    removePhoneNumberData,
    removeAddressData,
    closeAndResetAssignmentModal,
    getAssignmentDataAPI
  }
})
