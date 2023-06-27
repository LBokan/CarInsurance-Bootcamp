<template>
  <v-card-text>
    <v-container>
      <v-row>
        <v-col cols="12">
          <CustomSelect
            v-model="contact.type"
            :is-required="true"
            :is-loading="isLoadingContactTypesLabels && !isSuccessContactTypesLabels"
            :items="getAvailableContactTypes"
            :label="'Contact type'"
          />
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

      <v-row class="pb-5">
        <v-col cols="12">
          <CustomTextInput
            v-model="contact.email"
            :rules="[rules.required, rules.email]"
            :is-required="true"
            :label="'Email'"
          />
        </v-col>
      </v-row>
      <v-divider />

      <v-row
        v-for="phoneData in contact.phoneNumbers"
        :key="phoneData.id"
        :class="phoneData.id ? '' : 'pt-7'"
      >
        <v-col cols="12" sm="4">
          <CustomSelect
            v-model="phoneData.type"
            :is-required="true"
            :is-loading="isLoadingPhoneNumberTypesLabels && !isSuccessPhoneNumberTypesLabels"
            :items="phoneNumberTypesLabels"
            :label="'Phone number type'"
          />
        </v-col>
        <v-col cols="12" :sm="phoneData.id ? 7 : 8">
          <CustomTextInput
            v-model="phoneData.number"
            :rules="[rules.required, rules.phoneNumber]"
            :is-required="true"
            :label="'Phone number'"
            :placeholder="'+X XXX-XXX-XXX'"
          />
        </v-col>
        <v-col v-if="phoneData.id" cols="12" sm="1">
          <v-btn
            class="close-btn"
            color="error"
            density="compact"
            icon="mdi-close"
            @click="openConfirmationModal('phoneNumber', phoneData.id)"
          />
        </v-col>
      </v-row>
      <v-row class="pb-5">
        <v-col cols="12">
          <v-btn
            color="info"
            variant="elevated"
            @click="addInfo('phoneNumber')"
          >
            + Add phone number
          </v-btn>
        </v-col>
      </v-row>
      <v-divider />

      <v-row
        v-for="addressData in contact.addresses"
        :key="addressData.id"
        :class="addressData.id ? '' : 'pt-7'"
      >
        <v-col cols="12" sm="3">
          <CustomSelect 
            v-model="addressData.type"
            :is-required="true"
            :is-loading="isLoadingAddressTypesLabels && !isSuccessAddressTypesLabels"
            :items="addressTypesLabels"
            :label="'Address type'"
          />
        </v-col>
        <v-col cols="12" :sm="addressData.id ? 5 : 6">
          <CustomTextInput
            v-model="addressData.city"
            :rules="[rules.required]"
            :is-required="true"
            :label="'City'"
          />
        </v-col>
        <v-col cols="12" sm="3">
          <CustomAutocomplete 
            v-model="addressData.state"
            :is-required="true"
            :items="statesCodesLabels"
            :label="'State'"
            :error="!isStateValue(addressData.id)"
            :error-messages="!isStateValue(addressData.id) ? 
            ['Value is required'] : []"
          />
        </v-col>
        <v-col v-if="addressData.id" cols="12" sm="1">
          <v-btn
            class="close-btn"
            color="error"
            density="compact"
            icon="mdi-close"
            @click="openConfirmationModal('address', addressData.id)"
          />
        </v-col>
        <v-col cols="12" sm="8">
          <CustomTextInput
            v-model="addressData.addressLine"
            :rules="[rules.required]"
            :is-required="true"
            :label="'Address line'"
          />
        </v-col>
        <v-col cols="12" sm="4">
          <CustomTextInput
            v-model="addressData.zip"
            :rules="[rules.required, rules.zipCode]"
            :is-required="true"
            :label="'Zip code'"
            :placeholder="'XXXXX-XXXX'"
          />
        </v-col>
      </v-row>
      <v-row class="pb-7">
        <v-col cols="12">
          <v-btn
            color="info"
            variant="elevated"
            @click="addInfo('address')"
          >
            + Add address
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-card-text>
</template>

