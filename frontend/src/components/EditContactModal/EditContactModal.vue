<template>
  <v-dialog 
    persistent 
    width="800" 
    scrollable
  >
    <v-card>
      <v-form v-model="contact.formModel" ref="formRef">
        <v-card-title class="position-relative">
          <h1 class="text-h5">Contact editing</h1>
        </v-card-title>
        
        <v-divider/>

        <ContactInfoCard @validate-form="validateForm" />

        <v-card-actions>
          <v-spacer/>
          <v-btn
            color="success"
            rounded="lg"
            variant="elevated"
            :disabled="!contact.formModel"
            @click="submit"
          >
            Edit
          </v-btn>

          <v-btn
            class="ml-5"
            color="error"
            rounded="lg"
            variant="elevated"
            @click="closeContactModal"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { ref, type VNodeRef } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';

  import { eventBusNames } from '@/helpers/constants';
  import { useContactStore } from '@/stores/contact';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useEditContact } from '@/hooks/useEditContact';

  import ContactInfoCard from '@/components/EditContactModal/EditContactCard.vue';

  const bus = useEventBus<string>(eventBusNames.fetchAssignment);
  const formRef: VNodeRef = ref(null);

  const { contact } = storeToRefs(useContactStore());
  const { closeAndResetContactModal } = useContactStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const { 
    editContact,
    isLoading,
    isSuccess
  } = useEditContact();

  const validateForm = () => {
    if (formRef.value) {
      formRef.value.validate();
    }
  };

  const resetAndCloseModal = () => {
    if (formRef.value) {
      formRef.value.reset();
    }
    closeAndResetContactModal();
  };

  const submit = async () => {
    await editContact();

    if (!isLoading.value && isSuccess.value) {
      resetAndCloseModal();
      setSnackbarDataAndShow("A contact is successfully edited", 'success');

      bus.emit('true');
    }
  };

  const closeContactModal = () => {
    setConfirmationDataAndShow({
      title: "Contact editing cancellation",
      content: "Do you really want to cancel the editing of the contact? " +
        "All your entered data will be lost",
      onConfirmAction: resetAndCloseModal
    });
  };
</script>