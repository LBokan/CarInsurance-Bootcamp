<template>
  <v-dialog 
    persistent 
    width="800" 
    scrollable
  >
    <v-card>
      <v-form v-model="formModel" ref="formRef">
        <v-card-title class="position-relative">
          <h1 class="text-h5">Repair facility setting</h1>
        </v-card-title>
        
        <v-divider/>

        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <CustomAutocomplete 
                  v-model="repairFacilityValue"
                  :is-required="true"
                  :is-loading="isLoadingGetRepairFacilities && !isSuccessGetRepairFacilities"
                  :items="repairFacilitiesLabels"
                  :label="'Repair facility'"
                  :error="!isRepairFacilityValue"
                  :error-messages="!isRepairFacilityValue() ? ['Value is required'] : []"
                />
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-spacer/>
          <v-btn
            color="success"
            rounded="lg"
            variant="elevated"
            :disabled="!formModel"
            @click="submit"
          >
            Set
          </v-btn>

          <v-btn
            class="ml-5"
            color="error"
            rounded="lg"
            variant="elevated"
            @click="closeModal"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { onMounted, ref, type VNodeRef } from 'vue';
  import { useEventBus } from '@vueuse/core';

  import { eventBusNames } from '@/helpers/constants';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useGetRepairFacilities } from '@/hooks/useGetRepairFacilities';
  import { useSetAssignmentRepairFacility } from '@/hooks/useSetAssignmentRepairFacility';

  import CustomAutocomplete from '@/components/UI/CustomAutocomplete.vue';

  const bus = useEventBus<string>(eventBusNames.fetchAssignment);
  const emits = defineEmits(['close-modal']);
  
  const formRef: VNodeRef = ref(null);
  const formModel = ref(null);
  const repairFacilityValue = ref('');
  
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const {
    getRepairFacilities,
    data: repairFacilitiesLabels,
    isLoading: isLoadingGetRepairFacilities,
    isSuccess: isSuccessGetRepairFacilities
  } = useGetRepairFacilities();

  const {
    setAssignmentRepairFacility,
    isLoading: isLoadingSetAssignmentRepairFacility,
    isSuccess: isSuccessSetAssignmentRepairFacility
  } = useSetAssignmentRepairFacility();
  
  const isRepairFacilityValue = () => {
    return !!repairFacilityValue.value;
  };

  const validateForm = () => {
    if (formRef.value) {
      formRef.value.validate();
    }
  };

  onMounted(async () => {
    await getRepairFacilities();
    validateForm();
  })
  
  const resetAndCloseModal = () => {
    if (formRef.value) {
      formRef.value.reset();
    }
    repairFacilityValue.value = '';
    emits('close-modal');
  };

  const submit = async () => {
    await setAssignmentRepairFacility(repairFacilityValue.value);

    if (!isLoadingSetAssignmentRepairFacility.value && isSuccessSetAssignmentRepairFacility.value) {
      resetAndCloseModal();
      setSnackbarDataAndShow("A repair facility is successfully set", 'success');

      bus.emit('true');
    }
  };

  const closeModal = () => {
    setConfirmationDataAndShow({
      title: "Comment creation cancellation",
      content: "Do you really want to cancel the creation of the comment? " +
        "All your entered data will be lost",
      onConfirmAction: resetAndCloseModal
    });
  };
</script>