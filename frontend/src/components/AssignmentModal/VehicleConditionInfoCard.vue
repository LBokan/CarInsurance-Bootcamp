<template>
  <v-card-text>
    <v-container>
      <v-row>
        <v-col cols="12">
          <p class="text-subtitle-1">Incident information</p>
        </v-col>

        <v-col
          class="label-wrapper"
          cols="12"
          sm="4"
        >
          <v-label class="text-caption label" text="Date of the incident*"/>
          <VueDatePicker 
            v-model="assignment.dateOfIncident"
            :style="{ '--dp-input-padding': '15px 30px 15px 12px' }"
            :clearable="false"
            :max-date="new Date()"
            auto-apply
            :enable-time-picker="false"
            placeholder="Date of the incident*"
          />
        </v-col>

        <v-divider />

        <v-col cols="12">
          <p class="text-subtitle-1">Vehicle condition</p>
        </v-col>

        <v-col cols="12">
          <CustomAutocomplete 
            v-model="assignment.vehicleConditionInfo.directionOfImpact"
            :rules="[rules.required]"
            :is-required="true"
            :items="impactDirectionsLabels"
            :label="'Direction of an impact'"
            :error="
              !assignment.vehicleConditionInfo.directionOfImpact.length
            "
            :error-messages="
              !assignment.vehicleConditionInfo.directionOfImpact.length ? 
              ['Value is required'] : []
            "
          />
        </v-col>

        <v-col cols="12">
          <v-file-input 
            v-model="assignment.vehicleConditionInfo.photosOfImpact"
            variant="outlined"
            multiple
            chips
            counter
            prepend-icon="mdi-camera"
            accept="image/png, image/jpeg, image/bmp"
            label="Photos of impact*"
            placeholder="Photos of a vehicle without and with impacts*"
            :error="
              !assignment.vehicleConditionInfo.photosOfImpact.length
            "
            :error-messages="
              !assignment.vehicleConditionInfo.photosOfImpact.length ? 
              ['Photos are required'] : []
            "
            :show-size="1000"
          />
        </v-col>
      </v-row>
    </v-container>
  </v-card-text>
</template>

<script setup lang="ts">
  import { defineEmits, reactive, onMounted } from 'vue';
  import { storeToRefs } from 'pinia';

  import { type IFormRules } from '@/utils/interfaces';
  import { useAssignmentStore } from '@/stores/assignment';

  import VueDatePicker from '@vuepic/vue-datepicker';
  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';
  import { impactDirectionsLabels } from '@/helpers/assignmentModal';

  const { assignment } = storeToRefs(useAssignmentStore());

  const emits = defineEmits(['validate-form']);
  
  onMounted(() => {
    emits('validate-form');
  })

  const rules: IFormRules = reactive({
    required: (value) => value ? true : 'Value is required',
    vinNumber: (value) => /^[\dA-Za-z]{17}$/.test(value) ? 
      true : 
      'Value is not a valid VIN number',
    phoneNumber: (value) => 
      /^[+(]?\d{0,2}[)-\s.]?\d{3}[-\s.]?\d{3}[-\s.]?\d{3,4}$/.test(value) ? 
      true : 
      'Value is not a valid phone number',
    zipCode: (value) => /^\d{5}[-\s.]?(\d{4})?$/.test(value) ? 
      true : 
      'Value is not a valid zip code',
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