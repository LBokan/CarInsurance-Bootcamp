<template>
  <ApplicationBar />

  <v-container class="pt-15">
    <v-row v-if="userRole === ROLES.client" class="mb-10">
      <v-btn color="success" @click="openModal">
        Create assignment
      </v-btn>
    </v-row>
    <v-row>
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

  import { ROLES } from '@/helpers/constants';
  import { useUserStore } from '@/stores/user';
  import { useAssignmentStore } from '@/stores/assignment';

  import ApplicationBar from '@/components/ApplicationBar.vue';
  import CreateAssignmentModal from '@/components/CreateAssignmentModal/CreateAssignmentModal.vue';
  import AssignmentList from '@/components/AssignmentList.vue';

  const { userRole } = storeToRefs(useUserStore());
  const { assignment } = storeToRefs(useAssignmentStore());
  const { showAssignmentModal, setAssignmentData } = useAssignmentStore();

  onMounted(async() => {
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
  };
</script>