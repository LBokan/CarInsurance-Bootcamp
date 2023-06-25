<template>
  <v-dialog 
    persistent 
    width="800" 
    scrollable
  >
    <v-card>
      <v-form v-model="contact.formModel" ref="formRef">
        <v-card-title class="position-relative">
          <h1 class="text-h5">Contact creation</h1>
        </v-card-title>
        
        <v-divider/>

        <CreateContactCard @validate-form="validateForm" />

        <v-card-actions>
          <v-spacer/>
          <v-btn
            color="success"
            rounded="lg"
            variant="elevated"
            :disabled="!contact.formModel"
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
  import { ref, type VNodeRef } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';

  import { eventBusNames } from '@/helpers/constants';
  import { useContactStore } from '@/stores/contact';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useCreateContact } from '@/hooks/useCreateContact';

  import CreateContactCard from '@/components/CreateContactModal/CreateContactCard.vue';

  const bus = useEventBus<string>(eventBusNames.fetchAssignment);
  const formRef: VNodeRef = ref(null);

  const { contact } = storeToRefs(useContactStore());
  const { closeAndResetContactModal } = useContactStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const { 
    createContact,
    isLoading,
    isSuccess
  } = useCreateContact();

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
    await createContact();

    if (!isLoading.value && isSuccess.value) {
      resetAndCloseModal();
      setSnackbarDataAndShow("A contact is successfully created", 'success');

      bus.emit('true');
    }
  };

  const closeModal = () => {
    setConfirmationDataAndShow({
      title: "Contact creation cancellation",
      content: "Do you really want to cancel the creation of the contact? " +
        "All your entered data will be lost",
      onConfirmAction: resetAndCloseModal
    });
  };
</script>