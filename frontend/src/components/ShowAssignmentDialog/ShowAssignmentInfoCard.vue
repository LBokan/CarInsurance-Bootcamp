<template>
  <v-col cols="12" sm="6">
    <div>
      <span class="mr-2 text-subtitle-2 font-weight-bold">Status:</span>
      <v-chip
        class="my-2"
        color="info"
        text-color="white"
        prepend-icon="mdi-progress-clock"
      >
        {{ assignment.status }}
      </v-chip>
    </div>
    <p>
      <span class="text-subtitle-2">Date of creation:</span>
      {{ formatDate(assignment.creationDate) }}
    </p>
    <p>
      <span class="text-subtitle-2">Date of incident:</span>
      {{ formatDate(assignment.incidentDate) }}
    </p>
  </v-col>
</template>

<script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { formatDate } from '@/helpers/assignment';
  import { useAssignmentStore } from '@/stores/assignment';

  const { assignment } = storeToRefs(useAssignmentStore());
</script>