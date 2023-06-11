<template>
  <div>
    <h1>
      LANDING PAGE
    </h1>

    <v-btn color="red" @click="openModal">
      Open modal
    </v-btn>

    <CreateAssignmentModal 
      v-model="assignmentState.isOpen"
    />

    <router-link to="/login">Login page</router-link>
  </div>
</template>

<script setup lang="ts">
  import { storeToRefs } from 'pinia';

  import { useAssignmentStore } from '@/stores/assignment';
  import CreateAssignmentModal from '@/components/AssignmentModal/CreateAssignmentModal.vue';

  const { assignmentState } = storeToRefs(useAssignmentStore());
  const { 
    showAssignmentModal, 
    setAssignmentStateData
  } = useAssignmentStore();
  
  const openModal = () => {
    const assignmentDataFromLS = localStorage.getItem('assignmentData');

    if (assignmentDataFromLS) {
      setAssignmentStateData(JSON.parse(assignmentDataFromLS));
    }

    showAssignmentModal();
  }

  if (localStorage.getItem('assignmentData')) {
    openModal();
  }
</script>