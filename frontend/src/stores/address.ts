import { reactive } from 'vue';
import { defineStore } from 'pinia';

import { addressTemplate } from '@/helpers/contact';
import type { IAddress, IAddressState, IAssignmentInfoDataAPI } from '@/helpers/interfaces';

type TypeAddressKeys = keyof IAddress;

export const useAddressStore = defineStore('address', () => {
  const address: IAddressState = reactive(structuredClone(addressTemplate));
  
  function showCreateAddressModal() {
    address.isCreateModalOpen = true;
  }

  function showEditAddressModal() {
    address.isEditModalOpen = true;
  }

  function setAddressData<K extends TypeAddressKeys>(data: IAddress) {
    Object.keys(data).forEach((key) => {
      const typedKey = key as K;

      address[typedKey] = data[typedKey];
    })
  }

  function closeAndResetAddressModal<K extends TypeAddressKeys>() {
    Object.keys(addressTemplate).forEach((key) => {
      const typedKey = key as K;

      address[typedKey] = addressTemplate[typedKey];
    });
  }

  function getAddressDataAPI(): IAssignmentInfoDataAPI {
    return {
      type: address.type,
      city: address.city,
      state: address.state,
      zip: address.zip,
      addressLine: address.addressLine
    }
  }
  
  return {
    address,
    showCreateAddressModal,
    showEditAddressModal,
    setAddressData,
    getAddressDataAPI,
    closeAndResetAddressModal
  }
})
