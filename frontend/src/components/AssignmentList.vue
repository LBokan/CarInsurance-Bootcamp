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
  import { inject, onMounted, onUnmounted } from 'vue';
  import { type Emitter } from 'mitt';

  import { useGetAssignments } from '@/hooks/useGetAssignments';
  import { type AppEvents } from '@/utils/interfaces';
  import PreloaderCircle from '@/components/PreloaderCircle.vue';
  import AssignmentItem from '@/components/AssignmentItem.vue';

  const {
    getAssignments,
    data,
    isLoading,
    isSuccess
  } = useGetAssignments();

  const emitter: Emitter<AppEvents> | undefined = inject('emitter');

  const handleAssignmentCreated = (isAssignmentCreated: boolean) => {
    if (isAssignmentCreated) {
      getAssignments();
    }
  }

  onMounted(() => {
    if (emitter) {
      emitter.on('isAssignmentCreated', handleAssignmentCreated);
    }

    getAssignments();
  })

  onUnmounted(() => {
    if (emitter) {
      emitter.off('isAssignmentCreated', handleAssignmentCreated);
    }
  })
</script>

<style scoped>
  .assignment-list-wrapper {
    min-height: 80dvh;
    max-height: 80dvh;
    overflow-y: auto;
  }
</style>