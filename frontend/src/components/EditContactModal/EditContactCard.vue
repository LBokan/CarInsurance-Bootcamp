<template>
  <v-card-text>
    <v-container>
      <v-row>
        <v-col cols="12">
          <h3 class="text-subtitle-1 font-weight-bold">{{ contact.type }}</h3>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" sm="6">
          <CustomTextInput
            v-model="contact.firstName"
            :rules="[rules.required]"
            :is-required="true"
            :label="'First name'"
          />
        </v-col>
        <v-col cols="12" sm="6">
          <CustomTextInput
            v-model="contact.lastName"
            :rules="[rules.required]"
            :is-required="true"
            :label="'Last name'"
          />
        </v-col>
      </v-row>

      <v-row class="pb-2">
        <v-col cols="12">
          <CustomTextInput
            v-model="contact.email"
            :rules="[rules.required, rules.email]"
            :is-required="true"
            :label="'Email'"
          />
        </v-col>
      </v-row>
      <v-divider class="my-2" :thickness="4" />

      <v-row>
        <v-col cols="12" sm="9">
          <h4 class="text-subtitle-1 font-weight-bold">Phone numbers:</h4>
        </v-col>
      </v-row>

      <v-row
        v-for="phoneData in contact.phoneNumbers"
        :key="phoneData.id"
        align="center"
      >
        <v-col cols="12" sm="9">
          <p class="text-subtitle-1">{{ phoneData.type }}: {{ phoneData.number }}</p>
        </v-col>
        <v-col class="text-right" cols="12" sm="3">
          <v-btn
            color="orange"
            density="comfortable"
            icon="mdi-pencil"
            @click="openEditPhoneNumberModal(phoneData)"
          > 
            <v-icon color="white" icon="mdi-pencil" />
            <v-tooltip
              activator="parent"
              text="Edit phone number"
              location="bottom"
            />
          </v-btn>

          <v-btn
            v-if="contact.phoneNumbers.length > 1"
            class="ml-2"
            color="error"
            density="comfortable"
            icon="mdi-delete"
            @click="openDeletePhoneNumberModal(phoneData)"
          >
            <v-icon color="white" icon="mdi-delete" />
            <v-tooltip
              activator="parent"
              text="Delete phone number"
              location="bottom"
            />
          </v-btn>
        </v-col>
        <v-divider />
      </v-row>
      <v-row class="pb-5">
        <v-col cols="12">
          <v-btn
            color="info"
            variant="elevated"
            @click="showCreatePhoneNumberModal"
          >
            + Add phone number
          </v-btn>
        </v-col>
      </v-row>
      <v-divider class="my-2" :thickness="4" />

      <v-row>
        <v-col cols="12" sm="9">
          <h4 class="text-subtitle-1 font-weight-bold">Addresses:</h4>
        </v-col>
      </v-row>

      <v-row
        v-for="addressData in contact.addresses"
        :key="addressData.id"
        align="center"
      >
        <v-col cols="12" sm="9">
          <p>
            <span class="text-subtitle-2">Type:</span>
            {{ addressData.type }}
          </p>
          <p>
            <span class="text-subtitle-2">Address line:</span>
            {{ addressData.addressLine }}
          </p>
          <p>
            <span class="text-subtitle-2">City, state, ZIP-code:</span>
            {{ addressData.city }}, {{ addressData.state }}, {{ addressData.zip }}
          </p>
        </v-col>
        <v-col class="text-right" cols="12" sm="3">
          <v-btn
            color="orange"
            density="comfortable"
            icon="mdi-pencil"
            @click="openEditAddressModal(addressData)"
          > 
            <v-icon color="white" icon="mdi-pencil" />
            <v-tooltip
              activator="parent"
              text="Edit address"
              location="bottom"
            />
          </v-btn>

          <v-btn
            v-if="contact.addresses.length > 1"
            class="ml-2"
            color="error"
            density="comfortable"
            icon="mdi-delete"
            @click="openDeleteAddressModal(addressData)"
          >
            <v-icon color="white" icon="mdi-delete" />
            <v-tooltip
              activator="parent"
              text="Delete address"
              location="bottom"
            />
          </v-btn>
        </v-col>
        <v-divider />
      </v-row>
      <v-row class="pb-7">
        <v-col cols="12">
          <v-btn
            color="info"
            variant="elevated"
            @click="showCreateAddressModal"
          >
            + Add address
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-card-text>
</template>

<script setup lang="ts">
  import { onMounted } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';
  
  import { eventBusNames } from '@/helpers/constants';
  import { rules } from '@/helpers/rulesRegex';
  import { useContactStore } from '@/stores/contact';
  import { usePhoneNumberStore } from '@/stores/phoneNumber';
  import { useAddressStore } from '@/stores/address';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useDeletePhoneNumber } from '@/hooks/useDeletePhoneNumber';
  import { useDeleteAddress } from '@/hooks/useDeleteAddress';

  import CustomTextInput from '@/components/UI/CustomTextInput.vue';

  import type { IAddress, IPhoneNumber } from '@/helpers/interfaces';

  const bus = useEventBus<string>(eventBusNames.fetchAssignment);
  const emits = defineEmits(['validate-form']);

  const { contact } = storeToRefs(useContactStore());
  const { 
    showCreateAddressModal,
    showEditAddressModal,
    setAddressData,
    closeAndResetAddressModal
  } = useAddressStore();
  const {
    showCreatePhoneNumberModal,
    showEditPhoneNumberModal,
    setPhoneNumberData,
    closeAndResetPhoneNumberModal
  } = usePhoneNumberStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const {
    deletePhoneNumber,
    isLoading: isLoadingDeletePhoneNumber,
    isSuccess: isSuccessDeletePhoneNumber
  } = useDeletePhoneNumber();

  const {
    deleteAddress,
    isLoading: isLoadingDeleteAddress,
    isSuccess: isSuccessDeleteAddress
  } = useDeleteAddress();

  onMounted(async () => {
    emits('validate-form');
  })

  const openEditPhoneNumberModal = (data: IPhoneNumber) => {
    setPhoneNumberData(data);

    showEditPhoneNumberModal();
  };

  const openEditAddressModal = (data: IAddress) => {
    setAddressData(data);

    showEditAddressModal();
  };

  const deletePhoneNumberAndCloseModal = async () => {
    await deletePhoneNumber();

    if (!isLoadingDeletePhoneNumber.value && isSuccessDeletePhoneNumber.value) {
      closeAndResetPhoneNumberModal();
      setSnackbarDataAndShow("A phone number is successfully deleted", 'success');

      bus.emit('true');
    }
  }

  const deleteAddressAndCloseModal = async () => {
    await deleteAddress();

    if (!isLoadingDeleteAddress.value && isSuccessDeleteAddress.value) {
      closeAndResetAddressModal();
      setSnackbarDataAndShow("An address is successfully deleted", 'success');

      bus.emit('true');
    }
  }

  const openDeletePhoneNumberModal = (data: IPhoneNumber) => {
    setPhoneNumberData(data);

    setConfirmationDataAndShow({
      title: "Phone number deletion",
      content: "Do you really want to remove this phone number?",
      onConfirmAction: deletePhoneNumberAndCloseModal
    });
  }

  const openDeleteAddressModal = (data: IAddress) => {
    setAddressData(data);

    setConfirmationDataAndShow({
      title: "Address deletion",
      content: "Do you really want to remove this address?",
      onConfirmAction: deleteAddressAndCloseModal
    });
  }
</script>