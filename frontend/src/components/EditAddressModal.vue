<template>
  <v-dialog 
    v-if="address.isEditModalOpen"
    v-model="address.isEditModalOpen"
    persistent 
    width="800" 
    scrollable
  >
    <v-card>
      <v-form v-model="address.formModel" ref="formRef">
        <v-card-title class="position-relative">
          <h1 class="text-h5">Address editing</h1>
        </v-card-title>
        
        <v-divider/>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="3">
                <CustomSelect 
                  v-model="address.type"
                  :is-required="true"
                  :is-loading="isLoadingAddressTypesLabels"
                  :items="addressTypesLabels"
                  :label="'Address type'"
                />
              </v-col>
              <v-col cols="12" :sm="6">
                <CustomTextInput
                  v-model="address.city"
                  :rules="[rules.required]"
                  :is-required="true"
                  :label="'City'"
                />
              </v-col>
              <v-col cols="12" sm="3">
                <CustomAutocomplete 
                  v-model="address.state"
                  :is-required="true"
                  :items="statesCodesLabels"
                  :label="'State'"
                  :error="!isStateValue"
                  :error-messages="!isStateValue() ? 
                  ['Value is required'] : []"
                />
              </v-col>
              <v-col cols="12" sm="8">
                <CustomTextInput
                  v-model="address.addressLine"
                  :rules="[rules.required]"
                  :is-required="true"
                  :label="'Address line'"
                />
              </v-col>
              <v-col cols="12" sm="4">
                <CustomTextInput
                  v-model="address.zip"
                  :rules="[rules.required, rules.zipCode]"
                  :is-required="true"
                  :label="'Zip code'"
                  :placeholder="'XXXXX-XXXX'"
                />
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer/>
          <v-btn
            color="success"
            rounded="lg"
            variant="elevated"
            :disabled="!address.formModel"
            @click="submit"
          >
            Edit
          </v-btn>

          <v-btn
            class="ml-5"
            color="error"
            rounded="lg"
            variant="elevated"
            @click="closeAddressModal"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { onMounted, ref, type VNodeRef } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';
  
  import { eventBusNames } from '@/helpers/constants';
  import { rules } from '@/helpers/rulesRegex';
  import { statesCodesLabels } from '@/helpers/contact';

  import { useAddressStore } from '@/stores/address';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useEditAddress } from '@/hooks/useEditAddress';
  import { useGetAddressTypes } from '@/hooks/useGetAddressTypes';

  import CustomSelect from '@/components/UI/CustomSelect.vue';
  import CustomTextInput from '@/components/UI/CustomTextInput.vue';
  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';

  const bus = useEventBus<boolean>(eventBusNames.fetchAssignment);
  const formRef: VNodeRef = ref(null);
  
  const { address } = storeToRefs(useAddressStore());
  const { closeAndResetAddressModal } = useAddressStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const {
    editAddress,
    isLoading: isLoadingEditAddress,
    isSuccess: isSuccessEditAddress
  } = useEditAddress();

  const {
    getAddressTypes,
    data: addressTypesLabels,
    isLoading: isLoadingAddressTypesLabels
  } = useGetAddressTypes();

  const validateForm = () => {
    if (formRef.value) {
      formRef.value.validate();
    }
  };

  onMounted(async () => {
    await getAddressTypes();
    validateForm();
  })
  
  const isStateValue = () => {
    return !!address.value.state;
  };
  
  const resetAndCloseModal = () => {
    if (formRef.value) {
      formRef.value.reset();
    }
    closeAndResetAddressModal();
  };

  const submit = async () => {
    await editAddress();

    if (!isLoadingEditAddress.value && isSuccessEditAddress.value) {
      resetAndCloseModal();
      setSnackbarDataAndShow("An address is successfully edited", 'success');

      bus.emit(true);
    }
  };

  const closeAddressModal = () => {
    setConfirmationDataAndShow({
      title: "Address editing cancellation",
      content: "Do you really want to cancel the editing of the address? " +
        "All your entered data will be lost",
      onConfirmAction: resetAndCloseModal
    });
  };
</script>