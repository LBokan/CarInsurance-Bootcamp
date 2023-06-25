<template>
  <AssignmentDialog 
    :isLoadingAssignment="isLoadingAssignment"
    :isSuccessAssignment="isSuccessAssignment"
    :isLoadingPhotos="isLoadingPhotos"
    @open-photo="openPhoto" 
  />

  <v-dialog
    v-model="isPhotoShown"
    width="auto"
  >
    <v-card>
      <v-img
        :width="1200"
        :src="photoData"
        @click="closePhoto"
      />
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { onMounted, onUnmounted, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';

  import { eventBusNames } from '@/helpers/constants';
  import { useUserStore } from '@/stores/user';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useContactStore } from '@/stores/contact';
  import { useUser } from '@/hooks/useGetUser';
  import { useGetAssignment } from '@/hooks/useGetAssignment';
  import { useGetPhotos } from '@/hooks/useGetPhotos';

  import AssignmentDialog from '@/components/AssignmentDialog/AssignmentDialog.vue';

  const router = useRouter();
  const bus = useEventBus<string>(eventBusNames.fetchAssignment);

  const { userState } = storeToRefs(useUserStore());
  const { assignment } = storeToRefs(useAssignmentStore());
  const { showAssignmentDialog } = useAssignmentStore();
  const { contact } = storeToRefs(useContactStore());
  const { setContactData } = useContactStore();
  const { getUser } = useUser();

  const {
    getAssignmentById,
    isLoading: isLoadingAssignment,
    isSuccess: isSuccessAssignment
  } = useGetAssignment();
  const { 
    getPhotos,
    isLoading: isLoadingPhotos
  } = useGetPhotos();

  const isPhotoShown = ref<boolean>(false);
  const photoData = ref<string>('');

  const handleAssignmentFetch = async() => {
    const assignmentIdFromURL = parseInt(router.currentRoute.value.params.id.toString());

    if (!isNaN(assignmentIdFromURL)) {
      await getAssignmentById(assignmentIdFromURL);

      if (assignment.value.userId && assignment.value.vehicleConditionInfo.namesOfPhotosOfImpact) {
        getPhotos({
          userId: assignment.value.userId,
          assignmentId: +assignment.value.id,
          namesOfPhotosOfImpact: assignment.value.vehicleConditionInfo.namesOfPhotosOfImpact
        });
      }
    }

    showAssignmentDialog();
  };

  const handleBusAssignment = async (busEvent: string) => {
    if (busEvent == 'true') {
      await handleAssignmentFetch();

      const updatedContactData = assignment.value.contacts.find(item => item.id == contact.value.id);

      if (updatedContactData) {
        setContactData(updatedContactData);
      }
    }
  };

  onMounted(async () => {
    bus.on(handleBusAssignment);

    if (!userState.value.id) {
      await getUser();
    }

    if (!assignment.value.isDialogOpen && !assignment.value.id) {
      await handleAssignmentFetch();
    }
  });

  onUnmounted(() => {
    bus.off(handleBusAssignment);
  });

  const openPhoto = (photo: string) => {
    isPhotoShown.value = true;
    photoData.value = photo;
  };

  const closePhoto = () => {
    isPhotoShown.value = false;
    photoData.value = '';
  };
</script>