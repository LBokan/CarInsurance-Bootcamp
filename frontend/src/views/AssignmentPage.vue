<template>
  <ShowAssignmentDialog 
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
  import { onMounted, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { storeToRefs } from 'pinia';

  import { useUserStore } from '@/stores/user';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useUser } from '@/hooks/useGetUser';
  import { useGetAssignment } from '@/hooks/useGetAssignment';
  import { useGetPhotos } from '@/hooks/useGetPhotos';

  import ShowAssignmentDialog from '@/components/ShowAssignmentDialog/ShowAssignmentDialog.vue';

  const router = useRouter();

  const { userState } = storeToRefs(useUserStore());
  const { assignment } = storeToRefs(useAssignmentStore());
  const { showAssignmentDialog } = useAssignmentStore();
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

  onMounted(async () => {
    if (!userState.value.userId) {
      await getUser();
    }

    if (!assignment.value.isDialogOpen && !assignment.value.id) {
      const assignmentIdFromURL = parseInt(router.currentRoute.value.params.id.toString());
  
      if (!isNaN(assignmentIdFromURL)) {
        await getAssignmentById(assignmentIdFromURL);
  
        if (userState.value.userId && assignment.value.vehicleConditionInfo.namesOfPhotosOfImpact) {
          getPhotos({
            userId: userState.value.userId,
            assignmentId: assignment.value.id,
            namesOfPhotosOfImpact: assignment.value.vehicleConditionInfo.namesOfPhotosOfImpact
          });
        }
      }

      showAssignmentDialog();
    }
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

<style scoped>
  .test {
    min-width: 100%;
  }
</style>