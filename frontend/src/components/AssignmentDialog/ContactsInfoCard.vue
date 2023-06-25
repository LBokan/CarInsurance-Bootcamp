<template>
  <v-col cols="12" sm="6">
    <v-window v-model="contactPage" show-arrows="hover">
      <v-window-item v-for="contact in assignment.contacts" :key="contact.id">
        <v-card position="relative" height="380" class="contact-info-wrapper">
          <div class="info-wrapper">
            <v-chip class="mr-5" color="blue">
              {{ contactPage + 1 }} / {{ assignment.contacts.length }}
            </v-chip>

            <v-btn
              v-if="userState.role == ROLES.insurance"
              class="ml-5"
              color="orange"
              density="comfortable"
              icon="mdi-pencil"
              @click="openEditContactModal(contact)"
            > 
              <v-icon color="white" icon="mdi-pencil" />
              <v-tooltip
                activator="parent"
                text="Edit contact"
                location="bottom"
              />
            </v-btn>

            <v-btn
              v-if="userState.role == ROLES.insurance && !isExcludedContactTypes(contact.type)"
              class="ml-2"
              color="error"
              density="comfortable"
              icon="mdi-delete"
              @click="openDeleteContactModal(contact)"
            >
              <v-icon color="white" icon="mdi-delete" />
              <v-tooltip
                activator="parent"
                text="Delete contact"
                location="bottom"
              />
            </v-btn>

            <v-btn
              v-if="userState.role == ROLES.insurance"
              class="mx-5"
              color="success"
              density="comfortable"
              icon="mdi-plus"
              @click="showCreateContactModal"
            >
              <v-icon color="white" icon="mdi-plus" />
              <v-tooltip
                activator="parent"
                text="Create new contact"
                location="bottom"
              />
            </v-btn>
          </div>

          <h3 class="text-subtitle-1 font-weight-bold">{{ contact.type }}</h3>

          <p class="mt-5">
            <span class="text-subtitle-2">First name:</span>
            {{ contact.firstName }}
          </p>
          <p>
            <span class="text-subtitle-2">Last name:</span>
            {{ contact.lastName }}
          </p>
          <p>
            <span class="text-subtitle-2">Email:</span>
            {{ contact.email }}
          </p>

          <v-divider class="my-2" :thickness="4" />

          <p class="text-subtitle-2 font-weight-bold">Phone numbers:</p>
          <p v-for="phoneNumber in contact.phoneNumbers" :key="phoneNumber.id">
            <span class="text-subtitle-2">{{ phoneNumber.type }}:</span>
            {{ phoneNumber.number }}
          </p>

          <v-divider class="my-2" :thickness="4" />

          <p class="text-subtitle-2 font-weight-bold">Addresses:</p>
          <div v-for="address in contact.addresses" :key="address.id">
            <v-divider v-if="address.id != contact.addresses[0].id" class="my-2" />

            <p>
              <span class="text-subtitle-2">Type:</span>
              {{ address.type }}
            </p>
            <p>
              <span class="text-subtitle-2">Address line:</span>
              {{ address.addressLine }}
            </p>
            <p>
              <span class="text-subtitle-2">City, state, ZIP-code:</span>
              {{ address.city }}, {{ address.state }}, {{ address.zip }}
            </p>
          </div>
        </v-card>
      </v-window-item>
    </v-window>
  </v-col>
</template>

<script setup lang="ts">
  import { ref } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';

  import { eventBusNames, ROLES } from '@/helpers/constants';
  import { useUserStore } from '@/stores/user';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useContactStore } from '@/stores/contact';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useDeleteContact } from '@/hooks/useDeleteContact';
  import { useSnackbarStore } from '@/stores/snackbar';
  
  import type { IContact } from '@/helpers/interfaces';
  
  const bus = useEventBus<string>(eventBusNames.fetchAssignment);
  const contactPage = ref(0);

  const { userState } = storeToRefs(useUserStore());
  const { assignment } = storeToRefs(useAssignmentStore());
  const { 
    showCreateContactModal, 
    showEditContactModal, 
    setContactData, 
    closeAndResetContactModal 
  } = useContactStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();

  const {
    deleteContact,
    isLoading: isLoadingDeleteContact,
    isSuccess: isSuccessDeleteContact
  } = useDeleteContact();

  const isExcludedContactTypes = (contactType: string) => {
    return contactType.toLowerCase().includes('owner');
  };

  const openEditContactModal = (data: IContact) => {
    setContactData(data);
    showEditContactModal();
  };

  const deleteContactAndCloseModal = async () => {
    await deleteContact();

    if (!isLoadingDeleteContact.value && isSuccessDeleteContact.value) {
      closeAndResetContactModal();
      setSnackbarDataAndShow("A contact is successfully deleted", 'success');

      contactPage.value -= 1;

      bus.emit('true');
    }
  };

  const openDeleteContactModal = (data: IContact) => {
    setContactData(data);

    setConfirmationDataAndShow({
      title: "Contact deletion",
      content: "Do you really want to remove this contact?",
      onConfirmAction: deleteContactAndCloseModal
    });
  }
</script>

<style scoped>
  .contact-info-wrapper {
    overflow-y: auto;
  }

  .info-wrapper {
    position: absolute;
    top: 0;
    right: 0;
    display: flex;
    align-items: center;
  }
</style>