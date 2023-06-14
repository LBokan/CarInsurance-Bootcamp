<template>
  <v-card-text>
    <v-container 
      v-for="contactData in assignment.contacts"
      :key="contactData.id"
    >
      <v-row v-if="contactData.id == '0'">
        <v-col cols="12">
          <h2 class="text-subtitle-1">{{ contactData.type }}</h2>
        </v-col>
      </v-row>
      <v-row v-else>
        <v-col cols="12" sm="11">
          <CustomSelect 
            v-model="contactData.type"
            :is-required="true"
            :items="getAvailableContactTypes"
            :label="'Contact type'"
          />
        </v-col>
        <v-col
          v-if="contactData.id !== '0'"
          cols="12"
          sm="1"
        >
          <v-btn
            class="close-btn"
            color="error"
            density="compact"
            icon="mdi-close"
            @click="openConfirmationModal(contactData.id, 'contact')"
          />
        </v-col>
      </v-row>

      <v-row>
        <v-col cols="12" sm="6">
          <CustomTextInput
            v-model="contactData.firstName"
            :rules="[rules.required]"
            :is-required="true"
            :label="'First name'"
          />
        </v-col>
        <v-col cols="12" sm="6">
          <CustomTextInput
            v-model="contactData.lastName"
            :rules="[rules.required]"
            :is-required="true"
            :label="'Last name'"
          />
        </v-col>
      </v-row>

      <v-row class="pb-5">
        <v-col cols="12">
          <CustomTextInput
            v-model="contactData.email"
            :rules="[rules.required, rules.email]"
            :is-required="true"
            :label="'Email'"
          />
        </v-col>
      </v-row>
      <v-divider />

      <v-row
        v-for="phoneData in contactData.phoneNumbers"
        :key="phoneData.id"
        :class="phoneData.id !== '0' ? '' : 'pt-7'"
      >
        <v-col cols="12" sm="4">
          <CustomSelect
            v-model="phoneData.type"
            :is-required="true"
            :items="phoneNumberTypesLabels"
            :label="'Phone number type'"
          />
        </v-col>
        <v-col cols="12" :sm="phoneData.id !== '0' ? 7 : 8">
          <CustomTextInput
            v-model="phoneData.number"
            :rules="[rules.required, rules.phoneNumber]"
            :is-required="true"
            :label="'Phone number'"
            :placeholder="'+X XXX-XXX-XXX'"
          />
        </v-col>
        <v-col
          v-if="phoneData.id !== '0'"
          cols="12"
          sm="1"
        >
          <v-btn
            class="close-btn"
            color="error"
            density="compact"
            icon="mdi-close"
            @click="openConfirmationModal(
                contactData.id, 'phoneNumber', phoneData.id
              )"
          />
        </v-col>
      </v-row>
      <v-row class="pb-5">
        <v-col cols="12">
          <v-btn
            color="info"
            variant="elevated"
            @click="addInfo(contactData.id, 'phoneNumber')"
          >
            + Add phone number
          </v-btn>
        </v-col>
      </v-row>
      <v-divider />

      <v-row
        v-for="addressData in contactData.addresses"
        :key="addressData.id"
        :class="addressData.id !== '0' ? '' : 'pt-7'"
      >
        <v-col cols="12" sm="3">
          <CustomSelect 
            v-model="addressData.type"
            :is-required="true"
            :items="addressesTypesLabels"
            :label="'Address type'"
          />
        </v-col>
        <v-col cols="12" :sm="addressData.id !== '0' ? 5 : 6">
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
            :error="!isStateValue(contactData.id, addressData.id)"
            :error-messages="!isStateValue(contactData.id, addressData.id) ? 
            ['Value is required'] : []"
          />
        </v-col>
        <v-col
          v-if="addressData.id !== '0'"
          cols="12"
          sm="1"
        >
          <v-btn
            class="close-btn"
            color="error"
            density="compact"
            icon="mdi-close"
            @click="openConfirmationModal(
                contactData.id, 'address', addressData.id
              )"
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
            @click="addInfo(contactData.id, 'address')"
          >
            + Add address
          </v-btn>
        </v-col>
      </v-row>
      <v-divider 
        thickness="5"
        color="primary"
      />
    </v-container>

    <v-container>
      <v-row>
        <v-col cols="12">
          <v-btn
            color="primary"
            variant="elevated"
            @click="addContact"
          >
            + Add new contact
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-card-text>
</template>

