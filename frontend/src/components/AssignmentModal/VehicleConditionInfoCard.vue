<template>
  <v-card-text>
    <v-container>
      <v-row>
        <v-col
          cols="12"
        >
          <p class="text-subtitle-1">Vehicle condition</p>
        </v-col>

        <v-col
          cols="12"
        >
          <CustomAutocomplete 
            v-model="assignmentState.vehicleConditionInfo.directionsOfImpact"
            :rules="[rules.required]"
            :is-multiple="true"
            :items="impactDirectionsLabels"
            :is-chips="true"
            :label="'Direction of an impact*'"
            :error="
              !assignmentState.vehicleConditionInfo.directionsOfImpact.length
            "
            :error-messages="
              !assignmentState.vehicleConditionInfo.directionsOfImpact.length ? 
              ['Value is required'] : []
            "
          />
        </v-col>

        <v-col
          cols="12"
        >
          <v-file-input 
            v-model="assignmentState.vehicleConditionInfo.photosOfImpact"
            variant="outlined"
            multiple
            chips
            counter
            prepend-icon="mdi-camera"
            accept="image/png, image/jpeg, image/bmp"
            label="Photos of impact"
            placeholder="Photos of a vehicle without and with impacts*"
            :error="
              !assignmentState.vehicleConditionInfo.photosOfImpact.length
            "
            :error-messages="
              !assignmentState.vehicleConditionInfo.photosOfImpact.length ? 
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

  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';
  import { impactDirectionsLabels } from '@/helpers/assignmentModal';

  const { assignmentState } = storeToRefs(useAssignmentStore());

  const emits = defineEmits(['validate-form']);
  
  onMounted(() => {
    emits('validate-form');
  })

  const rules: IFormRules = reactive({
    required: (value) => value ? true : 'Value is required',
    vinNumber: (value) => /^[\dA-Za-z]{17}$/.test(value) ? 
      true : 
      'Value is not a valid VIN number',
    phoneNumber: (value) => /^[+(]?\d{0,2}[)-\s.]?\d{3}[-\s.]?\d{3}[-\s.]?\d{3,4}$/.test(value) ? 
      true : 
      'Value is not a valid phone number',
    zipCode: (value) => /^\d{5}[-\s.]?(\d{4})?$/.test(value) ? 
      true : 
      'Value is not a valid zip code',
  });
</script>