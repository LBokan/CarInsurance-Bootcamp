<template>
  <v-container class="d-flex flex-row flex-wrap justify-start align-start w-100 mx-auto assignment-list-wrapper">
    <PreloaderCircle v-if="isLoadingGetAssignments" />

    <AssignmentItem 
      v-else-if="!isLoadingGetAssignments && isSuccessGetAssignments && dataAssignments.length"
      v-for="assignment in dataAssignments"
      :key="assignment.id"
      :assignmentData="assignment"
    />
    <v-container v-else-if="!isLoadingGetAssignments && isSuccessGetAssignments && !dataAssignments.length">
      <h1 v-if="userRole === ROLES.client" class="mt-10 text-h5 text-center">No assignments created</h1>
      <h1 v-else class="mt-10 text-h5 text-center">No assignments assigned</h1>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
  import { onMounted, onUnmounted } from 'vue';
  import { useEventBus } from '@vueuse/core';
  import { storeToRefs } from 'pinia';

  import { ROLES, eventBusNames } from '@/helpers/constants';
  import { useUserStore } from '@/stores/user';
  import { useGetAssignments } from '@/hooks/useGetAssignments';
  import PreloaderCircle from '@/components/PreloaderCircle.vue';
  import AssignmentItem from '@/components/AssignmentItem.vue';

  const bus = useEventBus<boolean>(eventBusNames.fetchAssignments);

  const { userRole } = storeToRefs(useUserStore());
  const {
    getAssignments,
    data: dataAssignments,
    isLoading: isLoadingGetAssignments,
    isSuccess: isSuccessGetAssignments
  } = useGetAssignments();

  const handleBusAssignments = (busEvent: boolean) => {
    if (busEvent) {
      getAssignments();
    }
  }

  onMounted(() => {
    bus.on(handleBusAssignments);

    getAssignments();
  })

  onUnmounted(() => {
    bus.off(handleBusAssignments);
  })
</script>

<style scoped>
  .assignment-list-wrapper {
    min-height: 80dvh;
    max-height: 80dvh;
    overflow-y: auto;
  }
</style>