<script setup lang="ts">
  import { defineEmits, computed, onMounted } from 'vue';
  import { storeToRefs } from 'pinia';

  import { rules } from '@/utils/rulesRegex';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useConfirmationStore } from '@/stores/confirmation';

  import CustomTextInput from '@/components/UI/CustomTextInput.vue';
  import CustomSelect from '@/components/UI/CustomSelect.vue';
  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';

  import { 
    contactTypesLabels,
    phoneNumberTypesLabels, 
    addressesTypesLabels, 
    statesCodesLabels 
  } from '@/helpers/assignmentModal';

  const { assignment } = storeToRefs(useAssignmentStore());
  const { 
    addContactData, 
    addContactInfoData,
    removeContactData,
    removePhoneNumberData,
    removeAddressData
  } = useAssignmentStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();

  const emits = defineEmits(['validate-form']);

  onMounted(() => {
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
  })

  const getAssignmentDataPropName = (infoType: string) => {
    const infoTypeInLowerCase = infoType.toLowerCase();

    if (infoTypeInLowerCase.includes('phone')) {
      return 'phoneNumbers';
    } else if (infoTypeInLowerCase.includes('address')) {
      return 'addresses';
    }

    return '';
  }

  const getContactIndex = (contactId: string) => {
    return assignment.value.contacts.findIndex(contact => contact.id == contactId);
  }

  const isStateValue = (contactId: string, addressId: string) => {
    const contactIndex = getContactIndex(contactId);
    const addressIndex = assignment.value.contacts[contactIndex].addresses.findIndex(address => 
      address.id == addressId
    );

    return !!assignment.value.contacts[contactIndex]
              .addresses[addressIndex]?.state;
  }

  const addContact = () => {
    addContactData({
      id: crypto.randomUUID(),
      type: getAvailableContactTypes.value[0],
      firstName: '',
      lastName: '',
      email: '',
      phoneNumbers: [{
        id: '0',
        type: 'Mobile',
        number: ''
      }],
      addresses: [{
        id: '0',
        type: 'Home',
        city: '',
        state: null,
        zip: '',
        addressLine: ''
      }]
    });

    emits('validate-form');
  }

  const addInfo = (contactId: string, infoType: string) => {
    const contactIndex = getContactIndex(contactId);
    const assignmentDataPropName = getAssignmentDataPropName(infoType);

    if (contactIndex !== -1 && assignmentDataPropName) {
      switch(assignmentDataPropName) {
        case 'phoneNumbers':
          addContactInfoData(
            contactIndex,
            assignmentDataPropName,
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
            contactIndex,
            assignmentDataPropName,
            {
              id: crypto.randomUUID(),
              type: 'Home',
              city: '',
              state: null,
              zip: '',
              addressLine: ''
            }
          );
          emits('validate-form');
          break;
      }
    }
  }

  const openConfirmationModal = (
    contactId: string, 
    infoType: string, 
    infoId: string | null = null
  ) => {
    const contactIndex = getContactIndex(contactId);

    switch(infoType) {
      case 'phoneNumber':
        setConfirmationDataAndShow({
          title: "Phone number deletion",
          content: "Do you really want to remove this phone number?",
          onConfirmAction: removePhoneNumberData,
          args: [contactIndex, infoId]
        });
        break;

      case 'address':
        setConfirmationDataAndShow({
          title: "Address deletion",
          content: "Do you really want to remove this address?",
          onConfirmAction: removeAddressData,
          args: [contactIndex, infoId]
        });
        break;
      
      case 'contact':
        setConfirmationDataAndShow({
          title: "Contact deletion",
          content: "Do you really want to remove this contact?",
          onConfirmAction: removeContactData,
          args: [contactIndex]
        });
        break;
    }
  }
</script>