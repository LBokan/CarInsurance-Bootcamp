<template>
  <v-dialog 
    v-if="phoneNumber.isCreateModalOpen"
    v-model="phoneNumber.isCreateModalOpen"
    persistent 
    width="800" 
    scrollable
  >
    <v-card>
      <v-form v-model="phoneNumber.formModel" ref="formRef">
        <v-card-title class="position-relative">
          <h1 class="text-h5">Phone number creation</h1>
        </v-card-title>
        
        <v-divider/>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12" sm="4">
                <CustomSelect
                  v-model="phoneNumber.type"
                  :is-required="true"
                  :is-loading="isLoadingPhoneNumberTypesLabels"
                  :items="phoneNumberTypesLabels"
                  :label="'Phone number type'"
                />
              </v-col>
              <v-col cols="12" sm="8">
                <CustomTextInput
                  v-model="phoneNumber.number"
                  :rules="[rules.required, rules.phoneNumber]"
                  :is-required="true"
                  :label="'Phone number'"
                  :placeholder="'+X XXX-XXX-XXX'"
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
            :disabled="!phoneNumber.formModel"
            @click="submit"
          >
            Create
          </v-btn>

          <v-btn
            class="ml-5"
            color="error"
            rounded="lg"
            variant="elevated"
            @click="closeModal"
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
  import { usePhoneNumberStore } from '@/stores/phoneNumber';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useGetPhoneNumberTypes } from '@/hooks/useGetPhoneNumberTypes';
  import { useCreatePhoneNumber } from '@/hooks/useCreatePhoneNumber';

  import CustomSelect from '@/components/UI/CustomSelect.vue';
  import CustomTextInput from '@/components/UI/CustomTextInput.vue';

  const bus = useEventBus<boolean>(eventBusNames.fetchAssignment);
  const formRef: VNodeRef = ref(null);

  const { phoneNumber } = storeToRefs(usePhoneNumberStore());
  const { closeAndResetPhoneNumberModal } = usePhoneNumberStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const {
    createPhoneNumber,
    isLoading: isLoadingCreatePhoneNumber,
    isSuccess: isSuccessCreatePhoneNumber
  } = useCreatePhoneNumber();

  const {
    getPhoneNumberTypes,
    data: phoneNumberTypesLabels,
    isLoading: isLoadingPhoneNumberTypesLabels
  } = useGetPhoneNumberTypes();

  const validateForm = () => {
    if (formRef.value) {
      formRef.value.validate();
    }
  };

  onMounted(async () => {
    await getPhoneNumberTypes();
    validateForm();
  })

  const resetAndCloseModal = () => {
    if (formRef.value) {
      formRef.value.reset();
    }
    closeAndResetPhoneNumberModal();
  };

  const submit = async () => {
    await createPhoneNumber();

    if (!isLoadingCreatePhoneNumber.value && isSuccessCreatePhoneNumber.value) {
      resetAndCloseModal();
      setSnackbarDataAndShow("A phone number is successfully created", 'success');

      bus.emit(true);
    }
  };

  const closeModal = () => {
    setConfirmationDataAndShow({
      title: "Phone number creation cancellation",
      content: "Do you really want to cancel the creation of the phone number? " +
        "All your entered data will be lost",
      onConfirmAction: resetAndCloseModal
    });
  };
</script>