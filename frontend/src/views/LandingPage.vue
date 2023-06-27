<template>
  <v-container class="pt-15">
    <v-row>
      <v-btn v-if="userRole === ROLES.client" color="success" @click="openModal">
        Create assignment
      </v-btn>
      <v-spacer />
      <v-btn color="error" @click="openLogoutModal">
        Logout
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

  import { ROLES } from '@/helpers/constants';
  import { logout } from '@/helpers/authorization';
  import { useUserStore } from '@/stores/user';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useConfirmationStore } from '@/stores/confirmation';
  import { useUser } from '@/hooks/useGetUser';

  import CreateAssignmentModal from '@/components/CreateAssignmentModal/CreateAssignmentModal.vue';
  import AssignmentList from '@/components/AssignmentList.vue';

  const { userState, userRole } = storeToRefs(useUserStore());
  const { assignment } = storeToRefs(useAssignmentStore());
  const { showAssignmentModal, setAssignmentData } = useAssignmentStore();
  const { setConfirmationDataAndShow } = useConfirmationStore();

  const { getUser } = useUser();

  onMounted(async() => {
    if (!userState.value.id) {
      await getUser();
    }

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

  const openLogoutModal = () => {
    setConfirmationDataAndShow({
      title: "Logout",
      content: "Do you really want to log out from the application?",
      onConfirmAction: logout
    });
  }
</script>