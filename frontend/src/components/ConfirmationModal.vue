<template>
  <v-dialog
    v-if="confirmationState.isOpen"
    v-model="confirmationState.isOpen"
    persistent
    width="600"
  >
    <v-card>
      <v-card-title>
        <p class="text-h6">{{ confirmationState.title }}</p>
      </v-card-title>

      <v-card-text v-if="confirmationState.content">
        <v-container>
          <v-row>
            <p class="text-subtitle-1">{{ confirmationState.content }}</p>
          </v-row>
        </v-container>
      </v-card-text>

      <v-divider />

      <v-card-actions class="p-20">
        <v-spacer/>
        <v-btn
          class="mr-5"
          color="error"
          rounded="lg"
          variant="elevated"
          @click="onYesBtnClick"
        >
          Yes
        </v-btn>
        <v-btn
          color="success"
          rounded="lg"
          variant="elevated"
          @click="closeAndResetOnNoBtnClick"
        >
          No
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
  import { storeToRefs } from 'pinia';
  import { useConfirmationStore } from '@/stores/confirmation';

  const { confirmationState } = storeToRefs(useConfirmationStore());
  const { onYesBtnClick, closeAndResetOnNoBtnClick } = useConfirmationStore();
</script>