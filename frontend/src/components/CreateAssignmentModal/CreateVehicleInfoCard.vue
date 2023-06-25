<template>
  <v-card-text>
    <v-container>
      <v-row>
        <v-col cols="12">
          <h2 class="text-subtitle-1">Vehicle information</h2>
        </v-col>

        <v-col cols="12">
          <CustomTextInput
            v-model="assignment.vehicleInfo.vinNumber"
            :rules="[rules.required, rules.vinNumber]"
            :is-required="true"
            :label="'VIN number'"
            :counter="17"
          />
        </v-col>

        <v-col cols="12" sm="6">
          <CustomTextInput
            v-model="assignment.vehicleInfo.carMake"
            :rules="[rules.required]"
            :is-required="true"
            :label="'Car make'"
          />
        </v-col>
        <v-col cols="12" sm="6">
          <CustomTextInput
            v-model="assignment.vehicleInfo.carModel"
            :rules="[rules.required]"
            :is-required="true"
            :label="'Car model'"
          />
        </v-col>

        <v-col
          class="label-wrapper"
          cols="12"
          sm="4"
        >
          <v-label class="text-caption label-item" text="Year of manufacture*"/>
          <VueDatePicker 
            v-model="assignment.vehicleInfo.yearOfManufacture"
            :style="{ '--dp-input-padding': '15px 30px 15px 12px' }"
            :clearable="false"
            year-picker
            :year-range="yearOfManufactureRange"
            auto-apply
            placeholder="Year of manufacture*"
          />
        </v-col>
        <v-col cols="12" sm="8">
          <CustomTextInput
            v-model="assignment.vehicleInfo.odometerValue"
            :rules="[rules.required, rules.odometerValue]"
            :is-required="true"
            :suffix="'km'"
            :label="'Odometer value'"
          />
        </v-col>

        <v-col cols="12" sm="5">
          <CustomTextInput
            v-model="assignment.vehicleInfo.licensePlateNumber"
            :rules="[rules.required, rules.licensePlate]"
            :is-required="true"
            :label="'License plate number'"
          />
        </v-col>
        <v-col cols="12" sm="3">
          <CustomAutocomplete 
            v-model="assignment.vehicleInfo.licenseState"
            :rules="[rules.required]"
            :is-required="true"
            :items="statesCodesLabels"
            :label="'License state'"
            :error="!assignment.vehicleInfo.licenseState"
            :error-messages="!assignment.vehicleInfo.licenseState ? 
              ['Value is required'] : []"
          />
        </v-col>
        <v-col
          class="label-wrapper"
          cols="12"
          sm="4"
        >
          <v-label class="text-caption label-item" text="License expiration date*"/>
          <VueDatePicker 
            v-model="assignment.vehicleInfo.licenseExpirationDate"
            :style="{ '--dp-input-padding': '15px 30px 15px 12px' }"
            :clearable="false"
            :min-date="new Date()"
            auto-apply
            :enable-time-picker="false"
            placeholder="License expiration date*"
          />
        </v-col>
      </v-row>
    </v-container>
  </v-card-text>
</template>

<script setup lang="ts">
  import { ref, onMounted } from 'vue';
  import { storeToRefs } from 'pinia';

  import { rules } from '@/helpers/rulesRegex';
  import { getYearToday } from '@/helpers/assignment';
  import { statesCodesLabels } from '@/helpers/contact';
  import { useAssignmentStore } from '@/stores/assignment';

  import VueDatePicker from '@vuepic/vue-datepicker';
  import CustomTextInput from '@/components/UI/CustomTextInput.vue';
  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';

  const emits = defineEmits(['validate-form']);
  
  const { assignment } = storeToRefs(useAssignmentStore());

  const yearOfManufactureRange = ref([1980, getYearToday()]);
  
  onMounted(() => {
    emits('validate-form');
  })
</script>