<script setup lang="ts">
  import { computed, onMounted } from 'vue';
  import { storeToRefs } from 'pinia';

  import { rules } from '@/helpers/rulesRegex';
  import { statesCodesLabels, getContactDataPropName } from '@/helpers/contact';
  import { useContactStore } from '@/stores/contact';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useGetContactTypes } from '@/hooks/useGetContactTypes';
  import { useGetPhoneNumberTypes } from '@/hooks/useGetPhoneNumberTypes';
  import { useGetAddressTypes } from '@/hooks/useGetAddressTypes';
  
  import CustomTextInput from '@/components/UI/CustomTextInput.vue';
  import CustomSelect from '@/components/UI/CustomSelect.vue';
  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';
  
  const emits = defineEmits(['validate-form']);

  const { contact } = storeToRefs(useContactStore());
  const { 
    addContactInfoData,
    removePhoneNumberData,
    removeAddressData
  } = useContactStore();
  const { assignment } = storeToRefs(useAssignmentStore());
  const { setConfirmationDataAndShow } = useConfirmationStore();

  const {
    getContactTypes,
    data: contactTypesLabels,
    isLoading: isLoadingContactTypesLabels,
    isSuccess: isSuccessContactTypesLabels
  } = useGetContactTypes();
  
  const {
    getPhoneNumberTypes,
    data: phoneNumberTypesLabels,
    isLoading: isLoadingPhoneNumberTypesLabels,
    isSuccess: isSuccessPhoneNumberTypesLabels
  } = useGetPhoneNumberTypes();

  const {
    getAddressTypes,
    data: addressTypesLabels,
    isLoading: isLoadingAddressTypesLabels,
    isSuccess: isSuccessAddressTypesLabels
  } = useGetAddressTypes();
  
  onMounted(async () => {
    await getContactTypes();
    await getPhoneNumberTypes();
    await getAddressTypes();

    emits('validate-form');
  })

  const getAvailableContactTypes = computed(() => {
    const excludedTypes = ['owner', 'driver'];
    
    return contactTypesLabels.filter(contactType => {
      const hasExcludedType = excludedTypes.some(excludedType =>
        contactType.toLowerCase().includes(excludedType)
      );

      const hasContactWithExcludedType = assignment.value.contacts.some(contact => 
        contact.type?.includes(contactType) && hasExcludedType
      );

      return !hasContactWithExcludedType;
    });
  });

  const isStateValue = (addressId: string | number) => {
    const addressIndex = contact.value.addresses.findIndex(address => 
      address.id == addressId
    );

    return !!contact.value.addresses[addressIndex]?.state;
  };

  const addInfo = (infoType: string) => {
    const contactDataPropName = getContactDataPropName(infoType);

    if (contactDataPropName) {
      switch(contactDataPropName) {
        case 'phoneNumbers':
          addContactInfoData(
            contactDataPropName,
            {
              id: crypto.randomUUID(),
              type: 'Mobile',
              number: ''
            }
          );
          emits('validate-form');
          break;

        case 'addresses':
          addContactInfoData(
            contactDataPropName,
            {
              id: crypto.randomUUID(),
              type: 'Home',
              city: '',
              state: '',
              zip: '',
              addressLine: ''
            }
          );
          emits('validate-form');
          break;
      }
    }
  };

  const openConfirmationModal = (
    infoType: string, 
    infoId: string | number | null = null
  ) => {
    switch(infoType) {
      case 'phoneNumber':
        setConfirmationDataAndShow({
          title: "Phone number deletion",
          content: "Do you really want to remove this phone number?",
          onConfirmAction: removePhoneNumberData,
          args: [infoId]
        });
        break;

      case 'address':
        setConfirmationDataAndShow({
          title: "Address deletion",
          content: "Do you really want to remove this address?",
          onConfirmAction: removeAddressData,
          args: [infoId]
        });
        break;
    }
  };
</script>