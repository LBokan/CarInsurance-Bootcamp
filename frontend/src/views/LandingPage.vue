<template>
  <v-container class="pt-15">
    <v-row>
      <v-btn color="success" @click="openModal">
        Create assignment
      </v-btn>
    </v-row>
    <v-row class="mt-10">
      <AssignmentList />
    </v-row>
  </v-container>

  <CreateAssignmentModal
    v-if="assignment.isModalOpen"
    v-model="assignment.isModalOpen"
  />
</template>

<script setup lang="ts">
  import { onMounted } from 'vue';
  import { storeToRefs } from 'pinia';

  import { useAssignmentStore } from '@/stores/assignment';
  import CreateAssignmentModal from '@/components/CreateAssignmentModal/CreateAssignmentModal.vue';
  import AssignmentList from '@/components/AssignmentList.vue';

  const { assignment } = storeToRefs(useAssignmentStore());
  const { 
    showAssignmentModal, 
    setAssignmentData
  } = useAssignmentStore();

  onMounted(() => {
    if (localStorage.getItem('assignmentData')) {
      openModal();
    }
  })
  
  const openModal = () => {
    const assignmentDataFromLS = localStorage.getItem('assignmentData');

    if (assignmentDataFromLS) {
      setAssignmentData(JSON.parse(assignmentDataFromLS));
    }

    showAssignmentModal();
  }
</script>