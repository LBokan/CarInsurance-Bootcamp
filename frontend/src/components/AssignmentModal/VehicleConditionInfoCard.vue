<template>
  <v-card-text>
    <v-container>
      <v-row>
        <v-col cols="12">
          <h2 class="text-subtitle-1">Incident information</h2>
        </v-col>

        <v-col
          class="label-wrapper"
          cols="12"
          sm="4"
        >
          <v-label class="text-caption label-item" text="Date of the incident*"/>
          <VueDatePicker 
            v-model="assignment.incidentDate"
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
          <h2 class="text-subtitle-1">Vehicle condition</h2>
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
  import { defineEmits, onMounted } from 'vue';
  import { storeToRefs } from 'pinia';

  import { rules } from '@/utils/rulesRegex';
  import { useAssignmentStore } from '@/stores/assignment';

  import VueDatePicker from '@vuepic/vue-datepicker';
  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';
  import { impactDirectionsLabels } from '@/helpers/assignmentModal';

  const { assignment } = storeToRefs(useAssignmentStore());

  const emits = defineEmits(['validate-form']);
  
  onMounted(() => {
    emits('validate-form');
  })
</script>