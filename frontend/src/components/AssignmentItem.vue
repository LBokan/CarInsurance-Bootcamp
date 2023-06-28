<template>
  <v-card
    class="ml-5 mb-5"
    min-width="350"
    max-width="350"
    color="#fff"
    theme="light"
  >
    <v-card-title>Assignment from {{ formatDate(assignmentData.dateOfCreation) }}</v-card-title>

    <v-card-text>
      <v-chip
        class="my-2"
        :color="getChipStatusColor(assignmentData.status)"
        text-color="white"
        :prepend-icon="getChipStatusIcon(assignmentData.status)"
      >
        status: {{ assignmentData.status }}
      </v-chip>

      <v-list class="w-100 bg-transparent">
        <v-list-item
          :title="formatDate(assignmentData.dateOfIncident)"
          subtitle="Date of the incident"
        />
        <v-list-item
          :title="assignmentData.vehicleConditionInfo.directionOfImpact"
          subtitle="Direction of an impact"
        />
      </v-list>
    </v-card-text>
    <v-divider />

    <v-card-actions>
      <v-spacer />
      <v-btn
        color="blue-darken-1"
        variant="text"
        @click="showAssignmentInfo(assignmentData)"
      >
        See more
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script setup lang="ts">
  import { useRouter } from 'vue-router';
  import { storeToRefs } from 'pinia';

  import { formatDate } from '@/helpers/assignment';
  import { getChipStatusColor, getChipStatusIcon } from '@/helpers/styles';
  import { useAssignmentStore } from '@/stores/assignment';
  import { useSnackbarStore } from '@/stores/snackbar';
  import { useGetPhotos } from '@/hooks/useGetPhotos';

  import type { IGetAssignmentAPI } from '@/helpers/interfaces';

  const router = useRouter();

  const { assignment } = storeToRefs(useAssignmentStore());
  const { setAssignmentDataAPI, showAssignmentDialog } = useAssignmentStore();
  const { setSnackbarDataAndShow } = useSnackbarStore();
  
  const { getPhotos } = useGetPhotos();

  defineProps({
    assignmentData: {
      type: Object as () => IGetAssignmentAPI,
      required: true
    }
  });

  const showAssignmentInfo = async (data: IGetAssignmentAPI) => {
    setAssignmentDataAPI(data);

    if (!data.userId || !data.id || !data.vehicleConditionInfo.namesOfPhotosOfImpact) {
      setSnackbarDataAndShow('Bad request', 'error');
      
      return;
    }

    getPhotos({
      userId: data.userId,
      assignmentId: data.id,
      namesOfPhotosOfImpact: data.vehicleConditionInfo.namesOfPhotosOfImpact
    });

    showAssignmentDialog();
    router.push(`/assignment/${assignment.value.id}`);
  }
</script>