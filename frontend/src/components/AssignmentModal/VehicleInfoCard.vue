<template>
  <v-card-text>
    <v-container>
      <v-row>
        <v-col cols="12">
          <p class="text-subtitle-1">Vehicle information</p>
        </v-col>

        <v-col cols="12">
          <CustomInput
            v-model="assignment.vehicleInfo.vinNumber"
            :rules="[rules.required, rules.vinNumber]"
            :is-required="true"
            :label="'VIN number'"
            :counter="17"
          />
        </v-col>

        <v-col cols="12" sm="6">
          <CustomInput
            v-model="assignment.vehicleInfo.carMake"
            :rules="[rules.required]"
            :is-required="true"
            :label="'Car make'"
          />
        </v-col>
        <v-col cols="12" sm="6">
          <CustomInput
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
          <v-label class="text-caption label" text="Year of manufacture*"/>
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
          <CustomInput
            v-model="assignment.vehicleInfo.odometerValue"
            :rules="[rules.required, rules.odometerValue]"
            :is-required="true"
            :suffix="'km'"
            :label="'Odometer value'"
          />
        </v-col>

        <v-col cols="12" sm="5">
          <CustomInput
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
          <v-label class="text-caption label" text="License expiration date*"/>
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
  import { defineEmits, reactive, ref, onMounted } from 'vue';
  import { storeToRefs } from 'pinia';

  import { type IFormRules } from '@/utils/interfaces';
  import { useAssignmentStore } from '@/stores/assignment';

  import VueDatePicker from '@vuepic/vue-datepicker';
  import CustomInput from '@/components/UI/CustomInput.vue';
  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';
  import { 
    statesCodesLabels,
    getYearToday
  } from '@/helpers/assignmentModal';


  const { assignment } = storeToRefs(useAssignmentStore());

  const emits = defineEmits(['validate-form']);

  const yearOfManufactureRange = ref([1980, getYearToday()]);
  
  onMounted(() => {
    emits('validate-form');
  })

  const rules: IFormRules = reactive({
    required: (value) => value ? true : 'Value is required',
    vinNumber: (value) => /^[\dA-Za-z]{17}$/.test(value) ? 
      true : 
      'Value is not a valid VIN number',
    odometerValue: (value) => /^\d*$/.test(value) && +value > 0 ? 
      true : 
      'Value is not a valid odometer value',
    licensePlate: (value) => /^[\dA-Za-z]{7,8}$/.test(value) ? 
      true : 
      'Value is not a valid license plate value',
  });
</script>

<style scoped>
  .dp__theme_light {
    --dp-border-color: #959595;
    --dp-border-color-hover: #000;
  }

  .label-wrapper {
    position: relative;
  }

  .label {
    position: absolute;
    top: 0;
    left: 0;
    z-index: 9;
    padding: 0 5px;
    background-color: #fff;
    opacity: 1;
    transform: translate(18%, 10%);
  }
</style>