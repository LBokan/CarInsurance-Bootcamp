<template>
  <v-dialog 
    persistent 
    width="800" 
    scrollable
  >
    <v-card>
      <v-form v-model="assignment.formModel" ref="formRef">
        <v-card-title class="position-relative">
          <h1 class="text-h5">Assignment creation</h1>
          
          <div class="page-info-chip">
            <v-btn
              v-if="assignment.page > 1"
              color="blue"
              density="compact"
              icon="mdi-arrow-left"
              @click="goToPrevPage"
            />
            <v-chip class="mx-2" color="blue">
              Page: {{ assignment.page }} / 3
            </v-chip>
            <v-btn
              v-if="assignment.page < 3 && assignment.formModel"
              color="blue"
              density="compact"
              icon="mdi-arrow-right"
              @click="goToNextPage"
            />
          </div>
        </v-card-title>
        
        <v-divider/>

        <CreateContactInfoCard 
          v-if="assignment.page == 1"
          @validate-form="validateAssignment" 
        />
        <CreateVehicleInfoCard 
          v-if="assignment.page == 2" 
          @validate-form="validateAssignment" 
        />
        <CreateVehicleConditionInfoCard 
          v-if="assignment.page == 3" 
          @validate-form="validateAssignment" 
        />

        <v-divider/>

        <v-card-actions>
          <v-spacer/>
          <v-btn
            v-if="assignment.page < 3"
            class="mr-5"
            color="primary"
            rounded="lg"
            variant="elevated"
            @click="saveProgress"
          >
            Save progress
          </v-btn>
          <v-btn
            v-if="assignment.page < 3"
            class="mr-5"
            color="success"
            rounded="lg"
            variant="elevated"
            :disabled="!assignment.formModel"
            @click="continueCreateAssignment"
          >
            Continue
          </v-btn>
          <v-btn
            v-else
            class="mr-5"
            color="success"
            rounded="lg"
            variant="elevated"
            :loading="isLoading"
            :disabled="!assignment.formModel"
            @click="submit"
          >
            Create
          </v-btn>

          <v-btn
            color="error"
            rounded="lg"
            variant="elevated"
            @click="closeAssignment"
          >
            Close
          </v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { ref, type VNodeRef } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';

  import { useAssignmentStore } from '@/stores/assignment';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useCreateAssignment } from '@/hooks/useCreateAssignment';

  import CreateContactInfoCard from '@/components/CreateAssignmentModal/CreateContactInfoCard.vue';
  import CreateVehicleInfoCard from '@/components/CreateAssignmentModal/CreateVehicleInfoCard.vue';
  import CreateVehicleConditionInfoCard from '@/components/CreateAssignmentModal/CreateVehicleConditionInfoCard.vue';

  const bus = useEventBus<string>('isAssignmentCreated');

  const { assignment } = storeToRefs(useAssignmentStore());
  const { 
    goToNextPage, 
    goToPrevPage, 
    closeAndResetAssignmentModalOrDialog 
  } = useAssignmentStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const { 
    createAssignment,
    isLoading,
    isSuccess
  } = useCreateAssignment();

  const formRef: VNodeRef = ref(null);

  const validateAssignment = () => {
    if (formRef.value) {
      formRef.value.validate();
    }
  };

  const saveProgress = () => {
    localStorage.setItem('assignmentData', JSON.stringify({
      ...assignment.value,
      vehicleConditionInfo: {
        directionOfImpact: assignment.value.vehicleConditionInfo.directionOfImpact,
        photosOfImpactFiles: []
      }
    }));
    setSnackbarDataAndShow('Your data is successfully saved', 'success');
  };

  const continueCreateAssignment = () => {
    goToNextPage();
  };

  const submit = async () => {
    await createAssignment();

    if (!isLoading.value && isSuccess.value) {
      resetAssignment();
      setSnackbarDataAndShow("An assignments is successfully created", 'success');

      bus.emit('true');
    }
  };

  const resetAssignment = () => {
    if (formRef.value) {
      formRef.value.reset();
    }

    localStorage.removeItem('assignmentData');
    closeAndResetAssignmentModalOrDialog();
  };

  const closeAssignment = () => {
    setConfirmationDataAndShow({
      title: "Assignment creation cancellation",
      content: "Do you really want to cancel the creation of the assignment? " +
        "All your entered data will be lost",
      onConfirmAction: resetAssignment
    });
  };
</script>