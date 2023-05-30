<template>
  <v-snackbar
    v-model="snackbarState.isShown"
    :timeout="snackbarState.timeout"
    :color="snackbarState.type"
  >
    {{ snackbarState.message }}

    <template v-slot:actions>
      <v-btn
        color="white"
        variant="text"
        @click="hideAndResetSnackbar"
      >
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { useSnackbarStore } from '@/stores/snackbar';

  const { snackbarState } = storeToRefs(useSnackbarStore());
  const { hideAndResetSnackbar } = useSnackbarStore();
</script>
