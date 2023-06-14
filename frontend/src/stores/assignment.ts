import { reactive } from 'vue';
import { defineStore } from 'pinia';

import { assignmentTemplate } from '@/helpers/assignmentModal';
import {
  type IAssignment,
  type IContacts,
  type IPhoneNumbers,
  type IAddresses
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
    closeAndResetAssignmentModal
  }
})
