import { reactive } from 'vue';
import { defineStore } from 'pinia';

import { contactTemplate } from '@/helpers/contact';
import type { 
  IContact, 
  IContactState, 
  IPhoneNumber, 
  IAddress,
  IAssignmentInfoDataAPI
} from '@/helpers/interfaces';

type TypeContactKeys = keyof IContact;

export const useContactStore = defineStore('contact', () => {
  const contact: IContactState = reactive(structuredClone(contactTemplate));
  
  function showCreateContactModal() {
    contact.isCreateModalOpen = true;
  }

  function showEditContactModal() {
    contact.isEditModalOpen = true;
  }

  function setContactData<K extends TypeContactKeys>(data: IContact) {
    Object.keys(data).forEach((key) => {
      const typedKey = key as K;

      if (typeof data[typedKey] === 'object' && data[typedKey] !== null) {
        contact[typedKey] = JSON.parse(JSON.stringify(data[typedKey]));
      } else {
        contact[typedKey] = data[typedKey];
      }
    })
  }

  function addContactInfoData<T extends IPhoneNumber | IAddress>(
    contactDataPropName: keyof IContact,
    infoData: T
  ) {
    const propValue = contact[contactDataPropName] as Array<T>;

    propValue.push(infoData);
  }

  function removePhoneNumberData(phoneNumberId: string) {
    const filteredPhoneNumbersData = contact.phoneNumbers.filter(
      (phoneNumber) => phoneNumber.id !== phoneNumberId
    );

    contact.phoneNumbers = filteredPhoneNumbersData;
  }

  function removeAddressData(addressId: string) {
    const filteredAddressesData = contact.addresses.filter(
      (address) => address.id !== addressId
    );

    contact.addresses = filteredAddressesData;
  }

  function closeAndResetContactModal<K extends TypeContactKeys>() {
    Object.keys(contactTemplate).forEach((key) => {
      const typedKey = key as K;

      contact[typedKey] = structuredClone(contactTemplate[typedKey]);
    });
  }

  function getContactDataAPI(): IAssignmentInfoDataAPI {
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
  }

  return {
    contact,
    setContactData,
    showCreateContactModal,
    showEditContactModal,
    addContactInfoData,
    removePhoneNumberData,
    removeAddressData,
    closeAndResetContactModal,
    getContactDataAPI
  }
})
