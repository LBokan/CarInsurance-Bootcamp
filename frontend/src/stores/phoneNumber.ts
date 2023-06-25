import { reactive } from 'vue';
import { defineStore } from 'pinia';

import { phoneNumberTemplate } from '@/helpers/contact';
import type { IPhoneNumber, IPhoneNumberState, IAssignmentInfoDataAPI } from '@/helpers/interfaces';

type TypePhoneNumberKeys = keyof IPhoneNumber;

export const usePhoneNumberStore = defineStore('phoneNumber', () => {
  const phoneNumber: IPhoneNumberState = reactive(structuredClone(phoneNumberTemplate));
  
  function showCreatePhoneNumberModal() {
    phoneNumber.isCreateModalOpen = true;
  }

  function showEditPhoneNumberModal() {
    phoneNumber.isEditModalOpen = true;
  }

  function setPhoneNumberData<K extends TypePhoneNumberKeys>(data: IPhoneNumber) {
    Object.keys(data).forEach((key) => {
      const typedKey = key as K;

      phoneNumber[typedKey] = data[typedKey];
    })
  }

  function closeAndResetPhoneNumberModal<K extends TypePhoneNumberKeys>() {
    Object.keys(phoneNumberTemplate).forEach((key) => {
      const typedKey = key as K;
      
      phoneNumber[typedKey] = phoneNumberTemplate[typedKey];
    });
  }

  function getPhoneNumberDataAPI(): IAssignmentInfoDataAPI {
    return {
      type: phoneNumber.type,
      number: phoneNumber.number
    }
  }
  
  return {
    phoneNumber,
    showCreatePhoneNumberModal,
    showEditPhoneNumberModal,
    setPhoneNumberData,
    getPhoneNumberDataAPI,
    closeAndResetPhoneNumberModal
  }
})
