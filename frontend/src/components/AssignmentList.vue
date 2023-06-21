<template>
  <v-container class="d-flex flex-row flex-wrap justify-start align-start w-100 mx-auto assignment-list-wrapper">
    <PreloaderCircle v-if="isLoading" />

    <AssignmentItem 
      v-else-if="!isLoading && isSuccess && data.length"
      v-for="assignment in data"
      :key="assignment.assignmentId"
      :assignmentData="assignment"
    />
    <v-container v-else-if="!isLoading && isSuccess && !data.length">
      <h1 class="mt-10 text-h5 text-center">No assignments created</h1>
    </v-container>
  </v-container>
</template>

<script setup lang="ts">
  import { onMounted, onUnmounted } from 'vue';
  import { useEventBus } from '@vueuse/core';

  import { useGetAssignments } from '@/hooks/useGetAssignments';
  import PreloaderCircle from '@/components/PreloaderCircle.vue';
  import AssignmentItem from '@/components/AssignmentItem.vue';

  const bus = useEventBus<string>('isAssignmentCreated');

  const {
    getAssignments,
    data,
    isLoading,
    isSuccess
  } = useGetAssignments();

  const handleAssignmentCreated = (isAssignmentCreated: string) => {
    if (isAssignmentCreated == 'true') {
      getAssignments();
    }
  }

  onMounted(() => {
    bus.on(handleAssignmentCreated);

    getAssignments();
  })

  onUnmounted(() => {
    bus.off(handleAssignmentCreated);
  })
</script>

<style scoped>
  .assignment-list-wrapper {
    min-height: 80dvh;
    max-height: 80dvh;
    overflow-y: auto;
  }
</style>