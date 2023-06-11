import { reactive } from 'vue';
import { defineStore } from 'pinia';

import { assignmentTemplateState } from '@/helpers/assignmentModal';
import { 
  type IAssignmentState, 
  type IContactsInfo,
  type IPhoneNumbers,
  type IAddresses
} from '@/utils/interfaces';

type TypeAssignmentStateKeys = keyof IAssignmentState;

export const useAssignmentStore = defineStore('assignment', () => {
  const assignmentState: IAssignmentState = reactive(JSON.parse(JSON.stringify(assignmentTemplateState)));

  function showAssignmentModal() {
    assignmentState.isOpen = true;
  }
  
  function setAssignmentStateData<K extends TypeAssignmentStateKeys>(data: IAssignmentState) {
    Object.keys(data).forEach( ( key ) => {
      const typedKey = key as K;

      assignmentState[typedKey] = data[typedKey];
    });
  }

  function setNextPage() {
    if (assignmentState.page < 3) {
      assignmentState.page = assignmentState.page + 1;
    }
  }

  function setPrevPage() {
    if (assignmentState.page > 1) {
      assignmentState.page = assignmentState.page - 1;
    }
  }

  function addContactData(contactData: IContactsInfo) {
    assignmentState.contactsInfo.push(contactData);
  }

  function addContactInfoData<T extends IPhoneNumbers | IAddresses>(
    contactIndex: number, 
    assignmentDataPropName: keyof IContactsInfo, 
    infoData: T
  ) {
    const propValue = assignmentState.contactsInfo[contactIndex][assignmentDataPropName] as Array<T>;
    
    propValue.push(infoData);
  }

  function removeContactData(contactIndex: number) {
    assignmentState.contactsInfo.splice(contactIndex, 1);
  }

  function removePhoneNumberData(contactIndex: number, phoneNumberId: number) {
    const phoneNumbersArray = assignmentState.contactsInfo[contactIndex].phoneNumbers;
    const filteredPhoneNumbersData = phoneNumbersArray.filter(phoneNumber => phoneNumber.id !== phoneNumberId);
      
    assignmentState.contactsInfo[contactIndex].phoneNumbers = filteredPhoneNumbersData;
  }

  function removeAddressData(contactIndex: number, addressId: number) {
    const addressesArray = assignmentState.contactsInfo[contactIndex].addresses;
    const filteredAddressesData = addressesArray.filter(address => address.id !== addressId);
      
    assignmentState.contactsInfo[contactIndex].addresses = filteredAddressesData;
  }

  function closeAndResetAssignmentModal<K extends TypeAssignmentStateKeys>() {
    Object.keys(assignmentTemplateState).forEach( ( key ) => {
      const typedKey = key as K;

      assignmentState[typedKey] = JSON.parse(JSON.stringify(assignmentTemplateState[typedKey]));
    });
  }

  return { 
    assignmentState,
    showAssignmentModal,
    setAssignmentStateData,
    setNextPage,
    setPrevPage,
    addContactData,
    addContactInfoData,
    removeContactData,
    removePhoneNumberData,
    removeAddressData,
    closeAndResetAssignmentModal
  }
})
