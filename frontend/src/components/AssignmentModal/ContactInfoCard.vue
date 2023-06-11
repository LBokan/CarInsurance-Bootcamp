<template>
  <v-card-text>
    <v-container 
      v-for="contactData in assignmentState.contactsInfo"
      :key="contactData.id"
    >
      <v-row v-if="!contactData.id">
        <v-col
          cols="12"
        >
          <p class="text-subtitle-1">{{ contactData.type }}</p>
        </v-col>
      </v-row>
      <v-row v-else>
        <v-col
          cols="12"
          sm="11"
        >
          <CustomSelect 
            v-model="contactData.type"
            :items="getContactTypesLabels"
            :label="'Contact type*'"
          />
        </v-col>
        <v-col
          v-if="contactData.id"
          cols="12"
          sm="1"
        >
          <v-btn
            style="transform: translateY(50%);"
            color="error"
            density="compact"
            icon="mdi-close"
            @click="() => openConfirmationModal(contactData.id, 'contact')"
          />
        </v-col>
      </v-row>

      <v-row>
        <v-col
          cols="12"
          sm="6"
        >
          <CustomInput
            v-model="contactData.firstName"
            :rules="[rules.required]"
            :is-required="true"
            :label="'First name*'"
          />
        </v-col>
        <v-col
          cols="12"
          sm="6"
        >
          <CustomInput
            v-model="contactData.lastName"
            :rules="[rules.required]"
            :is-required="true"
            :label="'Last name*'"
          />
        </v-col>
      </v-row>

      <v-row class="pb-5">
        <v-col cols="12">
          <CustomInput
            v-model="contactData.email"
            :rules="[rules.required, rules.email]"
            :is-required="true"
            :label="'Email*'"
          />
        </v-col>
      </v-row>
      <v-divider />

      <v-row
        v-for="phoneData in contactData.phoneNumbers"
        :key="phoneData.id"
        :class="phoneData.id ? '' : 'pt-7'"
      >
        <v-col 
          cols="12"
          sm="4"
        >
          <CustomSelect
            v-model="phoneData.type"
            :items="phoneNumberTypesLabels"
            :label="'Phone number type*'"
          />
        </v-col>
        <v-col 
          cols="12"
          :sm="phoneData.id ? 7 : 8"
        >
          <CustomInput
            v-model="phoneData.number"
            :rules="[rules.required, rules.phoneNumber]"
            :is-required="true"
            :label="'Phone number*'"
            :placeholder="'+X XXX-XXX-XXX'"
          />
        </v-col>
        <v-col
          v-if="phoneData.id"
          cols="12"
          sm="1"
        >
          <v-btn
            style="transform: translateY(50%);"
            color="error"
            density="compact"
            icon="mdi-close"
            @click="() => openConfirmationModal(contactData.id, 'phoneNumber', phoneData.id)"
          />
        </v-col>
      </v-row>
      <v-row class="pb-5">
        <v-col cols="12">
          <v-btn
            color="info"
            variant="elevated"
            @click="() => addInfo(contactData.id, 'phoneNumber')"
          >
            + Add phone number
          </v-btn>
        </v-col>
      </v-row>
      <v-divider />

      <v-row
        v-for="addressData in contactData.addresses"
        :key="addressData.id"
        :class="addressData.id ? '' : 'pt-7'"
      >
        <v-col 
          cols="12"
          sm="3"
        >
          <CustomSelect 
            v-model="addressData.type"
            :items="addressesTypesLabels"
            :label="'Address type*'"
          />
        </v-col>
        <v-col 
          cols="12"
          :sm="addressData.id ? 5 : 6"
        >
          <CustomInput
            v-model="addressData.city"
            :rules="[rules.required]"
            :is-required="true"
            :label="'City*'"
          />
        </v-col>
        <v-col 
          cols="12"
          sm="3"
        >
          <CustomAutocomplete 
            v-model="addressData.state"
            :items="statesCodesLabels"
            :label="'State*'"
            :error="
              !assignmentState.contactsInfo[contactData.id]
              .addresses[addressData.id]?.state"
            :error-messages="
              !assignmentState.contactsInfo[contactData.id]
              .addresses[addressData.id]?.state ? ['Value is required'] : []
            "
          />
        </v-col>
        <v-col
          v-if="addressData.id"
          cols="12"
          sm="1"
        >
          <v-btn
            style="transform: translateY(50%);"
            color="error"
            density="compact"
            icon="mdi-close"
            @click="() => openConfirmationModal(contactData.id, 'address', addressData.id)"
          />
        </v-col>
        <v-col 
          cols="12"
          sm="8"
        >
          <CustomInput
            v-model="addressData.addressLine"
            :rules="[rules.required]"
            :is-required="true"
            :label="'Address line*'"
          />
        </v-col>
        <v-col 
          cols="12"
          sm="4"
        >
          <CustomInput
            v-model="addressData.zip"
            :rules="[rules.required, rules.zipCode]"
            :is-required="true"
            :label="'Zip code*'"
            :placeholder="'XXXXX-XXXX'"
          />
        </v-col>
      </v-row>
      <v-row class="pb-7">
        <v-col cols="12">
          <v-btn
            color="info"
            variant="elevated"
            @click="() => addInfo(contactData.id, 'address')"
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
  import { defineEmits, reactive, computed, onMounted } from 'vue';
  import { storeToRefs } from 'pinia';

  import { type IFormRules } from '@/utils/interfaces';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useConfirmationStore } from '@/stores/confirmation';

  import CustomInput from '@/components/UI/CustomInput.vue';
  import CustomSelect from '@/components/UI/CustomSelect.vue';
  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';

  import { 
    contactTypesLabels,
    phoneNumberTypesLabels, 
    addressesTypesLabels, 
    statesCodesLabels 
  } from '@/helpers/assignmentModal';

  const { assignmentState } = storeToRefs(useAssignmentStore());
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

  const getContactTypesLabels = computed(
    () => contactTypesLabels.filter(contactType => !assignmentState.value.contactsInfo.some(
        contact => contact.type?.includes(contactType) && 
        (contact.type.toLowerCase().includes('owner') ||
        contact.type.toLowerCase().includes('driver') ||
        contact.type.toLowerCase().includes('estimator'))
      ))
  );

  const setAssignmentDataPropName = (infoType: string) => {
    const infoTypeInLowerCase = infoType.toLowerCase();

    if (infoTypeInLowerCase.includes('phone')) {
      return 'phoneNumbers';
    } else if (infoTypeInLowerCase.includes('address')) {
      return 'addresses';
    }

    return '';
  }

  const addContact = () => {
    const sortedContactsArr = 
    assignmentState.value.contactsInfo.sort((a,b) => a.id - b.id);

    const qtyOfContacts = sortedContactsArr.length;
    const lastContactIdValue = sortedContactsArr[qtyOfContacts - 1].id;

    addContactData({
      id: lastContactIdValue + 1,
      type: getContactTypesLabels.value[0],
      firstName: '',
      lastName: '',
      email: '',
      phoneNumbers: [{
        id: 0,
        type: 'Mobile',
        number: ''
      }],
      addresses: [{
        id: 0,
        type: 'Home',
        city: '',
        state: null,
        zip: '',
        addressLine: ''
      }]
    });

    emits('validate-form');
  }

  const addInfo = (contactId: number, infoType: string) => {
    const contactIndex = assignmentState.value.contactsInfo.findIndex(contact => contact.id == contactId);
    const assignmentDataPropName = setAssignmentDataPropName(infoType);

    if (contactIndex > -1 && assignmentDataPropName) {
      const sortedInfoArr = 
        assignmentState.value.contactsInfo[contactIndex][assignmentDataPropName].sort((a,b) => a.id - b.id);

      const qtyOfInfoElements = sortedInfoArr.length;
      const lastInfoIdValue = sortedInfoArr[qtyOfInfoElements - 1].id;

      switch(assignmentDataPropName) {
        case 'phoneNumbers':
          addContactInfoData(
            contactIndex,
            assignmentDataPropName,
            {
              id: lastInfoIdValue + 1,
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
              id: lastInfoIdValue + 1,
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

  const openConfirmationModal = (contactId: number, infoType: string, infoId: number | null = null) => {
    switch(infoType) {
      case 'phoneNumber':
        setConfirmationDataAndShow(
          "Phone number deletion", 
          "Do you really want to remove this phone number?", 
          removePhoneNumberData,
          [contactId, infoId]
        );
        break;

      case 'address':
        setConfirmationDataAndShow(
          "Address deletion", 
          "Do you really want to remove this address?", 
          removeAddressData,
          [contactId, infoId]
        );
        break;
      
      case 'contact':
        setConfirmationDataAndShow(
          "Contact deletion", 
          "Do you really want to remove this contact?", 
          removeContactData,
          [contactId]
        );
        break;
    }
  }

  const rules: IFormRules = reactive({
    required: (value) => value ? true : 'Value is required',
    email: (value) => /.+@.+\..+/.test(value) ? 
      true : 
      'Value is not a valid email address',
    phoneNumber: (value) => /^[+(]?\d{0,2}[)-\s.]?\d{3}[-\s.]?\d{3}[-\s.]?\d{3,4}$/.test(value) ? 
      true : 
      'Value is not a valid phone number',
    zipCode: (value) => /^\d{5}[-\s.]?(\d{4})?$/.test(value) ? 
      true : 
      'Value is not a valid zip code',
  });
</script>