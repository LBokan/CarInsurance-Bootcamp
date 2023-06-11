<template>
  <v-dialog
    persistent
    width="800"
    scrollable
  >
    <v-card>
      <v-form 
        v-model="assignmentState.formModel" 
        ref="formRef"
      >
        <v-card-title class="position-relative">
          <p class="text-h5">Assignment creation</p>
          
          <div class="page-info-chip">
            <v-btn
              v-if="assignmentState.page > 1"
              color="blue"
              density="compact"
              icon="mdi-arrow-left"
              @click="setPrevPage"
            />
            <v-chip
              class="mx-2"
              color="blue"
            >
              Page: {{ assignmentState.page }} / 3
            </v-chip>
            <v-btn
              v-if="assignmentState.page < 3 && assignmentState.formModel"
              color="blue"
              density="compact"
              icon="mdi-arrow-right"
              @click="setNextPage"
            />
          </div>
        </v-card-title>
        
        <v-divider/>

        <ContactInfoCard 
          v-if="assignmentState.page == 1"
          @validate-form="validateAssignment" 
        />
        <VehicleInfoCard 
          v-else-if="assignmentState.page == 2" 
          @validate-form="validateAssignment" 
        />
        <VehicleConditionInfoCard 
          v-else-if="assignmentState.page == 3" 
          @validate-form="validateAssignment" 
        />

        <v-divider/>

        <v-card-actions>
          <v-spacer/>
          <v-btn
            v-if="assignmentState.page < 3"
            class="mr-5"
            color="primary"
            rounded="lg"
            variant="elevated"
            @click="saveProgress"
          >
            Save progress
          </v-btn>
          <v-btn
            v-if="assignmentState.page < 3"
            class="mr-5"
            color="success"
            rounded="lg"
            variant="elevated"
            :disabled="!assignmentState.formModel"
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
            :disabled="!assignmentState.formModel"
            @click="createAssignment"
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
  import { storeToRefs } from 'pinia';

  import { useAssignmentStore } from '@/stores/assignment';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useSnackbarStore } from '@/stores/snackbar';

  import ContactInfoCard from '@/components/AssignmentModal/ContactInfoCard.vue';
  import VehicleInfoCard from '@/components/AssignmentModal/VehicleInfoCard.vue';
  import VehicleConditionInfoCard from '@/components/AssignmentModal/VehicleConditionInfoCard.vue';

  const { assignmentState } = storeToRefs(useAssignmentStore());
  const { setNextPage, setPrevPage, closeAndResetAssignmentModal } = useAssignmentStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();

  const formRef: VNodeRef = ref(null);

  const validateAssignment = () => {
    if (formRef.value) {
      formRef.value.validate();
    }
  }

  const saveProgress = () => {
    localStorage.setItem('assignmentData', JSON.stringify(assignmentState.value));
    setSnackbarDataAndShow('Your data is successfully saved', 'success');
  };

  const continueCreateAssignment = () => {
    setNextPage();
  };

  const createAssignment = () => {
    alert('Assignment created');
  };

  const resetAssignment = () => {
    if (formRef.value) {
      formRef.value.reset();
    }

    localStorage.removeItem('assignmentData');
    closeAndResetAssignmentModal();
  };

  const closeAssignment = () => {
    setConfirmationDataAndShow(
      "Assignment creation cancellation", 
      "Do you really want to cancel the creation of the assignment? All your entered data will be lost", 
      resetAssignment
    );
  };
</script>

<style scoped>
  .page-info-chip {
    position: absolute;
    top: 50%;
    right: 10%;
    transform: translateY(-50%);
  }
</style>